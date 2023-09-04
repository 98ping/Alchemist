package ltd.matrixstudios.alchemist.util.totp

import ltd.matrixstudios.alchemist.api.AlchemistAPI
import org.apache.commons.codec.binary.Base32
import java.net.URLEncoder
import java.security.GeneralSecurityException
import java.security.SecureRandom
import java.util.*
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec


object TOTPUtil
{
    const val DEFAULT_TIME_STEP_SECONDS = 30
    private const val NUM_DIGITS_OUTPUT = 6
    private var blockOfZeros: String? = null

    init {
        val chars = CharArray(NUM_DIGITS_OUTPUT)
        for (i in chars.indices)
        {
            chars[i] = '0'
        }
        blockOfZeros = String(chars)
    }


    private var SECURE_RANDOM: SecureRandom? = null
    private val BASE_32_ENCODER = Base32()

    fun generateSecret(): String?
    {
        try
        {
            SECURE_RANDOM = SecureRandom.getInstance("SHA1PRNG", "SUN");
        } catch (e: Exception)
        {
            return null
        }

        val secretKey = ByteArray(10)
        SECURE_RANDOM!!.nextBytes(secretKey)
        return BASE_32_ENCODER.encodeToString(secretKey)
    }

    fun qrImageUrl(secret: String, username: String): String
    {
        return String.format(
            "https://www.google.com/chart?chs=200x200&chld=M%%7C0&cht=qr&chl=otpauth://totp/%s@%s%%3Fsecret%%3D%s",
            *arrayOf<Any>(username, URLEncoder.encode(AlchemistAPI.GENERIC_NAME, "UTF-8"), secret)
        )
    }

    fun generateOtpAuthUrl(keyId: String, secret: String, username: String): String
    {
        val sb = StringBuilder(64)
        addOtpAuthPart(keyId, username, secret, sb)
        return sb.toString()
    }

    private fun addOtpAuthPart(keyId: String, username: String?, secret: String, sb: StringBuilder)
    {
        sb.append("otpauth://totp/$keyId:$username?secret=$secret&issuer=$keyId")
    }

    fun generateBase32Secret(length: Int): String
    {
        val sb = StringBuilder(length)
        val random: Random = SecureRandom()
        for (i in 0 until length)
        {
            val `val`: Int = random.nextInt(32)
            if (`val` < 26)
            {
                sb.append(('A'.code + `val`).toChar())
            } else
            {
                sb.append(('2'.code + (`val` - 26)).toChar())
            }
        }
        return sb.toString()
    }

    @Throws(GeneralSecurityException::class)
    fun validateCurrentNumber(base32Secret: String, authNumber: Int, windowMillis: Int): Boolean
    {
        return validateCurrentNumber(
            base32Secret, authNumber, windowMillis, System.currentTimeMillis(),
            DEFAULT_TIME_STEP_SECONDS
        )
    }

    @Throws(GeneralSecurityException::class)
    fun validateCurrentNumber(
        base32Secret: String, authNumber: Int, windowMillis: Int, timeMillis: Long,
        timeStepSeconds: Int
    ): Boolean
    {
        var from = timeMillis
        var to = timeMillis
        if (windowMillis > 0)
        {
            from -= windowMillis.toLong()
            to += windowMillis.toLong()
        }
        val timeStepMillis = (timeStepSeconds * 1000).toLong()
        var millis = from
        while (millis <= to)
        {
            val compare = generateNumber(base32Secret, millis, timeStepSeconds)
            if (compare == authNumber.toLong())
            {
                return true
            }
            millis += timeStepMillis
        }
        return false
    }

    @Throws(GeneralSecurityException::class)
    fun generateCurrentNumberString(base32Secret: String): String?
    {
        return generateNumberString(base32Secret, System.currentTimeMillis(), DEFAULT_TIME_STEP_SECONDS)
    }

    @Throws(GeneralSecurityException::class)
    fun generateNumberString(base32Secret: String, timeMillis: Long, timeStepSeconds: Int): String?
    {
        val number = generateNumber(base32Secret, timeMillis, timeStepSeconds)
        return zeroPrepend(number, NUM_DIGITS_OUTPUT)
    }

    @Throws(GeneralSecurityException::class)
    fun generateCurrentNumber(base32Secret: String): Long
    {
        return generateNumber(base32Secret, System.currentTimeMillis(), DEFAULT_TIME_STEP_SECONDS)
    }

    @Throws(GeneralSecurityException::class)
    fun generateNumber(base32Secret: String, timeMillis: Long, timeStepSeconds: Int): Long
    {
        val key: ByteArray = decodeBase32(base32Secret)
        val data = ByteArray(8)
        var value = timeMillis / 1000 / timeStepSeconds
        run {
            var i = 7
            while (value > 0)
            {
                data[i] = (value and 0xFFL).toByte()
                value = value shr 8
                i--
            }
        }

        // encrypt the data with the key and return the SHA1 of it in hex
        val signKey = SecretKeySpec(key, "HmacSHA1")
        // if this is expensive, could put in a thread-local
        val mac = Mac.getInstance("HmacSHA1")
        mac.init(signKey)
        val hash = mac.doFinal(data)

        // take the 4 least significant bits from the encrypted string as an offset
        val offset = hash[hash.size - 1].toInt() and 0xF

        // We're using a long because Java hasn't got unsigned int.
        var truncatedHash: Long = 0
        for (i in offset until offset + 4)
        {
            truncatedHash = truncatedHash shl 8
            // get the 4 bytes at the offset
            truncatedHash = truncatedHash or (hash[i].toInt() and 0xFF).toLong()
        }
        // cut off the top bit
        truncatedHash = truncatedHash and 0x7FFFFFFFL

        // the token is then the last 6 digits in the number
        truncatedHash %= 1000000
        return truncatedHash
    }


    /**
     * Return the otp-auth part of the QR image which is suitable to be injected into other QR generators (e.g. JS
     * generator).
     *
     * @param keyId
     * Name of the key that you want to show up in the users authentication application. Should already be
     * URL encoded.
     * @param secret
     * Secret string that will be used when generating the current number.
     */
    fun generateOtpAuthUrl(keyId: String, secret: String): String?
    {
        val sb = StringBuilder(64)
        addOtpAuthPart(keyId, secret, sb)
        return sb.toString()
    }

    private fun addOtpAuthPart(keyId: String, secret: String, sb: StringBuilder)
    {
        sb.append("otpauth://totp/").append(keyId).append("%3Fsecret%3D").append(secret)
    }

    /**
     * Return the string prepended with 0s. Tested as 10x faster than String.format("%06d", ...); Exposed for testing.
     */
    fun zeroPrepend(num: Long, digits: Int): String?
    {
        val numStr = java.lang.Long.toString(num)
        return if (numStr.length >= digits)
        {
            numStr
        } else
        {
            val sb = StringBuilder(digits)
            val zeroCount = digits - numStr.length
            sb.append(blockOfZeros, 0, zeroCount)
            sb.append(numStr)
            sb.toString()
        }
    }

    fun decodeBase32(string: String) : ByteArray
    {
        val BASE_32_ENCODER = Base32()

        return BASE_32_ENCODER.decode(string)
    }
}
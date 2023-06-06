package ltd.matrixstudios.alchemist.util

import lombok.experimental.UtilityClass
import java.security.GeneralSecurityException
import java.security.SecureRandom
import java.util.*
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec


/**
 * Java implementation for the Time-based One-Time Password (TOTP) two factor authentication algorithm. To get this to
 * work you:
 *
 *
 *  1. Use generateBase32Secret() to generate a secret key for a user.
 *  1. Store the secret key in the database associated with the user account.
 *  1. Display the QR image URL returned by qrImageUrl(...) to the user.
 *  1. User uses the image to load the secret key into his authenticator application.
 *
 *
 *
 *
 * Whenever the user logs in:
 *
 *
 *
 *  1. The user enters the number from the authenticator application into the login form.
 *  1. Read the secret associated with the user account from the database.
 *  1. The server compares the user input with the output from generateCurrentNumber(...).
 *  1. If they are equal then the user is allowed to log in.
 *
 *
 *
 *
 * See: https://github.com/j256/two-factor-auth
 *
 *
 *
 *
 * For more details about this magic algorithm, see: http://en.wikipedia.org/wiki/Time-based_One-time_Password_Algorithm
 *
 *
 * @author graywatson
 */
@UtilityClass
object TOTPUtil {
    /** default time-step which is part of the spec, 30 seconds is default  */
    const val DEFAULT_TIME_STEP_SECONDS = 30

    /** set to the number of digits to control 0 prefix, set to 0 for no prefix  */
    private const val NUM_DIGITS_OUTPUT = 6
    private var blockOfZeros: String? = null

    init {
        val chars = CharArray(NUM_DIGITS_OUTPUT)
        for (i in chars.indices) {
            chars[i] = '0'
        }
        blockOfZeros = chars.toString()
    }
    /**
     * Similar to [.generateBase32Secret] but specifies a character length.
     */
    /**
     * Generate and return a 16-character secret key in base32 format (A-Z2-7) using [SecureRandom]. Could be used
     * to generate the QR image to be shared with the user. Other lengths should use [.generateBase32Secret].
     */
    @JvmOverloads
    fun generateBase32Secret(length: Int = 16): String {
        val sb = StringBuilder(length)
        val random: Random = SecureRandom()
        for (i in 0 until length) {
            val `val` = random.nextInt(32)
            if (`val` < 26) {
                sb.append(('A'.code + `val`).toChar())
            } else {
                sb.append(('2'.code + (`val` - 26)).toChar())
            }
        }
        return sb.toString()
    }
    /**
     * Similar to [.validateCurrentNumber] except exposes other parameters. Mostly for testing.
     *
     * @param base32Secret
     * Secret string encoded using base-32 that was used to generate the QR code or shared with the user.
     * @param authNumber
     * Time based number provided by the user from their authenticator application.
     * @param windowMillis
     * Number of milliseconds that they are allowed to be off and still match. This checks before and after
     * the current time to account for clock variance. Set to 0 for no window.
     * @param timeMillis
     * Time in milliseconds.
     * @param timeStepSeconds
     * Time step in seconds. The default value is 30 seconds here. See [.DEFAULT_TIME_STEP_SECONDS].
     * @return True if the authNumber matched the calculated number within the specified window.
     */
    /**
     * Validate a given secret-number using the secret base-32 string. This allows you to set a window in milliseconds to
     * account for people being close to the end of the time-step. For example, if windowMillis is 10000 then this method
     * will check the authNumber against the generated number from 10 seconds before now through 10 seconds after now.
     *
     *
     *
     * WARNING: This requires a system clock that is in sync with the world.
     *
     *
     * @param base32Secret
     * Secret string encoded using base-32 that was used to generate the QR code or shared with the user.
     * @param authNumber
     * Time based number provided by the user from their authenticator application.
     * @param windowMillis
     * Number of milliseconds that they are allowed to be off and still match. This checks before and after
     * the current time to account for clock variance. Set to 0 for no window.
     * @return True if the authNumber matched the calculated number within the specified window.
     */
    @JvmOverloads
    @Throws(GeneralSecurityException::class)
    fun validateCurrentNumber(
        base32Secret: String, authNumber: Int, windowMillis: Int, timeMillis: Long = System.currentTimeMillis(),
        timeStepSeconds: Int =
            DEFAULT_TIME_STEP_SECONDS
    ): Boolean {
        var from = timeMillis
        var to = timeMillis
        if (windowMillis > 0) {
            from -= windowMillis.toLong()
            to += windowMillis.toLong()
        }
        val timeStepMillis = (timeStepSeconds * 1000).toLong()
        var millis = from
        while (millis <= to) {
            val compare = generateNumber(base32Secret, millis, timeStepSeconds)
            if (compare == authNumber.toLong()) {
                return true
            }
            millis += timeStepMillis
        }
        return false
    }

    /**
     * Return the current number to be checked. This can be compared against user input.
     *
     *
     *
     * WARNING: This requires a system clock that is in sync with the world.
     *
     *
     * @param base32Secret
     * Secret string encoded using base-32 that was used to generate the QR code or shared with the user.
     * @return A number as a string with possible leading zeros which should match the user's authenticator application
     * output.
     */
    @Throws(GeneralSecurityException::class)
    fun generateCurrentNumberString(base32Secret: String): String {
        return generateNumberString(base32Secret, System.currentTimeMillis(), DEFAULT_TIME_STEP_SECONDS)
    }

    /**
     * Similar to [.generateCurrentNumberString] except exposes other parameters. Mostly for testing.
     *
     * @param base32Secret
     * Secret string encoded using base-32 that was used to generate the QR code or shared with the user.
     * @param timeMillis
     * Time in milliseconds.
     * @param timeStepSeconds
     * Time step in seconds. The default value is 30 seconds here. See [.DEFAULT_TIME_STEP_SECONDS].
     * @return A number as a string with possible leading zeros which should match the user's authenticator application
     * output.
     */
    @Throws(GeneralSecurityException::class)
    fun generateNumberString(base32Secret: String, timeMillis: Long, timeStepSeconds: Int): String {
        val number = generateNumber(base32Secret, timeMillis, timeStepSeconds)
        return zeroPrepend(number, NUM_DIGITS_OUTPUT)
    }

    /**
     * Similar to [.generateCurrentNumberString] but this returns a long instead of a string.
     *
     * @return A number which should match the user's authenticator application output.
     */
    @Throws(GeneralSecurityException::class)
    fun generateCurrentNumber(base32Secret: String): Long {
        return generateNumber(base32Secret, System.currentTimeMillis(), DEFAULT_TIME_STEP_SECONDS)
    }

    /**
     * Similar to [.generateNumberString] but this returns a long instead of a string.
     *
     * @return A number which should match the user's authenticator application output.
     */
    @Throws(GeneralSecurityException::class)
    fun generateNumber(base32Secret: String, timeMillis: Long, timeStepSeconds: Int): Long {
        val key = decodeBase32(base32Secret)
        val data = ByteArray(8)
        var value = timeMillis / 1000 / timeStepSeconds
        run {
            var i = 7
            while (value > 0) {
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
        for (i in offset until offset + 4) {
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
     * Return the QR image url thanks to Google. This can be shown to the user and scanned by the authenticator program
     * as an easy way to enter the secret.
     *
     * @param keyId
     * Name of the key that you want to show up in the users authentication application. Should already be
     * URL encoded.
     * @param secret
     * Secret string that will be used when generating the current number.
     */
    fun qrImageUrl(keyId: String, secret: String): String {
        val sb = StringBuilder(128)
        sb.append("https://chart.googleapis.com/chart?chs=200x200&cht=qr&chl=200x200&chld=M|0&cht=qr&chl=")
        addOtpAuthPart(keyId, secret, sb)
        return sb.toString()
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
    fun generateOtpAuthUrl(keyId: String, secret: String): String {
        val sb = StringBuilder(64)
        addOtpAuthPart(keyId, secret, sb)
        return sb.toString()
    }

    private fun addOtpAuthPart(keyId: String, secret: String, sb: StringBuilder) {
        sb.append("otpauth://totp/").append(keyId).append("%3Fsecret%3D").append(secret)
    }

    /**
     * Return the string prepended with 0s. Tested as 10x faster than String.format("%06d", ...); Exposed for testing.
     */
    fun zeroPrepend(num: Long, digits: Int): String {
        val numStr = java.lang.Long.toString(num)
        return if (numStr.length >= digits) {
            numStr
        } else {
            val sb = StringBuilder(digits)
            val zeroCount = digits - numStr.length
            sb.append(blockOfZeros, 0, zeroCount)
            sb.append(numStr)
            sb.toString()
        }
    }

    /**
     * Decode base-32 method. I didn't want to add a dependency to Apache Codec just for this decode method. Exposed for
     * testing.
     */
    fun decodeBase32(str: String): ByteArray {
        // each base-32 character encodes 5 bits
        val numBytes = (str.length * 5 + 7) / 8
        var result = ByteArray(numBytes)
        var resultIndex = 0
        var which = 0
        var working = 0
        for (i in 0 until str.length) {
            val ch = str[i]
            var `val`: Int
            if (ch >= 'a' && ch <= 'z') {
                `val` = ch.code - 'a'.code
            } else if (ch >= 'A' && ch <= 'Z') {
                `val` = ch.code - 'A'.code
            } else if (ch >= '2' && ch <= '7') {
                `val` = 26 + (ch.code - '2'.code)
            } else if (ch == '=') {
                // special case
                which = 0
                break
            } else {
                throw IllegalArgumentException("Invalid base-32 character: $ch")
            }
            when (which) {
                0 -> {
                    // all 5 bits is top 5 bits
                    working = `val` and 0x1F shl 3
                    which = 1
                }

                1 -> {
                    // top 3 bits is lower 3 bits
                    working = working or (`val` and 0x1C shr 2)
                    result[resultIndex++] = working.toByte()
                    // lower 2 bits is upper 2 bits
                    working = `val` and 0x03 shl 6
                    which = 2
                }

                2 -> {
                    // all 5 bits is mid 5 bits
                    working = working or (`val` and 0x1F shl 1)
                    which = 3
                }

                3 -> {
                    // top 1 bit is lowest 1 bit
                    working = working or (`val` and 0x10 shr 4)
                    result[resultIndex++] = working.toByte()
                    // lower 4 bits is top 4 bits
                    working = `val` and 0x0F shl 4
                    which = 4
                }

                4 -> {
                    // top 4 bits is lowest 4 bits
                    working = working or (`val` and 0x1E shr 1)
                    result[resultIndex++] = working.toByte()
                    // lower 1 bit is top 1 bit
                    working = `val` and 0x01 shl 7
                    which = 5
                }

                5 -> {
                    // all 5 bits is mid 5 bits
                    working = working or (`val` and 0x1F shl 2)
                    which = 6
                }

                6 -> {
                    // top 2 bits is lowest 2 bits
                    working = working or (`val` and 0x18 shr 3)
                    result[resultIndex++] = working.toByte()
                    // lower 3 bits of byte 6 is top 3 bits
                    working = `val` and 0x07 shl 5
                    which = 7
                }

                7 -> {
                    // all 5 bits is lower 5 bits
                    working = working or (`val` and 0x1F)
                    result[resultIndex++] = working.toByte()
                    which = 0
                }
            }
        }
        if (which != 0) {
            result[resultIndex++] = working.toByte()
        }
        if (resultIndex != result.size) {
            result = Arrays.copyOf(result, resultIndex)
        }
        return result
    }
}

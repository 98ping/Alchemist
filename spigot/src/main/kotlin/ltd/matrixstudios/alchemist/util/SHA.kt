package ltd.matrixstudios.alchemist.util

import java.math.BigInteger
import java.nio.charset.StandardCharsets
import java.security.MessageDigest


object SHA {

    fun toHexString(input: String): String? {
        return try {
            val md = MessageDigest.getInstance("SHA-384")
            val hash = md.digest(input.toByteArray(StandardCharsets.UTF_8))
            val number = BigInteger(1, hash)
            val hexString = StringBuilder(number.toString(16))
            while (hexString.length < 32) hexString.insert(0, '0')
            hexString.toString()
        } catch (ex: Exception) {
            ex.printStackTrace()
            null
        }
    }
}
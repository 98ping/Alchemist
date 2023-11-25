package ltd.matrixstudios.website.utils.mojang

import com.google.gson.JsonParser
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.util.*

/**
 * Class created on 11/24/2023

 * @author 98ping
 * @project Alchemist
 * @website https://solo.to/redis
 */
object MojangUtils
{
    @Throws(Exception::class)
    fun fetchUUID(playerName: String?): UUID?
    {
        val url = URL("https://api.minetools.eu/uuid/$playerName")
        val connection = url.openConnection() as HttpURLConnection
        connection.connect()
        val inputStream = connection.inputStream
        val bufferedReader = BufferedReader(InputStreamReader(inputStream))
        val element = JsonParser().parse(bufferedReader)
        val `object` = element.asJsonObject
        val status = `object`["status"].asString
        if (status.equals("ERR", ignoreCase = true))
        {
            return null
        }
        val uuidAsString = `object`["id"].asString
        return parseUUIDFromString(uuidAsString)
    }

    @Throws(Exception::class)
    fun fetchName(uuid: UUID?): String?
    {
        val url = URL("https://api.minetools.eu/profile/" + uuid.toString().replace("-", ""))
        val connection = url.openConnection() as HttpURLConnection
        connection.connect()
        val inputStream = connection.inputStream
        val bufferedReader = BufferedReader(InputStreamReader(inputStream))
        val element = JsonParser().parse(bufferedReader)
        val `object` = element.asJsonObject
        val raw = `object`["raw"]
        val rawObject = raw.asJsonObject
        val status = rawObject["status"].asString ?: return null
        return rawObject["name"].asString
    }

    private fun parseUUIDFromString(uuidAsString: String): UUID
    {
        val parts = arrayOf(
            "0x" + uuidAsString.substring(0, 8),
            "0x" + uuidAsString.substring(8, 12),
            "0x" + uuidAsString.substring(12, 16),
            "0x" + uuidAsString.substring(16, 20),
            "0x" + uuidAsString.substring(20, 32)
        )
        var mostSigBits = java.lang.Long.decode(parts[0])
        mostSigBits = mostSigBits shl 16
        mostSigBits = mostSigBits or java.lang.Long.decode(parts[1])
        mostSigBits = mostSigBits shl 16
        mostSigBits = mostSigBits or java.lang.Long.decode(parts[2])
        var leastSigBits = java.lang.Long.decode(parts[3])
        leastSigBits = leastSigBits shl 48
        leastSigBits = leastSigBits or java.lang.Long.decode(parts[4])
        return UUID(mostSigBits, leastSigBits)
    }
}
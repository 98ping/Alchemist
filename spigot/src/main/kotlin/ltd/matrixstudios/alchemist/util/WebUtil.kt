package ltd.matrixstudios.alchemist.util

import ltd.matrixstudios.alchemist.Alchemist
import ltd.matrixstudios.alchemist.api.AlchemistAPI
import org.bukkit.event.player.PlayerJoinEvent
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.URL
import java.net.URLConnection
import java.util.UUID


object WebUtil {

    fun playerHasLiked(uuid: UUID) : Boolean {
        try {
            val serverLikes = URL("https://api.namemc.com/server/${AlchemistAPI.SERVER_NAME}/likes")
            val urlConn: URLConnection = serverLikes.openConnection()
            urlConn.setRequestProperty(
                "User-Agent",
                "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2"
            )
            urlConn.getDoOutput()
            BufferedReader(InputStreamReader(urlConn.getInputStream())).use { reader ->
                val obj = Alchemist.gson.fromJson(reader, Array<String>::class.java)

                if (obj.contains(uuid.toString()))
                {
                    return true
                }
            }
        } catch (ex: IOException) {
            return false
        }

        return false
    }
}
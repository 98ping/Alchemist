package ltd.matrixstudios.alchemist.servers.packets

import ltd.matrixstudios.alchemist.Alchemist
import ltd.matrixstudios.alchemist.AlchemistSpigotPlugin
import ltd.matrixstudios.alchemist.redis.RedisPacket
import org.bukkit.Bukkit

class ServerShutdownPacket(val target: String) : RedisPacket("shutdown-packet") {

    override fun action() {
        val server = Alchemist.globalServer

        if (server.id.equals(target, ignoreCase = true))
        {
            Bukkit.broadcastMessage("&8[&eMonitor&8] &fServer has been forcefully shut down by a remote user" )
            Bukkit.shutdown()
        }
    }
}
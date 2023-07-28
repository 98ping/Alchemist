package ltd.matrixstudios.alchemist.servers.packets


import ltd.matrixstudios.alchemist.Alchemist
import ltd.matrixstudios.alchemist.AlchemistSpigotPlugin
import ltd.matrixstudios.alchemist.redis.RedisPacket
import org.bukkit.Bukkit

class ExplicitServerWhitelistPacket(val target: String, val boolean: Boolean) : RedisPacket("explicit-whitelist-packet") {

    override fun action() {
        val server = Alchemist.globalServer

        if (server.id.equals(target, ignoreCase = true))
        {
            Bukkit.setWhitelist(boolean)
        }
    }
}
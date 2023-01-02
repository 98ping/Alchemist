package ltd.matrixstudios.alchemist.packets

import ltd.matrixstudios.alchemist.redis.RedisPacket
import ltd.matrixstudios.alchemist.util.Chat
import org.bukkit.Bukkit
import java.util.*

class PlayerKickPacket(private val uuid: UUID, private val reason: String) : RedisPacket("player-kick") {

    override fun action()
    {
        if (Bukkit.getPlayer(uuid) != null)
        {
            Bukkit.getPlayer(uuid).kickPlayer(Chat.format(reason))
        }
    }
}

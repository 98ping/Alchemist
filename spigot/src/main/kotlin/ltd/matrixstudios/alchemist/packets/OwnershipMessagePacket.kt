package ltd.matrixstudios.alchemist.packets

import ltd.matrixstudios.alchemist.redis.RedisPacket
import ltd.matrixstudios.alchemist.util.Chat
import org.bukkit.Bukkit

class OwnershipMessagePacket(val message: String) : RedisPacket("owners-message-general") {

    override fun action() {
        Bukkit.getOnlinePlayers().filter { it.hasPermission("alchemist.owner") }.forEach { it.sendMessage(Chat.format(message)) }
    }
}
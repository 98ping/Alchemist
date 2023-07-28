package ltd.matrixstudios.alchemist.packets

import ltd.matrixstudios.alchemist.redis.RedisPacket
import ltd.matrixstudios.alchemist.util.Chat
import org.bukkit.Bukkit

class StaffGeneralMessagePacket(val message: String) : RedisPacket("staff-message-general") {

    override fun action() {
        Bukkit.getOnlinePlayers().filter { it.hasPermission("alchemist.staff") }.forEach { it.sendMessage(Chat.format(message)) }
    }
}
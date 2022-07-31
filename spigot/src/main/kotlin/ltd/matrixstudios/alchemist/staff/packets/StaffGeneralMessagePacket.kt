package ltd.matrixstudios.alchemist.staff.packets

import ltd.matrixstudios.alchemist.api.AlchemistAPI
import ltd.matrixstudios.alchemist.redis.RedisPacket
import ltd.matrixstudios.alchemist.util.Chat
import org.bukkit.Bukkit
import java.util.*

class StaffGeneralMessagePacket(val message: String) : RedisPacket("staff-message-general") {

    override fun action() {
        Bukkit.getOnlinePlayers().filter { it.hasPermission("alchemist.positions.staff") }.forEach { it.sendMessage(Chat.format(message)) }
    }
}
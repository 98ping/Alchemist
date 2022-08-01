package ltd.matrixstudios.alchemist.staff.packets

import ltd.matrixstudios.alchemist.api.AlchemistAPI
import ltd.matrixstudios.alchemist.redis.RedisPacket
import ltd.matrixstudios.alchemist.util.Chat
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import java.util.*

class StaffMessagePacket(val message: String, val server: String, val sender: UUID) : RedisPacket("staff-message") {

    override fun action() {
        val name = AlchemistAPI.getRankDisplay(sender)
        val msg = "&b[SC] &7[$server] &r$name&7: &b$message"
        Bukkit.getOnlinePlayers().filter { it.hasPermission("alchemist.positions.staff") }.forEach { it.sendMessage(Chat.format(msg)) }
    }
}
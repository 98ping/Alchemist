package ltd.matrixstudios.alchemist.staff.packets

import ltd.matrixstudios.alchemist.api.AlchemistAPI
import ltd.matrixstudios.alchemist.redis.RedisPacket
import ltd.matrixstudios.alchemist.util.Chat
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import java.util.*

class AdminChatPacket(val message: String, val server: String, val sender: UUID) : RedisPacket("admin-message") {

    override fun action() {
        val name = AlchemistAPI.getRankDisplay(sender)
        val msg = "&c[AC] &7[$server] &r$name&7: &b$message"
        Bukkit.getOnlinePlayers().filter { it.hasPermission("alchemist.positions.admin") }.forEach { it.sendMessage(Chat.format(msg)) }
    }
}
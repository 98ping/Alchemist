package ltd.matrixstudios.alchemist.packets

import ltd.matrixstudios.alchemist.api.AlchemistAPI
import ltd.matrixstudios.alchemist.redis.RedisPacket
import ltd.matrixstudios.alchemist.util.Chat
import org.bukkit.Bukkit
import java.util.*

class AdminChatPacket(val message: String, val server: String, val sender: UUID) : RedisPacket("admin-message") {

    override fun action() {
        val name = AlchemistAPI.getRankDisplay(sender)
        val msg = AlchemistAPI.AC_FORMAT.replace("%server%", server).replace("%profile%", name).replace("%message%", message)
        Bukkit.getOnlinePlayers().filter { it.hasPermission("alchemist.admin") }.forEach { it.sendMessage(Chat.format(msg)) }
    }
}
package ltd.matrixstudios.alchemist.staff.requests.packets

import ltd.matrixstudios.alchemist.redis.RedisPacket
import ltd.matrixstudios.alchemist.staff.requests.handlers.RequestHandler
import ltd.matrixstudios.alchemist.util.Chat
import org.bukkit.Bukkit

class RequestPacket(val message: String) : RedisPacket("staff-message-request") {

    override fun action() {
        Bukkit.getOnlinePlayers().filter {
            it.hasPermission("alchemist.staff") && RequestHandler.hasReportsEnabled(it)
        }.forEach { it.sendMessage(Chat.format(message)) }
    }
}
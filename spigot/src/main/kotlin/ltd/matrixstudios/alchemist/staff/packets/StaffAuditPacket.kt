package ltd.matrixstudios.alchemist.staff.packets

import ltd.matrixstudios.alchemist.api.AlchemistAPI
import ltd.matrixstudios.alchemist.redis.RedisPacket
import ltd.matrixstudios.alchemist.util.Chat
import org.bukkit.Bukkit
import org.bukkit.entity.Player

class StaffAuditPacket(val message: String) : RedisPacket("staff-audit") {

    override fun action() {
        Bukkit.getOnlinePlayers().filter { it.hasPermission("alchemist.audit") }.forEach { it.sendMessage(Chat.format(message)) }
    }
}
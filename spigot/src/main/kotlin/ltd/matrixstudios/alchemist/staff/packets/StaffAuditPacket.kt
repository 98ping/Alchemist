package ltd.matrixstudios.alchemist.staff.packets

import ltd.matrixstudios.alchemist.commands.grants.AuditCommand
import ltd.matrixstudios.alchemist.redis.RedisPacket
import ltd.matrixstudios.alchemist.util.Chat
import org.bukkit.Bukkit

class StaffAuditPacket(val message: String) : RedisPacket("staff-audit") {

    override fun action() {
        Bukkit.getOnlinePlayers().filter { AuditCommand.auditedPlayers.contains(it.uniqueId) }.forEach { it.sendMessage(Chat.format(message)) }
    }
}
package ltd.matrixstudios.alchemist.staff.packets

import ltd.matrixstudios.alchemist.api.AlchemistAPI
import ltd.matrixstudios.alchemist.redis.RedisPacket
import ltd.matrixstudios.alchemist.util.Chat
import org.bukkit.Bukkit
import org.bukkit.entity.Player

class StaffMessagePacket(val message: String, val sender: Player) : RedisPacket("staff-message") {

    override fun action() {
        val name = AlchemistAPI.getRankDisplay(sender.uniqueId)
        val msg = "&b[S] &r$name&7: &f$message"
        Bukkit.getOnlinePlayers().filter { it.hasPermission("alchemist.positions.staff") }.forEach { it.sendMessage(Chat.format(msg)) }
    }
}
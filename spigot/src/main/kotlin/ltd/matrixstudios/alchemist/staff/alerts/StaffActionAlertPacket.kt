package ltd.matrixstudios.alchemist.staff.alerts

import ltd.matrixstudios.alchemist.Alchemist
import ltd.matrixstudios.alchemist.redis.RedisPacket
import ltd.matrixstudios.alchemist.util.Chat
import org.bukkit.Bukkit

// classic overengineering
class StaffActionAlertPacket(val action: String, val user: String, val server: String) : RedisPacket("staff-action-alert")
{
    override fun action()
    {
        if (Alchemist.globalServer.id.equals(server, ignoreCase = true))
        {
            Bukkit.getOnlinePlayers().filter { it.hasPermission("alchemist.staffalerts") }.forEach {
                it.sendMessage(
                    Chat.format("&7&o[$user: &e$action&7&o]")
                )
            }
        }
    }
}
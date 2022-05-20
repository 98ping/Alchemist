package ltd.matrixstudios.alchemist.listeners.filter

import ltd.matrixstudios.alchemist.redis.AsynchronousRedisSender
import ltd.matrixstudios.alchemist.service.filter.FilterService
import ltd.matrixstudios.alchemist.staff.packets.StaffAuditPacket
import ltd.matrixstudios.alchemist.util.Chat
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerChatEvent

object FilterListener : Listener {

    @EventHandler
    fun chat(event: AsyncPlayerChatEvent) {
        for (filter in FilterService.getValues())
        {
            if (event.message.contains(filter.word, ignoreCase = false))
            {
                event.isCancelled = true
                event.player.sendMessage(Chat.format("&c&lYou have set off our filter system. Word: &e" + filter.word))
                AsynchronousRedisSender.send(StaffAuditPacket("&c[Filtered] &e(" + event.player.displayName + " &e-> " + event.message + ")"))
            }
        }
    }
}
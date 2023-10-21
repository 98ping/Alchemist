package ltd.matrixstudios.alchemist.servers.packets

import ltd.matrixstudios.alchemist.AlchemistSpigotPlugin
import ltd.matrixstudios.alchemist.models.server.UniqueServer
import ltd.matrixstudios.alchemist.redis.RedisPacket
import ltd.matrixstudios.alchemist.util.Chat
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.event.HoverEvent
import org.bukkit.Bukkit

class ServerStatusChangePacket(
    var message: String,
    var server: UniqueServer
) : RedisPacket("server-status-change-packet")
{

    override fun action()
    {
        val hoverComponent = Component.text(Chat.format("&8&m-------------------------"))
            .appendNewline()
            .append(Component.text(Chat.format("&fServer: &e${server.displayName}")))
            .appendNewline()
            .append(Component.text(Chat.format("&fRam: &e" + server.ramAllocated + "mb")))
            .appendNewline()
            .append(Component.text(Chat.format("&fServer Locked: &e" + if (server.lockedWithRank) "&aYes" else "&cNo")))
            .appendNewline()
            .append(Component.text(Chat.format("&8&m-------------------------")))

        val component = Component.text(
            Chat.format(message)
        ).hoverEvent(HoverEvent.showText(hoverComponent))

        Bukkit.getOnlinePlayers().filter { it.hasPermission("alchemist.staff") }.forEach {
            AlchemistSpigotPlugin.instance.audience.player(it).sendMessage(component.asComponent())
        }
    }
}
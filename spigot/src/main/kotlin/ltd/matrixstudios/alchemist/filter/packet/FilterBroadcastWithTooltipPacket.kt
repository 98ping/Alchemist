package ltd.matrixstudios.alchemist.filter.packet

import com.sun.org.apache.xpath.internal.operations.Bool
import ltd.matrixstudios.alchemist.AlchemistSpigotPlugin
import ltd.matrixstudios.alchemist.api.AlchemistAPI
import ltd.matrixstudios.alchemist.redis.RedisPacket
import ltd.matrixstudios.alchemist.util.Chat
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.event.ClickEvent
import net.kyori.adventure.text.event.HoverEvent
import org.bukkit.Bukkit

class FilterBroadcastWithTooltipPacket(
    var filterMessage: String,
    var target: String,
    var mutes: Int,
    var bans: Int,
    var alreadyPunished: Boolean,
) : RedisPacket("filter-broadcast-packet") {

    override fun action() {
        val hoverComponent = Component.text(Chat.format("&6&m-------------------------"))
            .appendNewline()
            .append(Component.text(Chat.format("&eTarget: &r$target")))
            .appendNewline()
            .append(Component.text(Chat.format("&ePreviously Muted: &f$mutes times")))
            .appendNewline()
            .append(Component.text(Chat.format("&ePreviously Banned: &f$bans times")))
            .appendNewline()
            .append(Component.text(Chat.format("&eAlready punished: " + if (alreadyPunished) "&aYes" else "&cNo"))
            .appendNewline()
            .append(Component.text(Chat.format("&6&m-------------------------"))))

        val component = Component.text(
            Chat.format(filterMessage)).hoverEvent(HoverEvent.showText(hoverComponent))

        Bukkit.getOnlinePlayers().filter { it.hasPermission("alchemist.staff") }.forEach {
            AlchemistSpigotPlugin.instance.audience.player(it).sendMessage(component.asComponent())
        }
    }
}
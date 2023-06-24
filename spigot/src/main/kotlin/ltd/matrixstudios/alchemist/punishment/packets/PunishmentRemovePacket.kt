package ltd.matrixstudios.alchemist.punishment.packets

import ltd.matrixstudios.alchemist.AlchemistSpigotPlugin
import ltd.matrixstudios.alchemist.api.AlchemistAPI
import ltd.matrixstudios.alchemist.models.grant.types.Punishment
import ltd.matrixstudios.alchemist.punishments.PunishmentType
import ltd.matrixstudios.alchemist.redis.RedisPacket
import ltd.matrixstudios.alchemist.util.Chat
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.event.HoverEvent
import net.kyori.adventure.text.event.HoverEventSource
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.Bukkit
import java.util.*

class PunishmentRemovePacket(
    var punishmentType: PunishmentType,
    var target: UUID,
    var executor: UUID,
    var silent: Boolean,
    var reason: String
) : RedisPacket(
    "remove-punishment"
) {

    override fun action() {
        val profile = AlchemistAPI.syncFindProfile(executor)
        val target = AlchemistAPI.syncFindProfile(target)
        val hoverComponent = Component.text(Chat.format("&6&m-------------------------"))
            .appendNewline()
            .append(Component.text("Target: ").color(NamedTextColor.YELLOW)
                .append(Component.text(profile?.username ?: "Console").color(Chat.findTextColorFromString(profile?.getCurrentRank()?.color ?: "&c")))
                .appendNewline()
                .append(Component.text(Chat.format("&eExecutor: &r")))
                .append(Component.text(target?.username ?: "Console").color(Chat.findTextColorFromString(target?.getCurrentRank()?.color ?: "&c"))))
            .appendNewline()
            .append(Component.text(Chat.format("&eReason: &f$reason")))
            .appendNewline()
            .append(Component.text(Chat.format("&6&m-------------------------")))

        val component = Component.text(Chat.format((if (silent) "&7[Silent]" else "") + " &r"))
            .append(Component.text(profile?.username ?: "Console").color(Chat.findTextColorFromString(profile?.getCurrentRank()?.color ?: "&c")))
            .append(Component.text(Chat.format(" &ahas " +
                    punishmentType.removed +
                    " &r")))
            .append(Component.text(target?.username ?: "Console").color(Chat.findTextColorFromString(target?.getCurrentRank()?.color ?: "&c"))).hoverEvent(HoverEvent.showText(hoverComponent))

        Bukkit.getOnlinePlayers().filter { it.hasPermission("alchemist.staff") }.forEach {
            AlchemistSpigotPlugin.instance.audience.player(it).sendMessage(component.asComponent())
        }
    }
}
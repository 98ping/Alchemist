package ltd.matrixstudios.alchemist.commands.alts

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandPermission
import ltd.matrixstudios.alchemist.AlchemistSpigotPlugin
import ltd.matrixstudios.alchemist.api.AlchemistAPI
import ltd.matrixstudios.alchemist.models.profile.GameProfile
import ltd.matrixstudios.alchemist.service.profiles.ProfileGameService
import ltd.matrixstudios.alchemist.util.Chat
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.TextComponent
import net.kyori.adventure.text.event.HoverEvent
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.command.CommandSender
import java.util.Date

class IpReportCommand : BaseCommand() {

    @CommandAlias("ipreport")
    @CommandPermission("alchemist.ipreport")
    fun ipreport(sender: CommandSender)
    {
        ProfileGameService.ipReportLookup().thenAccept {
            sender.sendMessage(Chat.format("&7[&f&oMuted Alt&7, &cBanned Alt&7, &4Blacklisted Alt&7]"))
            sender.sendMessage(Chat.format("&aEveryone's &ealts (&6${it.size}&e):"))
            val finalMessage = Component.text()
            val list = if (it.size > 350) it.take(350) else it
            for (target in list)
            {
                val userComponent = Component.text(Chat.format((target.getPunishmentedPrefix() + target.username) + "&7, ")).hoverEvent(HoverEvent.showText(createHover(target)))
                finalMessage.append(userComponent)
            }

            if (it.size > 350)
            {
                sender.sendMessage(Chat.format("&c(Only displaying first 350 entries...)"))
            }

            AlchemistSpigotPlugin.instance.audience.sender(sender).sendMessage(finalMessage)
        }
    }

    fun createHover(target: GameProfile) : Component
    {
        val hoverComponent =
            Component.text("Name: ").color(NamedTextColor.YELLOW).append(Component.text(target.username).color(Chat.findTextColorFromString(target.getCurrentRank().color)))
                .appendNewline()
                .append(Component.text(Chat.format("&eLast Seen: &6${Date(target.lastSeenAt)}")))
                .appendNewline()
                .append(Component.text(Chat.format("&e&m-----------------------------")))
                .appendNewline()
                .append(Component.text(Chat.format("&eAlt Count: &f${target.getAltAccounts().join().size}")))
                .appendNewline()
                .append(Component.text(Chat.format("&eMost Serious Punishment: &f${target.getAltMostSeriousPunishment()}")))
                .appendNewline()
                .append(Component.text(Chat.format("&e&m-----------------------------")))

        return hoverComponent
    }
}
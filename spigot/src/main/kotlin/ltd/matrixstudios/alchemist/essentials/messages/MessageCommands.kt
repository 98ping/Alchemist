package ltd.matrixstudios.alchemist.essentials.messages

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandCompletion
import co.aikar.commands.annotation.CommandPermission
import co.aikar.commands.annotation.Name
import co.aikar.commands.bukkit.contexts.OnlinePlayer
import ltd.matrixstudios.alchemist.AlchemistSpigotPlugin
import ltd.matrixstudios.alchemist.essentials.messages.menu.MessageSettingsMenu
import ltd.matrixstudios.alchemist.profiles.AsyncGameProfile
import ltd.matrixstudios.alchemist.punishment.BukkitPunishmentFunctions
import ltd.matrixstudios.alchemist.util.Chat
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import java.util.concurrent.CompletableFuture

class MessageCommands : BaseCommand() {

    @CommandAlias("message|msg|m|tell")
    @CommandCompletion("@players")
    fun message(player: CommandSender, @Name("target") target: OnlinePlayer, @Name("message...") message: String) {
        val ignored = MessageHandler.hasPlayerIgnored(target.player, BukkitPunishmentFunctions.getSenderUUID(player))

        if (ignored) {
            player.sendMessage(Chat.format("&cThis player has you ignored!"))
            return
        }

        MessageHandler.message(target.player, player, message)
    }

    @CommandAlias("reply|r")
    @CommandCompletion("@players")
    fun reply(player: Player, @Name("message...") message: String)
    {
        val lastSent = MessageHandler.replyMap[player.uniqueId]

        if (lastSent == null)
        {
            player.sendMessage(Chat.format("&cYou have nobody to reply to!"))
            return
        }

        val optional = Bukkit.getPlayer(lastSent)

        if (optional == null || !optional.isOnline)
        {
            player.sendMessage(Chat.format("&cThis user is no longer online!"))
            return
        }

        val ignored = MessageHandler.hasPlayerIgnored(optional, player.uniqueId)

        if (ignored) {
            player.sendMessage(Chat.format("&cThis player has you ignored!"))
            return
        }

        MessageHandler.message(optional, player, message)
    }

    @CommandAlias("messagesettings|msgsettings")
    fun msgSettings(player: Player)
    {
        MessageSettingsMenu(player).openMenu()
    }

    @CommandAlias("staffmessage|staffmsg")
    @CommandPermission("alchemist.messages.staff")
    fun staffMessage(player: Player, @Name("target") target: OnlinePlayer, @Name("message...") msg: String)
    {
        MessageHandler.staffMessage(target.player, player, msg)
    }

    @CommandAlias("ignore|ignoreadd")
    @CommandCompletion("@players")
    fun ignore(player: Player, @Name("target") profile: AsyncGameProfile) : CompletableFuture<Void>
    {
        return profile.use(player) {
            val uuid = it.uuid

            MessageHandler.addIgnoredPlayer(player, uuid)
            player.sendMessage(Chat.format("&aYou are now ignoring a player with the name &f${it.getRankDisplay()}"))
        }
    }

    @CommandAlias("unignore|removeignore")
    @CommandCompletion("@players")
    fun unignore(player: Player, @Name("target") profile: AsyncGameProfile) : CompletableFuture<Void>
    {
        return profile.use(player) {
            val uuid = it.uuid

            MessageHandler.removeIgnoredPlayer(player, uuid)
            player.sendMessage(Chat.format("&cYou are no longer ignoring a player with the name &f${it.getRankDisplay()}"))
        }
    }
}

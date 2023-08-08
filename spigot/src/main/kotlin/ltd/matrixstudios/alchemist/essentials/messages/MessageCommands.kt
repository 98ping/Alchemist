package ltd.matrixstudios.alchemist.essentials.messages

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandCompletion
import co.aikar.commands.annotation.Name
import co.aikar.commands.bukkit.contexts.OnlinePlayer
import ltd.matrixstudios.alchemist.profiles.AsyncGameProfile
import ltd.matrixstudios.alchemist.util.Chat
import org.bukkit.entity.Player
import java.util.concurrent.CompletableFuture

class MessageCommands : BaseCommand() {

    @CommandAlias("message|msg|m|tell")
    @CommandCompletion("@players")
    fun message(player: Player, @Name("target") target: OnlinePlayer, @Name("message...") message: String) {
        val ignored = MessageHandler.hasPlayerIgnored(target.player, player.uniqueId)

        if (ignored) {
            player.sendMessage(Chat.format("&cThis player has you ignored!"))
            return
        }

        MessageHandler.message(target.player, player, message)
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

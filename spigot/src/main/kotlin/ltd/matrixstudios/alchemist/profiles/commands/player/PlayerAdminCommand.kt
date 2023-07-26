package ltd.matrixstudios.alchemist.profiles.commands.player

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.*
import ltd.matrixstudios.alchemist.api.AlchemistAPI
import ltd.matrixstudios.alchemist.profiles.commands.player.menu.PlayerInformationMenu
import ltd.matrixstudios.alchemist.models.profile.GameProfile
import ltd.matrixstudios.alchemist.profiles.AsyncGameProfile
import ltd.matrixstudios.alchemist.util.Chat
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

@CommandAlias("useradmin|user|player|playeradmin")
class PlayerAdminCommand : BaseCommand() {

    @Subcommand("info")
    @CommandCompletion("@gameprofile")
    @CommandPermission("alchemist.profiles.admin")
    fun info(player: Player,  @Name("target")gameProfile: GameProfile) {
        PlayerInformationMenu(player, gameProfile).openMenu()
    }

    @Subcommand("test-async")
    @CommandCompletion("@gameprofile")
    @CommandPermission("alchemist.profiles.admin")
    fun asyncTest(player: Player, @Name("target") target: AsyncGameProfile) {
        target.use {
            player.sendMessage(Chat.format("&eName: &f" + it.username))
            player.sendMessage(Chat.format("&eRank Display: &f" + it.getRankDisplay()))
        }.whenComplete { v, t ->
            if (t != null) {
                player.sendMessage(Chat.format("&cUnable to complete"))
                player.sendMessage(t.localizedMessage)
            }
            player.sendMessage(Chat.format("&aReached end of future"))
        }
    }
}
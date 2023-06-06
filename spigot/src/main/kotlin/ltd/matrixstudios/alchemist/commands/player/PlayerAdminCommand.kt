package ltd.matrixstudios.alchemist.commands.player

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.*
import ltd.matrixstudios.alchemist.api.AlchemistAPI
import ltd.matrixstudios.alchemist.commands.player.menu.PlayerInformationMenu
import ltd.matrixstudios.alchemist.models.profile.GameProfile
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
}
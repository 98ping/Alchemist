package ltd.matrixstudios.alchemist.commands.notes

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.*
import ltd.matrixstudios.alchemist.commands.notes.menu.PlayerNotesMenu
import ltd.matrixstudios.alchemist.models.profile.GameProfile
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

@CommandAlias("notes|note")
@CommandPermission("alchemist.profiles.admin")
class PlayerNotesCommands : BaseCommand() {

    @Default
    @CommandCompletion("@gameprofile")
    fun default(sender: Player, @Name("target") gameProfile: GameProfile) {
        PlayerNotesMenu(sender, gameProfile).updateMenu()
    }
}
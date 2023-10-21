package ltd.matrixstudios.alchemist.commands.notes

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.*
import ltd.matrixstudios.alchemist.commands.notes.menu.PlayerNotesMenu
import ltd.matrixstudios.alchemist.models.profile.GameProfile
import ltd.matrixstudios.alchemist.models.profile.notes.ProfileNote
import ltd.matrixstudios.alchemist.service.profiles.ProfileGameService
import ltd.matrixstudios.alchemist.util.Chat
import org.bukkit.entity.Player

@CommandAlias("notes|note")
@CommandPermission("alchemist.profiles.admin")
class PlayerNotesCommands : BaseCommand()
{

    @Default
    @CatchUnknown
    @CommandCompletion("@gameprofile")
    fun default(sender: Player, @Name("target") gameProfile: GameProfile)
    {
        PlayerNotesMenu(sender, gameProfile).updateMenu()
    }

    @Subcommand("add")
    @CommandCompletion("@gameprofile")
    fun add(sender: Player, @Name("target") gameProfile: GameProfile, @Name("note") note: String)
    {
        gameProfile.notes.add(
            ProfileNote(
                author = sender.uniqueId,
                content = note,
                createdAt = System.currentTimeMillis()
            )
        )

        ProfileGameService.save(gameProfile)

        sender.sendMessage(Chat.format("&eAdded note to ${gameProfile.username}."))
    }
}
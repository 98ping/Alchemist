package ltd.matrixstudios.alchemist.commands.sessions

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandCompletion
import co.aikar.commands.annotation.CommandPermission
import co.aikar.commands.annotation.Name
import ltd.matrixstudios.alchemist.commands.sessions.menu.SessionsMenu
import ltd.matrixstudios.alchemist.models.profile.GameProfile
import ltd.matrixstudios.alchemist.service.session.SessionService
import ltd.matrixstudios.alchemist.util.Chat
import org.bukkit.entity.Player

class SessionCommands : BaseCommand()
{

    @CommandAlias("sessions")
    @CommandCompletion("@gameprofile")
    @CommandPermission("alchemist.sessions")
    fun sessions(player: Player, @Name("profile") profile: GameProfile)
    {
        if (!SessionService.cache.containsKey(profile.uuid))
        {
            player.sendMessage(Chat.format("&cPlayer has no sessions"))
            return
        }

        val sessions = SessionService.cache[profile.uuid]

        SessionsMenu(player, sessions!!).updateMenu()
    }
}
package ltd.matrixstudios.alchemist.profiles.commands.auth

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandPermission
import co.aikar.commands.annotation.Subcommand
import ltd.matrixstudios.alchemist.profiles.commands.auth.menu.AuthSetupMenu
import ltd.matrixstudios.alchemist.profiles.getProfile
import ltd.matrixstudios.alchemist.service.profiles.ProfileGameService
import ltd.matrixstudios.alchemist.util.Chat
import ltd.matrixstudios.alchemist.util.totp.TOTPUtil
import org.bukkit.entity.Player

@CommandAlias("auth|2fa")
@CommandPermission("alchemist.auth")
class AuthCommands : BaseCommand()
{

    @Subcommand("setup")
    fun onAuthSetup(player: Player)
    {
        val profile = player.getProfile()

        if (profile == null)
        {
            player.sendMessage(Chat.format("&cYou must have a profile in order to use this."))
            return
        }

        val authStatus = profile.getAuthStatus()
        if (authStatus.secret == "")
        {
            val secret = TOTPUtil.generateSecret()

            if (secret != null)
            {
                authStatus.secret = secret
                profile.authStatus = authStatus
            }
        }

        ProfileGameService.save(profile).thenAccept {
            AuthSetupMenu(player).openMenu()
        }
    }
}
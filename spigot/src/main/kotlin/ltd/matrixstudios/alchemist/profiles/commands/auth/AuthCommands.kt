package ltd.matrixstudios.alchemist.profiles.commands.auth

import co.aikar.commands.BaseCommand
import co.aikar.commands.CommandHelp
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandPermission
import co.aikar.commands.annotation.Description
import co.aikar.commands.annotation.HelpCommand
import co.aikar.commands.annotation.Name
import co.aikar.commands.annotation.Subcommand
import ltd.matrixstudios.alchemist.AlchemistSpigotPlugin
import ltd.matrixstudios.alchemist.profiles.AsyncGameProfile
import ltd.matrixstudios.alchemist.profiles.BukkitProfileAdaptation
import ltd.matrixstudios.alchemist.profiles.commands.auth.menu.AuthSetupMenu
import ltd.matrixstudios.alchemist.profiles.getProfile
import ltd.matrixstudios.alchemist.service.profiles.ProfileGameService
import ltd.matrixstudios.alchemist.util.Chat
import ltd.matrixstudios.alchemist.util.SHA
import ltd.matrixstudios.alchemist.util.totp.TOTPUtil
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import java.security.GeneralSecurityException
import java.util.*
import java.util.concurrent.CompletableFuture
import java.util.concurrent.TimeUnit

@CommandAlias("auth|2fa")
@CommandPermission("alchemist.auth")
class AuthCommands : BaseCommand()
{

    @HelpCommand
    fun help(help: CommandHelp)
    {
        help.showHelp()
    }

    @Subcommand("status")
    @Description("View your current authentication status.")
    fun onAuthStatus(player: Player)
    {
        val profile = player.getProfile() ?: return

        player.sendMessage(" ")
        player.sendMessage(Chat.format("&eHello there, ${profile.getRankDisplay()}"))
        player.sendMessage(Chat.format("&eYou ${if (!profile.getAuthStatus().hasSetup2fa) "&cdo not" else "&ado"} &ehave 2fa enabled."))
        player.sendMessage(Chat.format("&eYour next authentication date is &d${Date(profile.getAuthStatus().lastAuthenticated.plus(TimeUnit.DAYS.toMillis(3L)))}&e."))
        player.sendMessage(Chat.format("&eIf you have &bAuthentication Bypass&e, you will not need to re-authenticate."))
        player.sendMessage(" ")
    }

    @Subcommand("bypass")
    @Description("Allow a user to bypass authentication.")
    @CommandPermission("alchemist.auth.admin")
    fun onBypass(player: Player, @Name("target") gameProfile: AsyncGameProfile)
    {
        gameProfile.use(player) {
            if (it.uuid == player.uniqueId)
            {
                player.sendMessage(Chat.format("&cFor security reasons, you are not able to change your authentication bypass."))
                return@use
            }

            val authStatus = it.getAuthStatus()

            if (it.hasMetadata("needsAuthetication"))
            {
                it.metadata.remove("needsAuthetication")
            }

            if (!authStatus.authBypassed)
            {
                authStatus.authBypassed = true
                it.authStatus = authStatus

                player.sendMessage(Chat.format("&eYou have set ${it.getRankDisplay()}'s &eauthentication bypass to true"))
            } else
            {
                authStatus.authBypassed = false
                it.authStatus = authStatus
                player.sendMessage(Chat.format("&eYou have removed ${it.getRankDisplay()}'s &eauthentication bypass"))
            }

            ProfileGameService.save(it)
        }
    }

    @Subcommand("reset")
    @Description("Reset a user's authentication status.")
    @CommandPermission("alchemist.auth.admin")
    fun onReset(commandSender: CommandSender, @Name("target") target: AsyncGameProfile) : CompletableFuture<Void>
    {
        return target.use(commandSender) {
            val authStatus = it.getAuthStatus()

            authStatus.lastAuthenticated = 0L
            it.authStatus = authStatus

            ProfileGameService.saveSync(it)
            commandSender.sendMessage(Chat.format("&eYou have reset ${it.getRankDisplay()}'s &eauthentication."))
        }
    }

    @Subcommand("verify")
    @Description("Verify with your code in order to gain access to the server.")
    fun onVerify(player: Player, @Name("code")code: String) : CompletableFuture<Void>
    {
        return CompletableFuture.runAsync {
            val profile = player.getProfile()

            if (profile == null)
            {
                player.sendMessage(Chat.format("&cYour profile cannot be null."))
                return@runAsync
            }

            val parse = code.replace(" ", "")
            val int: Int

            try
            {
                int = Integer.parseInt(parse)
            } catch (e: NumberFormatException)
            {
                player.sendMessage(Chat.format("&cInvalid integer. Cannot parse to code."))
                return@runAsync
            }

            val authProfile = profile.getAuthStatus()

            if (!BukkitProfileAdaptation.playerNeedsAuthenticating(profile, player))
            {
                player.sendMessage(Chat.format("&cYou have already authenticated in the last 3 days."))
                return@runAsync
            }

            if (authProfile.secret == "")
            {
                player.sendMessage(Chat.format("&cCannot setup authentication on a blank secret key."))
                return@runAsync
            }

            try
            {
                val codeIsCorrect = TOTPUtil.validateCurrentNumber(authProfile.secret, int, 250)

                if (!codeIsCorrect)
                {
                    player.sendMessage(Chat.format("&cThe code &e${code} &cis incorrect. Cannot authenticate you."))
                    return@runAsync
                } else
                {

                    authProfile.lastAuthenticated = System.currentTimeMillis()

                    if (!authProfile.hasSetup2fa)
                    {
                        authProfile.hasSetup2fa = true
                    }

                    //add their ip to allowed list if they pass this
                    //im sorry, but if you get your account hijacked
                    //and let them get your 2fa code too ur just dumb
                    val ip = SHA.toHexString(player.address.hostString)
                    if (ip != null && !authProfile.allowedIps.contains(ip))
                    {
                        authProfile.allowedIps.add(ip)
                    }

                    profile.authStatus = authProfile
                    if (profile.hasMetadata("needsAuthetication"))
                    {
                        profile.metadata.remove("needsAuthetication")
                    }

                    ProfileGameService.saveSync(profile)
                    player.sendMessage(Chat.format("&aYou have been successfully authenticated! Thank you for keeping the server safe :)"))
                }

            } catch (e: GeneralSecurityException)
            {
                player.sendMessage(Chat.format("&cDecryption error occurred. Contact an administrator."))
                return@runAsync
            }
        }
    }

    @Subcommand("setup")
    @Description("Set up your current Authentication Profile to match your needs.")
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
package ltd.matrixstudios.alchemist.profiles.commands.player

import co.aikar.commands.BaseCommand
import co.aikar.commands.CommandHelp
import co.aikar.commands.annotation.*
import com.google.gson.JsonObject
import ltd.matrixstudios.alchemist.models.profile.GameProfile
import ltd.matrixstudios.alchemist.profiles.AsyncGameProfile
import ltd.matrixstudios.alchemist.profiles.commands.player.menu.PlayerInformationMenu
import ltd.matrixstudios.alchemist.service.profiles.ProfileGameService
import ltd.matrixstudios.alchemist.util.Chat
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import java.util.*

@CommandAlias("useradmin|user|player|playeradmin")
@CommandPermission("alchemist.profiles.admin")
class PlayerAdminCommand : BaseCommand()
{

    @HelpCommand
    fun help(help: CommandHelp)
    {
        help.showHelp()
    }

    @Subcommand("info")
    @CommandCompletion("@gameprofile")
    fun info(player: Player, @Name("target") gameProfile: GameProfile)
    {
        PlayerInformationMenu(player, gameProfile).openMenu()
    }

    @Subcommand("test-async")
    @CommandCompletion("@gameprofile")
    fun asyncTest(player: Player, @Name("target") target: AsyncGameProfile)
    {
        target.use(player) {
            player.sendMessage(Chat.format("&eName: &f" + it.username))
            player.sendMessage(Chat.format("&eRank Display: &f" + it.getRankDisplay()))
            println(it.getCurrentRank().color + it.getCurrentRank().displayName)
        }.thenAccept {
            player.sendMessage(Chat.format("&aReached end of future"))
        }
    }
}
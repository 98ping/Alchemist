package ltd.matrixstudios.alchemist.profiles.commands.player

import co.aikar.commands.BaseCommand
import co.aikar.commands.CommandHelp
import co.aikar.commands.annotation.*
import co.aikar.commands.bukkit.contexts.OnlinePlayer
import com.google.gson.JsonObject
import ltd.matrixstudios.alchemist.api.AlchemistAPI
import ltd.matrixstudios.alchemist.profiles.commands.player.menu.PlayerInformationMenu
import ltd.matrixstudios.alchemist.models.profile.GameProfile
import ltd.matrixstudios.alchemist.profiles.AsyncGameProfile
import ltd.matrixstudios.alchemist.service.profiles.ProfileGameService
import ltd.matrixstudios.alchemist.util.Chat
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import java.util.UUID

@CommandAlias("useradmin|user|player|playeradmin")
@CommandPermission("alchemist.profiles.admin")
class PlayerAdminCommand : BaseCommand() {

    @HelpCommand
    fun help(help: CommandHelp) {
        help.showHelp()
    }

    @Subcommand("create")
    fun create(sender: CommandSender, @Name("username") name: String, @Name("uuid") uuid: String) {
        val found: UUID?

        try {
            found = UUID.fromString(uuid)
        } catch (e: IllegalArgumentException) {
            sender.sendMessage(Chat.format("&cThis UUID is invalid!"))
            return
        }

        val profile = GameProfile(found, name, name.toLowerCase(),
            JsonObject(), "0", arrayListOf(), arrayListOf(),
            null, null, null, arrayListOf(),
            System.currentTimeMillis(), null, null, 0, arrayListOf(), arrayListOf()
        )

        ProfileGameService.save(profile)
        sender.sendMessage(Chat.format("&aCreated a fake profile with the name &f$name &awith uuid &f$uuid"))
    }

    @Subcommand("info")
    @CommandCompletion("@gameprofile")
    fun info(player: Player,  @Name("target")gameProfile: GameProfile) {
        PlayerInformationMenu(player, gameProfile).openMenu()
    }

    @Subcommand("test-async")
    @CommandCompletion("@gameprofile")
    fun asyncTest(player: Player, @Name("target") target: AsyncGameProfile) {
        target.use(player) {
            player.sendMessage(Chat.format("&eName: &f" + it.username))
            player.sendMessage(Chat.format("&eRank Display: &f" + it.getRankDisplay()))
        }.thenAccept {
            player.sendMessage(Chat.format("&aReached end of future"))
        }
    }
}
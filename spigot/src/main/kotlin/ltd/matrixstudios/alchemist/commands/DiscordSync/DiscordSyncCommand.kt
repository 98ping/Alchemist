package ltd.matrixstudios.alchemist.commands.DiscordSync

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.*
import ltd.matrixstudios.alchemist.models.profile.GameProfile
import ltd.matrixstudios.alchemist.service.profiles.ProfileGameService
import ltd.matrixstudios.alchemist.util.Chat
import org.bukkit.entity.Player
import java.util.*

@CommandAlias("sync")
class DiscordSyncCommand : BaseCommand() {

    @Default
    fun sync(sender: Player) {

        val gameProfile = ProfileGameService.byId(sender.uniqueId) ?: run {
            sender.sendMessage("Player profile could not be found.")
            return
        }

        val currentCode = gameProfile.syncCode
        if (currentCode != null) {
            sender.sendMessage(Chat.format("&aHere is your &c&lsync code&a &f: $currentCode"))
            return
        }

        val uniqueCode = generateUniqueCode()

        gameProfile.syncCode = uniqueCode
        ProfileGameService.save(gameProfile)

        sender.sendMessage(Chat.format("&aThis is your &c&lsync&a &f code: $uniqueCode , &to use it you have to enter the discord and follow the steps of the SYNC channel."))
    }

    @Subcommand("check")
    @CommandCompletion("@gameprofile")
    @CommandPermission("head")
    fun check(sender: Player, @Name("target") gameProfile: GameProfile) {
        val syncCode = gameProfile.syncCode

        if (syncCode != null) {
            sender.sendMessage("The unique code of ${gameProfile.username} is: $syncCode")
        } else {
            sender.sendMessage("The player ${gameProfile.username} does not have a unique code or DiscordTag..")
        }
    }

    @Subcommand("delete")
    @CommandCompletion("@players")
    @CommandPermission("owner")
    fun delete(sender: Player, @Name("username") targetUsername: String) {
        val targetGameProfile = ProfileGameService.byId(sender.uniqueId)
        if (targetGameProfile != null) {
            targetGameProfile.syncCode = null
            ProfileGameService.save(targetGameProfile)
            sender.sendMessage("The unique code has been deleted from $targetUsername.")
        } else {
            sender.sendMessage("Player profile not found $targetUsername.")
        }
    }

    private fun generateUniqueCode(): String {
        val characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
        val random = Random()
        return (1..4).map { characters[random.nextInt(characters.length)] }.joinToString("")
    }
}

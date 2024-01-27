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
            sender.sendMessage("No se pudo encontrar el perfil del jugador.")
            return
        }

        val currentCode = gameProfile.syncCode
        if (currentCode != null) {
            sender.sendMessage(Chat.format("&aEste es tu código del &c&lsync&a &f: $currentCode"))
            return
        }

        val uniqueCode = generateUniqueCode()

        gameProfile.syncCode = uniqueCode
        ProfileGameService.save(gameProfile)

        sender.sendMessage(Chat.format("&aEste es tu código del &c&lsync&a &f: $uniqueCode , &apara usarlo tienes que entrar al discord y seguir los pasos del canal SYNC."))
    }

    @Subcommand("check")
    @CommandCompletion("@gameprofile")
    @CommandPermission("head")
    fun check(sender: Player, @Name("target") gameProfile: GameProfile) {
        val syncCode = gameProfile.syncCode

        if (syncCode != null) {
            sender.sendMessage("El código único de ${gameProfile.username} es: $syncCode")
        } else {
            sender.sendMessage("El jugador ${gameProfile.username} no tiene un código único ni DiscordTag.")
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
            sender.sendMessage("Se ha borrado el código único de $targetUsername.")
        } else {
            sender.sendMessage("No se encontró el perfil del jugador $targetUsername.")
        }
    }

    private fun generateUniqueCode(): String {
        val characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
        val random = Random()
        return (1..4).map { characters[random.nextInt(characters.length)] }.joinToString("")
    }
}

package ltd.matrixstudios.alchemist.punishment.commands.remove

/**
 * Class created on 8/24/2023

 * @author 98ping
 * @project Alchemist
 * @website https://solo.to/redis
 */
import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.*
import ltd.matrixstudios.alchemist.models.profile.GameProfile
import ltd.matrixstudios.alchemist.punishment.BukkitPunishmentFunctions
import ltd.matrixstudios.alchemist.punishments.PunishmentType
import ltd.matrixstudios.alchemist.util.Chat
import org.bukkit.command.CommandSender

class UnghostmuteCommand : BaseCommand() {

    @CommandAlias("unghostmute|ungm")
    @CommandPermission("alchemist.punishments.unghostmute")
    @CommandCompletion("@gameprofile")
    fun unghostmute(sender: CommandSender, @Name("target") gameProfile: GameProfile, @Name("reason") reason: String) {
        val punishments = gameProfile.getActivePunishments(PunishmentType.GHOST_MUTE)

        if (punishments.isEmpty()) {
            sender.sendMessage(Chat.format("&cNo punishments of this type"))
            return
        }

        val punishment = punishments.first()

        punishment.expirable.expired = true
        punishment.expirable.removedAt = System.currentTimeMillis()
        punishment.removedBy = BukkitPunishmentFunctions.getSenderUUID(sender)
        punishment.removedReason = reason

        sender.sendMessage(Chat.format((if (BukkitPunishmentFunctions.isSilent(reason)) "&7(Silent) " else "")
                + "&aYou've un-ghost muted " + gameProfile.username + " for &f"
                + BukkitPunishmentFunctions.parseReason(reason)))
        BukkitPunishmentFunctions.remove(BukkitPunishmentFunctions.getSenderUUID(sender), punishment, true, reason)
    }
}
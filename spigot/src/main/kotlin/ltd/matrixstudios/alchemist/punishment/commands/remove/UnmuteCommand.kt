package ltd.matrixstudios.alchemist.punishment.commands.remove

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.*
import ltd.matrixstudios.alchemist.models.profile.GameProfile
import ltd.matrixstudios.alchemist.punishment.BukkitPunishmentFunctions
import ltd.matrixstudios.alchemist.punishments.PunishmentType
import ltd.matrixstudios.alchemist.util.Chat
import org.bukkit.command.CommandSender

class UnmuteCommand : BaseCommand() {

    @CommandAlias("unmute|unm")
    @CommandPermission("alchemist.punishments.unmute")
    @CommandCompletion("@gameprofile")
    fun ban(sender: CommandSender, @Name("target") gameProfile: GameProfile, @Name("reason") reason: String) {
        val punishments = gameProfile.getActivePunishments(PunishmentType.MUTE)

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
                + "&aYou've unmuted " + gameProfile.username + " for &f"
                + BukkitPunishmentFunctions.parseReason(reason)))
        BukkitPunishmentFunctions.remove(BukkitPunishmentFunctions.getSenderUUID(sender), punishment, true)

    }

}
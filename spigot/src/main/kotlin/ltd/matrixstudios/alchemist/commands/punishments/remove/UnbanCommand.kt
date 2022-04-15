package ltd.matrixstudios.alchemist.commands.punishments.remove

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.*
import ltd.matrixstudios.alchemist.models.grant.types.Punishment
import ltd.matrixstudios.alchemist.models.profile.GameProfile
import ltd.matrixstudios.alchemist.punishment.BukkitPunishmentFunctions
import ltd.matrixstudios.alchemist.punishments.PunishmentType
import ltd.matrixstudios.alchemist.punishments.actor.ActorType
import ltd.matrixstudios.alchemist.punishments.actor.DefaultActor
import ltd.matrixstudios.alchemist.util.Chat
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender

class UnbanCommand  : BaseCommand() {

    @CommandAlias("unban|unb")
    @CommandPermission("alchemist.punishments.unban")
    fun ban(sender: CommandSender, @Name("target") gameProfile: GameProfile, @Optional @Name("s") silent: Boolean, @Name("reason") reason: String) {
        val punishments = gameProfile.getActivePunishments(PunishmentType.BAN)

        if (punishments.isEmpty()) {
            sender.sendMessage(Chat.format("&cNo punishments of this type"))
            return
        }

        val punishment = punishments.first()

        punishment.expirable.expired = true
        punishment.expirable.removedAt = System.currentTimeMillis()
        punishment.removedBy = BukkitPunishmentFunctions.getSenderUUID(sender)
        punishment.removedReason = reason

        BukkitPunishmentFunctions.remove(punishment, silent != null)

    }

}
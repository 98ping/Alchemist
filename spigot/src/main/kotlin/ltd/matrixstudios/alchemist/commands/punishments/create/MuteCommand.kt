package ltd.matrixstudios.alchemist.commands.punishments.create

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.*
import ltd.matrixstudios.alchemist.models.grant.types.Punishment
import ltd.matrixstudios.alchemist.models.grant.types.proof.ProofEntry
import ltd.matrixstudios.alchemist.models.profile.GameProfile
import ltd.matrixstudios.alchemist.punishment.BukkitPunishmentFunctions
import ltd.matrixstudios.alchemist.punishment.limitation.PunishmentLimitationUnderstander
import ltd.matrixstudios.alchemist.punishments.PunishmentType
import ltd.matrixstudios.alchemist.punishments.actor.ActorType
import ltd.matrixstudios.alchemist.punishments.actor.DefaultActor
import ltd.matrixstudios.alchemist.util.Chat
import ltd.matrixstudios.alchemist.util.TimeUtil
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import java.util.*

class MuteCommand : BaseCommand() {

    @CommandAlias("mute|pmute")
    @CommandPermission("alchemist.punishments.mute")
    @CommandCompletion("@gameprofile")
    @Syntax("<target> [-a] <reason>")
    fun ban(sender: CommandSender, @Name("target") gameProfile: GameProfile, @Name("reason") reason: String) {
        val punishment = Punishment(
            PunishmentType.MUTE.name,
            UUID.randomUUID().toString().substring(0, 4),
            mutableListOf<ProofEntry>(),
            gameProfile.uuid,
            BukkitPunishmentFunctions.getSenderUUID(sender),
            BukkitPunishmentFunctions.parseReason(reason, "Unspecified"), Long.MAX_VALUE,

            DefaultActor(
                BukkitPunishmentFunctions.getExecutorFromSender(sender),
                ActorType.GAME)

        )

        val hasPunishment = gameProfile.hasActivePunishment(PunishmentType.MUTE)

        if (hasPunishment)
        {
            sender.sendMessage(Chat.format("&cPlayer is already muted!"))
            return
        }

        if (sender is Player) {

            val canExecute =
                PunishmentLimitationUnderstander.canApplyPunishment(sender.uniqueId)

            if (!canExecute) {
                sender.sendMessage(Chat.format("&cYou are currently on punishment cooldown."))
                sender.sendMessage(Chat.format("&cPlease wait &e" + PunishmentLimitationUnderstander.getDurationString(sender.uniqueId)))

                return
            }

            PunishmentLimitationUnderstander.equipCooldown(sender.uniqueId)
        }

        BukkitPunishmentFunctions.dispatch(punishment, BukkitPunishmentFunctions.isSilent(reason))

    }

}
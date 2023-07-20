package ltd.matrixstudios.alchemist.commands.punishments.redo

/**
 * Class created on 7/20/2023

 * @author 98ping
 * @project Alchemist
 * @website https://solo.to/redis
 */
import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.*
import co.aikar.commands.annotation.Optional
import ltd.matrixstudios.alchemist.Alchemist
import ltd.matrixstudios.alchemist.api.AlchemistAPI
import ltd.matrixstudios.alchemist.models.grant.types.Punishment
import ltd.matrixstudios.alchemist.models.grant.types.proof.ProofEntry
import ltd.matrixstudios.alchemist.models.profile.GameProfile
import ltd.matrixstudios.alchemist.packets.OwnershipMessagePacket
import ltd.matrixstudios.alchemist.punishment.BukkitPunishmentFunctions
import ltd.matrixstudios.alchemist.punishment.limitation.PunishmentLimitationUnderstander
import ltd.matrixstudios.alchemist.punishments.PunishmentType
import ltd.matrixstudios.alchemist.punishments.actor.ActorType
import ltd.matrixstudios.alchemist.punishments.actor.DefaultActor
import ltd.matrixstudios.alchemist.punishments.actor.executor.Executor
import ltd.matrixstudios.alchemist.redis.AsynchronousRedisSender
import ltd.matrixstudios.alchemist.service.expirable.PunishmentService
import ltd.matrixstudios.alchemist.util.Chat
import ltd.matrixstudios.alchemist.util.TimeUtil
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import java.util.*

class RebanCommand : BaseCommand() {

    @CommandAlias("reban|rb")
    @CommandPermission("alchemist.punishments.reban")
    @CommandCompletion("@gameprofile")
    @Syntax("<target> [-a] <reason>")
    fun reban(sender: CommandSender, @Name("target") gameProfile: GameProfile, @Name("reason") reason: String) {
        val punishment = Punishment(
            PunishmentType.BAN.name,
            UUID.randomUUID().toString().substring(0, 4),
            mutableListOf(),
            gameProfile.uuid,
            BukkitPunishmentFunctions.getSenderUUID(sender),
            BukkitPunishmentFunctions.parseReason(reason, "Unspecified"), Long.MAX_VALUE,

            DefaultActor(
                BukkitPunishmentFunctions.getExecutorFromSender(sender),
                ActorType.GAME)
        )

        val hasPunishment = gameProfile.getActivePunishments(PunishmentType.BAN).firstOrNull()

        if (hasPunishment == null) {
            sender.sendMessage(Chat.format("&cPlayer has no bans that need to be removed!"))
            return
        }

        if (sender is Player) {

            val profile = AlchemistAPI.syncFindProfile(sender.uniqueId)!!
            val canExecute =
                PunishmentLimitationUnderstander.canApplyPunishment(sender.uniqueId)

            if (!canExecute) {
                sender.sendMessage(Chat.format("&cYou are currently on punishment cooldown."))
                sender.sendMessage(Chat.format("&cPlease wait &e" + PunishmentLimitationUnderstander.getDurationString(sender.uniqueId)))

                return
            }

            if (!BukkitPunishmentFunctions.playerCanPunishOther(profile, gameProfile)) {
                sender.sendMessage(Chat.format("&cYou are not eligible to punish this player!"))
                AsynchronousRedisSender.send(OwnershipMessagePacket("&b[S] &3[${Alchemist.globalServer.displayName}] ${profile.getRankDisplay()} &3tried punishing a player with a rank weight higher than theirs"))
                return
            }

            PunishmentLimitationUnderstander.equipCooldown(sender.uniqueId)
        }

        //remove previous
        hasPunishment.expirable.expired = true
        hasPunishment.expirable.removedAt = System.currentTimeMillis()
        hasPunishment.removedBy = BukkitPunishmentFunctions.getSenderUUID(sender)
        hasPunishment.removedReason = "Punishment Re-Executed"

        PunishmentService.save(hasPunishment)

        //create new
        sender.sendMessage(Chat.format((if (BukkitPunishmentFunctions.isSilent(reason)) "&7(Silent) " else "")
                + "&aYou've re-banned " + gameProfile.username + " for &f"
                + BukkitPunishmentFunctions.parseReason(reason) + " &afor "
                + TimeUtil.formatDuration(punishment.expirable.duration)))
        BukkitPunishmentFunctions.dispatch(punishment, BukkitPunishmentFunctions.isSilent(reason))
    }

}
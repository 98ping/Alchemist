package ltd.matrixstudios.alchemist.punishment

import ltd.matrixstudios.alchemist.models.grant.types.Punishment
import ltd.matrixstudios.alchemist.punishment.packets.PunishmentDispatchPacket
import ltd.matrixstudios.alchemist.punishment.packets.PunishmentExecutePacket
import ltd.matrixstudios.alchemist.punishment.packets.PunishmentRemovePacket
import ltd.matrixstudios.alchemist.punishments.actor.executor.Executor
import ltd.matrixstudios.alchemist.redis.AsynchronousRedisSender
import ltd.matrixstudios.alchemist.profiles.cache.UpdatePunishmentsRequest
import ltd.matrixstudios.alchemist.models.profile.GameProfile
import ltd.matrixstudios.alchemist.service.expirable.PunishmentService
import ltd.matrixstudios.alchemist.webhook.types.punishments.PunishmentNotification
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import java.util.*

object BukkitPunishmentFunctions {

    fun getSenderUUID(sender: CommandSender) : UUID {
        return if (sender is Player) {
            sender.uniqueId
        } else UUID.fromString("00000000-0000-0000-0000-000000000000")
    }

    fun getExecutorFromSender(sender: CommandSender) : Executor {
        if (sender is Player) return Executor.PLAYER

        return Executor.CONSOLE
    }

    fun remove(executor: UUID, punishment: Punishment, silent: Boolean) {
        AsynchronousRedisSender.send(PunishmentRemovePacket(punishment.getGrantable(), punishment.target, executor, silent, punishment.reason))
        PunishmentService.save(punishment)
    }

    fun dispatch(punishment: Punishment, silent: Boolean) {
        PunishmentService.save(punishment)
        PunishmentNotification(punishment).send()

        AsynchronousRedisSender.send(PunishmentDispatchPacket(punishment.getGrantable(), punishment.target, punishment.executor, silent, punishment.reason))
        AsynchronousRedisSender.send(PunishmentExecutePacket(punishment.getGrantable(), punishment.target, punishment.reason, punishment))
        AsynchronousRedisSender.send(UpdatePunishmentsRequest(punishment.target))
    }

    fun dispatchKick(punishment: Punishment, silent: Boolean) {
        AsynchronousRedisSender.send(PunishmentDispatchPacket(punishment.getGrantable(), punishment.target, punishment.executor, silent, punishment.reason))
        AsynchronousRedisSender.send(PunishmentExecutePacket(punishment.getGrantable(), punishment.target, punishment.reason, punishment))
    }

    fun isSilent(reason: String) : Boolean {
        if (reason.endsWith("-a", ignoreCase = true) || reason.startsWith("-a", ignoreCase = true))
        {
            return false
        }

        return true
    }

    fun playerCanPunishOther(executor: GameProfile, target: GameProfile) : Boolean {
        val rankWeightExec = executor.getCurrentRank()?.weight ?: 1
        val rankWeightTarget = target.getCurrentRank()?.weight ?: 1

        return (rankWeightExec >= rankWeightTarget)
    }

    fun parseReason(
        reason: String?,
        fallback: String = "Unfair Advantage"
    ): String
    {
        var preParsedReason = reason ?: fallback
        preParsedReason = preParsedReason.removePrefix("-a ")
        preParsedReason = preParsedReason.removeSuffix(" -a")

        preParsedReason = preParsedReason.removePrefix("-A ")
        preParsedReason = preParsedReason.removeSuffix(" -A")

        preParsedReason = preParsedReason.removePrefix("-A")
        preParsedReason = preParsedReason.removeSuffix("-A")

        preParsedReason = preParsedReason.removePrefix("-a")
        preParsedReason = preParsedReason.removeSuffix("-a")


        return preParsedReason.ifBlank { fallback }
    }
}
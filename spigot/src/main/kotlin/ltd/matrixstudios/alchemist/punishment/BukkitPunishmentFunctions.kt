package ltd.matrixstudios.alchemist.punishment

import ltd.matrixstudios.alchemist.models.grant.types.Punishment
import ltd.matrixstudios.alchemist.punishment.packets.PunishmentDispatchPacket
import ltd.matrixstudios.alchemist.punishment.packets.PunishmentExecutePacket
import ltd.matrixstudios.alchemist.punishment.packets.PunishmentRemovePacket
import ltd.matrixstudios.alchemist.punishments.actor.executor.Executor
import ltd.matrixstudios.alchemist.redis.AsynchronousRedisSender
import ltd.matrixstudios.alchemist.redis.RedisPacketManager
import ltd.matrixstudios.alchemist.service.expirable.PunishmentService
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import java.util.*

object BukkitPunishmentFunctions {

    fun getSenderUUID(sender: CommandSender) : UUID {
        if (sender is Player) {
            return sender.uniqueId
        } else return UUID.fromString("00000000-0000-0000-0000-000000000000")
    }

    fun getExecutorFromSender(sender: CommandSender) : Executor {
        if (sender is Player) return Executor.PLAYER

        return Executor.CONSOLE
    }

    fun remove(executor: UUID, punishment: Punishment, silent: Boolean) {
        AsynchronousRedisSender.send(PunishmentRemovePacket(punishment.getGrantable(), punishment.target, executor, silent))
        PunishmentService.save(punishment)
    }

    fun dispatch(punishment: Punishment, silent: Boolean) {
        AsynchronousRedisSender.send(PunishmentDispatchPacket(punishment.getGrantable(), punishment.target, punishment.executor, silent))
        AsynchronousRedisSender.send(PunishmentExecutePacket(punishment.getGrantable(), punishment.target, punishment.reason))

        PunishmentService.save(punishment)
    }
}
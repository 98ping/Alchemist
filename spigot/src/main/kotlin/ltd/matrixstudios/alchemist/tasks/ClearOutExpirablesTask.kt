package ltd.matrixstudios.alchemist.tasks

import ltd.matrixstudios.alchemist.permissions.packet.PermissionUpdatePacket
import ltd.matrixstudios.alchemist.redis.AsynchronousRedisSender
import ltd.matrixstudios.alchemist.service.expirable.PunishmentService
import ltd.matrixstudios.alchemist.service.expirable.RankGrantService
import org.bukkit.scheduler.BukkitRunnable
import java.util.*

object ClearOutExpirablesTask : BukkitRunnable() {

    override fun run() {
        for (grant in RankGrantService.cache.values)
        {
            grant.forEach {
                if (!it.expirable.isActive() && it.removedBy == null) {
                    it.removedBy = UUID.fromString("00000000-0000-0000-0000-000000000000")
                    it.removedReason = "Expired"

                    AsynchronousRedisSender.send(PermissionUpdatePacket(it.target))

                    RankGrantService.save(it)
                }
            }
        }

        PunishmentService.getValues().forEach {
            if (!it.expirable.isActive() && it.removedBy == null)
            {
                it.removedBy = UUID.fromString("00000000-0000-0000-0000-000000000000")
                it.removedReason = "Expired"

                AsynchronousRedisSender.send(PermissionUpdatePacket(it.target))
                PunishmentService.save(it)
            }
        }
    }
}
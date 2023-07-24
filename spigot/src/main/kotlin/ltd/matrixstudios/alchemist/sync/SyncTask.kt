package ltd.matrixstudios.alchemist.sync

import ltd.matrixstudios.alchemist.redis.AsynchronousRedisSender
import ltd.matrixstudios.alchemist.redis.cache.RefreshFiltersPacket
import ltd.matrixstudios.alchemist.redis.cache.RefreshRankPacket
import org.bukkit.scheduler.BukkitRunnable

class SyncTask : BukkitRunnable() {

    override fun run() {
        AsynchronousRedisSender.send(RefreshRankPacket())
        AsynchronousRedisSender.send(RefreshFiltersPacket())
    }

}
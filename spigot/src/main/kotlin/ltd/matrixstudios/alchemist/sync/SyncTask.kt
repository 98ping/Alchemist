package ltd.matrixstudios.alchemist.sync

import ltd.matrixstudios.alchemist.redis.AsynchronousRedisSender
import ltd.matrixstudios.alchemist.redis.cache.refresh.RefreshFiltersPacket
import ltd.matrixstudios.alchemist.redis.cache.refresh.RefreshRankPacket
import ltd.matrixstudios.alchemist.redis.cache.refresh.RefreshServersPacket
import org.bukkit.scheduler.BukkitRunnable

class SyncTask : BukkitRunnable() {

    override fun run() {
        AsynchronousRedisSender.send(RefreshRankPacket())
        AsynchronousRedisSender.send(RefreshFiltersPacket())
        AsynchronousRedisSender.send(RefreshServersPacket())
    }
}
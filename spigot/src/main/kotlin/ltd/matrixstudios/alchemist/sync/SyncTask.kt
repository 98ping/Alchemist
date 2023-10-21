package ltd.matrixstudios.alchemist.sync

import ltd.matrixstudios.alchemist.redis.AsynchronousRedisSender
import ltd.matrixstudios.alchemist.redis.cache.refresh.RefreshFiltersPacket
import ltd.matrixstudios.alchemist.redis.cache.refresh.RefreshRankPacket
import ltd.matrixstudios.alchemist.redis.cache.refresh.RefreshServersPacket
import org.bukkit.scheduler.BukkitRunnable

class SyncTask : BukkitRunnable()
{

    override fun run()
    {
        val packets = listOf(RefreshRankPacket(), RefreshFiltersPacket(), RefreshServersPacket())

        for (packet in packets)
        {
            AsynchronousRedisSender.send(packet)
        }
    }
}
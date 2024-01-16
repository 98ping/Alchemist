package ltd.matrixstudios.alchemist.redis.data.packet

import ltd.matrixstudios.alchemist.redis.RedisPacket
import ltd.matrixstudios.alchemist.redis.data.RedisDataSyncService

class RedisModelPopulationPacket(
    val id: String,
) : RedisPacket("redis-populate-packet")
{
    override fun action()
    {
        RedisDataSyncService.syncServices[id]?.load()
    }
}
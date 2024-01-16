package ltd.matrixstudios.alchemist.redis.data.packet

import ltd.matrixstudios.alchemist.redis.RedisPacket
import ltd.matrixstudios.alchemist.redis.data.RedisDataSyncService

class RedisModelPopulationPacket<V>(
    val id: String,
    val toUpdate: V
) : RedisPacket("redis-populate-packet")
{
    override fun action()
    {
        RedisDataSyncService.syncModel(id, toUpdate)
    }
}
package ltd.matrixstudios.alchemist.redis.data.packet

import ltd.matrixstudios.alchemist.redis.RedisPacket
import ltd.matrixstudios.alchemist.redis.RedisPacketManager
import ltd.matrixstudios.alchemist.redis.data.RedisDataSyncService

class RedisModelPopulationPacket<V>(
    val id: String,
    val newModel: String,
    val clazz: Class<V>
) : RedisPacket("redis-populate-packet")
{
    override fun action()
    {
        println(clazz)
        println(newModel)
        println(RedisPacketManager.gson.fromJson(newModel, clazz))
        RedisDataSyncService.syncModel(id, RedisPacketManager.gson.fromJson(newModel, clazz))
    }
}
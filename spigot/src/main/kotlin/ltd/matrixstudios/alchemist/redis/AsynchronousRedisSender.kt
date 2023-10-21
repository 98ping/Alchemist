package ltd.matrixstudios.alchemist.redis

import java.util.concurrent.ForkJoinPool

object AsynchronousRedisSender
{

    var totalPacketCount = 0

    fun send(packet: RedisPacket)
    {
        totalPacketCount++
        ForkJoinPool.commonPool().execute {
            RedisPacketManager.pool.resource.use { jedis ->
                val encodedPacket = packet.javaClass.name + "|" + RedisPacketManager.gson.toJson(packet)
                jedis.publish("Alchemist||Packets||", encodedPacket)
            }
        }
    }
}
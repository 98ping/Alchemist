package ltd.matrixstudios.alchemist.redis

import ltd.matrixstudios.alchemist.AlchemistBungee

object BungeeRedisSender {

    fun send(packet: RedisPacket) {
        AlchemistBungee.instance.proxy.scheduler.runAsync(AlchemistBungee.instance)
        {
            RedisPacketManager.pool.resource.use { jedis ->
                val encodedPacket = packet.javaClass.name + "|" + RedisPacketManager.gson.toJson(packet)
                jedis.publish("Alchemist||Packets||", encodedPacket)
            }
        }
    }
}
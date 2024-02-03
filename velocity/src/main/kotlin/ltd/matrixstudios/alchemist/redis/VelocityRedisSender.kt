package ltd.matrixstudios.alchemist.redis

import ltd.matrixstudios.alchemist.AlchemistVelocity

object VelocityRedisSender {
    fun send(packet: RedisPacket) {
        AlchemistVelocity.instance.server.scheduler.buildTask(AlchemistVelocity.instance) {
            RedisPacketManager.pool.resource.use { jedis ->
                val encodedPacket = packet.javaClass.name + "|" + RedisPacketManager.gson.toJson(packet)
                jedis.publish("Alchemist||Packets||", encodedPacket)
            }
        }.schedule()
    }
}
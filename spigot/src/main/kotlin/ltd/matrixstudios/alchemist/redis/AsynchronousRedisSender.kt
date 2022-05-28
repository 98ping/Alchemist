package ltd.matrixstudios.alchemist.redis

import ltd.matrixstudios.alchemist.AlchemistSpigotPlugin
import org.bukkit.Bukkit

object AsynchronousRedisSender {

    fun send(packet: RedisPacket) {
        Bukkit.getScheduler().runTaskAsynchronously(AlchemistSpigotPlugin.instance) {
            RedisPacketManager.pool.resource.use { jedis ->
                val encodedPacket = packet.javaClass.name + "|" + RedisPacketManager.gson.toJson(packet)
                jedis.publish("Alchemist||Packets||", encodedPacket)
            }
        }
    }
}
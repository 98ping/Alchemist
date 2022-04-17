package ltd.matrixstudios.alchemist.redis

import ltd.matrixstudios.alchemist.AlchemistSpigotPlugin
import org.bukkit.Bukkit
import org.bukkit.scheduler.BukkitRunnable
import kotlin.concurrent.thread

object AsynchronousRedisSender {

    fun send(redisPacket: RedisPacket) {
        val dataToSend = "${redisPacket.javaClass.name}|${RedisPacketManager.redisGson.toJson(redisPacket)}"

        RedisPacketManager.pool.resource.publish("Alchemist||Packets", dataToSend)
    }

}


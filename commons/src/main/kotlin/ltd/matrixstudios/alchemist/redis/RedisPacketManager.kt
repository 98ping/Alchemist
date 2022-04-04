package ltd.matrixstudios.alchemist.redis

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import ltd.matrixstudios.alchemist.Alchemist
import org.apache.commons.pool2.impl.GenericObjectPoolConfig
import redis.clients.jedis.JedisPool
import java.util.concurrent.ForkJoinPool

object RedisPacketManager {

    lateinit var pool: JedisPool

    var redisGson: Gson = GsonBuilder().setPrettyPrinting().serializeNulls().create()

    fun load(host: String) {
        pool = JedisPool(host, 6379)

        ForkJoinPool.commonPool().execute {
            pool.resource.subscribe(RedisPacketPubSub(), "Andromeda||Packets")
        }
    }

    fun send(redisPacket: RedisPacket) {
        ForkJoinPool.commonPool().execute {
            val dataToSend = redisGson.toJson(redisPacket)

            pool.resource.publish("Alchemist||Packets", dataToSend)
        }
    }
}
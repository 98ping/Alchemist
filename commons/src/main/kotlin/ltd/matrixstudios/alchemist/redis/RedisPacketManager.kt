package ltd.matrixstudios.alchemist.redis

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import ltd.matrixstudios.alchemist.Alchemist
import org.apache.commons.pool2.impl.GenericObjectPoolConfig
import redis.clients.jedis.JedisPool
import java.util.concurrent.ForkJoinPool
import kotlin.concurrent.thread

object  RedisPacketManager {

    lateinit var pool: JedisPool

    var redisGson: Gson = GsonBuilder().setPrettyPrinting().serializeNulls().create()

    fun load(host: String, port: Int, password: String?, username: String?) {
        pool = JedisPool(host, port, username, password)

        thread {
            pool.resource.subscribe(RedisPacketPubSub(), "Alchemist||Packets")
        }
    }

}
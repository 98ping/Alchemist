package ltd.matrixstudios.alchemist.redis

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.LongSerializationPolicy
import io.github.nosequel.data.DataHandler
import io.github.nosequel.data.connection.redis.NoAuthRedisConnectionPool
import io.github.nosequel.data.connection.redis.RedisConnectionPool
import ltd.matrixstudios.alchemist.Alchemist
import org.apache.commons.pool2.impl.GenericObjectPoolConfig
import redis.clients.jedis.JedisPool
import java.util.concurrent.ForkJoinPool
import kotlin.concurrent.thread

object  RedisPacketManager {

    lateinit var pool: JedisPool

    var gson: Gson = GsonBuilder().setLongSerializationPolicy(LongSerializationPolicy.STRING).serializeNulls().create()

    fun load(host: String, port: Int, password: String?, username: String?) {
        pool = JedisPool(host, port, username, password)
    }
}
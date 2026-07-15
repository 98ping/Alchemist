package ltd.matrixstudios.alchemist.redis

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.LongSerializationPolicy
import redis.clients.jedis.JedisPool

object RedisPacketManager {

    lateinit var pool: JedisPool

    var gson: Gson = GsonBuilder().setLongSerializationPolicy(LongSerializationPolicy.STRING).serializeNulls().create()

    fun load(host: String, port: Int, password: String?, username: String?) {
        pool = JedisPool(host, port, username, password)
    }
}
package ltd.matrixstudios.alchemist.redis

import ltd.matrixstudios.alchemist.Alchemist
import org.apache.commons.pool2.impl.GenericObjectPoolConfig
import redis.clients.jedis.JedisPool
import java.util.concurrent.ForkJoinPool

object RedisPacketManager {

    lateinit var pool: JedisPool

    fun load(host: String) {
        pool = JedisPool(host, 6379)


        //pool.resource.subscribe(RedisPacketPubSub(), "Andromeda||Packets")

    }

    fun send(redisPacket: RedisPacket) {
        ForkJoinPool.commonPool().execute {
            val dataToSend = Alchemist.dataflow.mainSerializer.deserialize(redisPacket)

            pool.resource.publish("Alchemist||Packets", dataToSend)
        }
    }
}
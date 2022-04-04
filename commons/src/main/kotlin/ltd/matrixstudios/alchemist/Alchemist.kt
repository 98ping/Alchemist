package ltd.matrixstudios.alchemist

import io.github.nosequel.data.DataHandler
import io.github.nosequel.data.connection.mongo.MongoConnectionPool
import io.github.nosequel.data.serializer.type.GsonSerialization
import ltd.matrixstudios.alchemist.redis.RedisPacket
import ltd.matrixstudios.alchemist.redis.RedisPacketManager

object Alchemist {

    lateinit var MongoConnectionPool: MongoConnectionPool

    lateinit var dataHandler: DataHandler

    fun start(mongoConnectionPool: MongoConnectionPool, redisHost: String) {
        this.MongoConnectionPool = mongoConnectionPool


        this.dataHandler = DataHandler.withConnectionPool(mongoConnectionPool)

        RedisPacketManager.load(redisHost)

    }
}
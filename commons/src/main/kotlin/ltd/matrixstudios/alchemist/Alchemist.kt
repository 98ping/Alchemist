package ltd.matrixstudios.alchemist

import io.github.nosequel.data.DataHandler
import io.github.nosequel.data.connection.mongo.MongoConnectionPool
import ltd.matrixstudios.alchemist.redis.RedisPacketManager
import ltd.matrixstudios.alchemist.service.ranks.RankService

object Alchemist {

    lateinit var MongoConnectionPool: MongoConnectionPool

    lateinit var dataHandler: DataHandler

    fun start(mongoConnectionPool: MongoConnectionPool, redisHost: String, redisPort: Int, redisUsername: String?, redisPassword: String?) {
        this.MongoConnectionPool = mongoConnectionPool


        this.dataHandler = DataHandler.withConnectionPool(mongoConnectionPool)

        RedisPacketManager.load(redisHost, redisPort, redisPassword, redisUsername)

        RankService.createDefaultRankIfDoesntExist()

    }
}
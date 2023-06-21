package ltd.matrixstudios.alchemist

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.LongSerializationPolicy
import io.github.nosequel.data.DataHandler
import io.github.nosequel.data.connection.mongo.MongoConnectionPool
import ltd.matrixstudios.alchemist.models.server.UniqueServer
import ltd.matrixstudios.alchemist.redis.RedisPacketManager
import ltd.matrixstudios.alchemist.service.filter.FilterService
import ltd.matrixstudios.alchemist.service.ranks.RankService

object Alchemist {

    lateinit var MongoConnectionPool: MongoConnectionPool

    lateinit var dataHandler: DataHandler

    lateinit var globalServer: UniqueServer

    var gson: Gson = GsonBuilder().setLongSerializationPolicy(LongSerializationPolicy.STRING).serializeNulls().create()

    fun start(mongoConnectionPool: MongoConnectionPool, redisHost: String, redisPort: Int, redisUsername: String?, redisPassword: String?) {
        this.MongoConnectionPool = mongoConnectionPool


        this.dataHandler = DataHandler.withConnectionPool(mongoConnectionPool)

        RedisPacketManager.load(redisHost, redisPort, redisPassword, redisUsername)

        RankService.loadRanks()
        FilterService.loadIntoCache()
    }
}
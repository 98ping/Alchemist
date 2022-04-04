package ltd.matrixstudios.alchemist

import ltd.matrixstudios.alchemist.redis.RedisPacket
import ltd.matrixstudios.alchemist.redis.RedisPacketManager
import ltd.matrixstudios.mongo.MongoDataFlow
import ltd.matrixstudios.mongo.credientials.MongoPoolConnection

object Alchemist {

    lateinit var dataflow: MongoDataFlow

    fun start(poolConnection: MongoPoolConnection, redisHost: String) {
        dataflow = MongoDataFlow().of().setPool(poolConnection).setDatabase("Alchemist")

        RedisPacketManager.load(redisHost)

    }
}
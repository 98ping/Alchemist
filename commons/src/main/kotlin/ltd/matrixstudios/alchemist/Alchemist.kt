package ltd.matrixstudios.alchemist

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.LongSerializationPolicy
import io.github.nosequel.data.DataHandler
import io.github.nosequel.data.DataStoreType
import io.github.nosequel.data.connection.flatfile.FlatfileConnectionPool
import io.github.nosequel.data.connection.mongo.MongoConnectionPool
import io.github.nosequel.data.store.StoreType
import io.github.nosequel.data.store.type.MongoStoreType
import ltd.matrixstudios.alchemist.cache.types.UUIDCache
import ltd.matrixstudios.alchemist.models.server.UniqueServer
import ltd.matrixstudios.alchemist.redis.RedisPacketManager
import ltd.matrixstudios.alchemist.service.filter.FilterService
import ltd.matrixstudios.alchemist.service.profiles.ProfileGameService
import ltd.matrixstudios.alchemist.service.queue.QueueService
import ltd.matrixstudios.alchemist.service.ranks.RankService
import ltd.matrixstudios.alchemist.service.server.UniqueServerService
import ltd.matrixstudios.alchemist.service.tags.TagService
import kotlin.properties.Delegates

object Alchemist
{

    //connection
    lateinit var MongoConnectionPool: MongoConnectionPool
    lateinit var FlatFileConnectionPool: FlatfileConnectionPool

    lateinit var dataHandler: DataHandler

    //global properties
    lateinit var globalServer: UniqueServer
    var redisConnectionPort by Delegates.notNull<Int>()

    // store type information
    var usingMongo = false

    var gson: Gson = GsonBuilder()
        .setLongSerializationPolicy(LongSerializationPolicy.STRING)
        .serializeNulls().create()

    fun start(
        useMongo: Boolean,
        mongoConnectionPool: MongoConnectionPool?,
        needsRedis: Boolean,
        redisHost: String,
        redisPort: Int,
        redisUsername: String?,
        redisPassword: String?,
        directory: String? = null
    )
    {
        if (useMongo)
        {
            this.MongoConnectionPool = mongoConnectionPool!!
            this.dataHandler = DataHandler.withConnectionPool(mongoConnectionPool)

            usingMongo = true
        } else
        {
            this.MongoConnectionPool = mongoConnectionPool!!
            this.FlatFileConnectionPool = FlatfileConnectionPool().apply {
                this.directory = directory
            }

            this.dataHandler = DataHandler.withConnectionPool(MongoConnectionPool)
        }

        if (needsRedis)
        {
            RedisPacketManager.load(redisHost, redisPort, redisPassword, redisUsername)
            redisConnectionPort = redisPort
        }

        UniqueServerService.loadAll()
        RankService.loadRanks()
        ProfileGameService.loadIndexes()
        TagService.loadTags()

        FilterService.loadIntoCache()

        if (needsRedis)
        {
            UUIDCache.loadAllFromRedis()
        }
    }
    
    fun getDataStoreMethod(): DataStoreType
    {
        return if (usingMongo)
        {
            DataStoreType.MONGO
        } else
        {
            DataStoreType.FLATFILE
        }
    }
}
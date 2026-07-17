package ltd.matrixstudios.alchemist

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.LongSerializationPolicy
import ltd.matrixstudios.alchemist.cache.types.UUIDCache
import ltd.matrixstudios.alchemist.models.server.UniqueServer
import ltd.matrixstudios.alchemist.mongo.MongoManager
import ltd.matrixstudios.alchemist.redis.RedisPacketManager
import ltd.matrixstudios.alchemist.service.filter.FilterService
import ltd.matrixstudios.alchemist.service.profiles.ProfileGameService
import ltd.matrixstudios.alchemist.service.ranks.RankService
import ltd.matrixstudios.alchemist.service.server.UniqueServerService
import ltd.matrixstudios.alchemist.service.tags.TagService
import kotlin.properties.Delegates

object Alchemist
{

    lateinit var globalServer: UniqueServer
    var redisConnectionPort by Delegates.notNull<Int>()

    var gson: Gson = GsonBuilder()
        .setLongSerializationPolicy(LongSerializationPolicy.STRING)
        .serializeNulls().create()

    fun start(
        mongoUri: String,
        mongoDatabase: String,
        needsRedis: Boolean,
        redisHost: String,
        redisPort: Int,
        redisUsername: String?,
        redisPassword: String?
    )
    {
        MongoManager.connect(mongoUri, mongoDatabase)

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
}

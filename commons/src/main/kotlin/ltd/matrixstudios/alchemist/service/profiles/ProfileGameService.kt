package ltd.matrixstudios.alchemist.service.profiles

import io.github.nosequel.data.DataStoreType
import ltd.matrixstudios.alchemist.Alchemist
import ltd.matrixstudios.alchemist.models.profile.GameProfile
import ltd.matrixstudios.alchemist.redis.RedisPacketManager
import java.util.*
import java.util.concurrent.CompletableFuture
import java.util.concurrent.TimeUnit

object ProfileGameService {


    var handler = Alchemist.dataHandler.createStoreType<UUID, GameProfile>(DataStoreType.MONGO)


    fun getValues(): Collection<GameProfile> {
        return handler.retrieveAllAsync().get()
    }

    fun save(profile: GameProfile) {
        handler.storeAsync(profile.uuid, profile)

        CompletableFuture.runAsync {
            RedisPacketManager.pool.resource.use {
                it.setex(
                    "Alchemist||ProfileCache||${profile.uuid.toString()}",
                    TimeUnit.MINUTES.toMillis(1),
                    RedisPacketManager.redisGson.toJson(profile)
                )
            }
        }
    }

    fun load(profile: GameProfile) {
        CompletableFuture.runAsync {
            RedisPacketManager.pool.resource.use {
                it.setex(
                    "Alchemist||ProfileCache||${profile.uuid.toString()}",
                    TimeUnit.MINUTES.toMillis(1),
                    RedisPacketManager.redisGson.toJson(profile)
                )
            }
        }
    }

    fun quickFind(id: UUID) : GameProfile? {
        var redisProfile: GameProfile?

        var foundInRedis = false

        val startingTime = System.currentTimeMillis()

        RedisPacketManager.pool.resource.use {
            redisProfile = RedisPacketManager.redisGson.fromJson(
                it.get("Alchemist||ProfileCache||$id"),
                GameProfile::class.java
            )
        }

        if (redisProfile == null) foundInRedis = false

        //return this profile because it was actually found in redis.
        if (redisProfile != null) {
            foundInRedis = true
            return redisProfile
        }

        var foundInMongo = false

        val mongoProfile: GameProfile? = byId(id)

        if (mongoProfile == null) foundInMongo = false

        if (mongoProfile != null) {
            foundInMongo = true
            return mongoProfile
        }

        if (foundInMongo && !foundInRedis)
        {
            if (mongoProfile != null)
            {
                load(mongoProfile)
            }
        }

        if (!foundInMongo && !foundInRedis) {
            return null
        }

        //shouldnt get here but if it does then wtf.
        return null

    }


    fun byId(id: UUID): GameProfile? {
        return getValues().firstOrNull { it.uuid == id }
    }


    fun byName(name: String): GameProfile? {
        return getValues().firstOrNull { it.username.equals(name, ignoreCase = true) }
    }

}
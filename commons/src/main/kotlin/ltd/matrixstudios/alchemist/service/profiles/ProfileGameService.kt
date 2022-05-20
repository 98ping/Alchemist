package ltd.matrixstudios.alchemist.service.profiles

import io.github.nosequel.data.DataStoreType
import ltd.matrixstudios.alchemist.Alchemist
import ltd.matrixstudios.alchemist.models.profile.GameProfile
import ltd.matrixstudios.alchemist.redis.RedisPacketManager
import java.util.*
import java.util.concurrent.CompletableFuture
import java.util.concurrent.TimeUnit
import javax.security.auth.callback.Callback

object ProfileGameService {


    var handler = Alchemist.dataHandler.createStoreType<UUID, GameProfile>(DataStoreType.MONGO)

    var cache = hashMapOf<UUID, GameProfile?>()

    fun byId(uuid: UUID) : GameProfile? {
        return ProfileSearchService.getAsync(uuid).get()!!
    }

    fun save(gameProfile: GameProfile) {
        cache[gameProfile.uuid] = gameProfile

<<<<<<< HEAD
        handler.storeAsync(gameProfile.uuid, gameProfile)
=======
        CompletableFuture.runAsync {
            RedisPacketManager.pool.resource.use {
                it.setex(
                    "Alchemist||ProfileCache||${profile.uuid.toString()}",
                    TimeUnit.MINUTES.toMillis(10),
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
                    TimeUnit.MINUTES.toMillis(10),
                    RedisPacketManager.redisGson.toJson(profile)
                )
            }
        }
    }

    fun quickFind(id: UUID) : GameProfile? {
        var redisProfile: GameProfile?

        var foundInRedis = false

        var startingTime = System.currentTimeMillis()

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

            println("[Alchemist] [Debug] Profile loading for " + redisProfile!!.username + " took " + System.currentTimeMillis().minus(startingTime) + "ms (redis loading)")
            return redisProfile
        }

        var foundInMongo = false

        val mongoProfile: GameProfile? = byId(id)

        if (mongoProfile == null) foundInMongo = false

        if (mongoProfile != null) {
            foundInMongo = true
            println("[Alchemist] [Debug] Profile loading for " + mongoProfile.username + " took " + System.currentTimeMillis().minus(startingTime) + "ms (mongo loading)")
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
>>>>>>> 051709bb1ff9433b1035fb471994d2c9a529f86f
    }

}
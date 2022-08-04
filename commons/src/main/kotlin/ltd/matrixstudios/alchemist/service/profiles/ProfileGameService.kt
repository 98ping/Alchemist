package ltd.matrixstudios.alchemist.service.profiles

import com.google.gson.JsonObject
import com.mongodb.client.model.Filters
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

    val collection = Alchemist.MongoConnectionPool.getCollection("gameprofile")

    var cache = hashMapOf<UUID, GameProfile?>()

    fun byId(uuid: UUID) : GameProfile? {
        return cache.getOrDefault(uuid, handler.retrieve(uuid))
    }

    fun byUsername(name: String) : GameProfile? {
        val cacheProfile = cache.values.firstOrNull { it!!.username.equals(name, ignoreCase = true) }

        if (cacheProfile != null)
        {
            return cacheProfile
        }

        val mongoProfile = collection.find(Filters.eq("lowercasedUsername", name.toLowerCase())).firstOrNull()

        if (mongoProfile != null)
        {
            return Alchemist.gson.fromJson(mongoProfile.toJson(), GameProfile::class.java)
        }

        return null
    }

    fun save(gameProfile: GameProfile) {
        cache[gameProfile.uuid] = gameProfile
        handler.storeAsync(gameProfile.uuid, gameProfile)
    }

    fun loadProfile(uuid: UUID, username: String) : GameProfile
    {
        val cached = cache[uuid] ?: handler.retrieve(uuid)

        return if (cached != null)
        {
            cached
        } else
        {
            GameProfile(
                uuid, username, username.toLowerCase(), JsonObject(), "", arrayListOf(), arrayListOf(), null, System.currentTimeMillis()
            )
        }
    }
}
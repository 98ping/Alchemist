package ltd.matrixstudios.alchemist.service.profiles

import com.google.gson.JsonObject
import com.mongodb.client.model.Filters
import io.github.nosequel.data.DataStoreType
import ltd.matrixstudios.alchemist.Alchemist
import ltd.matrixstudios.alchemist.models.grant.types.RankGrant
import ltd.matrixstudios.alchemist.models.profile.GameProfile
import ltd.matrixstudios.alchemist.models.ranks.Rank
import ltd.matrixstudios.alchemist.service.GeneralizedService
import ltd.matrixstudios.alchemist.service.expirable.RankGrantService
import java.util.*
import java.util.stream.Collectors

object ProfileGameService : GeneralizedService {


    var handler = Alchemist.dataHandler.createStoreType<UUID, GameProfile>(DataStoreType.MONGO)

    val collection = Alchemist.MongoConnectionPool.getCollection("gameprofile")

    var cache = hashMapOf<UUID, GameProfile?>()

    fun getHighestGrant(uuid: UUID) : RankGrant? {
        val grants = RankGrantService.getFromCache(uuid)

        grants.stream().sorted { o1, o2 ->  o2.getGrantable().weight - o1.getGrantable().weight }.collect(Collectors.toList())

        return grants.firstOrNull()
    }

    fun byId(uuid: UUID) : GameProfile? {
        return cache.computeIfAbsent(uuid) {
            return@computeIfAbsent handler.retrieveAsync(it).get()
        }
    }

    fun getHighestRank(uuid: UUID): Rank {
        val current = Rank("unknown", "Unknown", "Unknown", 1, arrayListOf(), arrayListOf(), "&f", "&f")
        val profile = byId(uuid) ?: return current

        return profile.getCurrentRank() ?: return current
    }

    fun byUsername(name: String) : GameProfile? {
        val cacheProfile = cache.values.firstOrNull { it!!.username.equals(name, ignoreCase = true) }

        if (cacheProfile != null) {
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
        val cached = cache[uuid] ?: handler.retrieveAsync(uuid).get()

        return cached
            ?: GameProfile(
                uuid, username, username.toLowerCase(), JsonObject(), "", arrayListOf(), arrayListOf(), null, null, mutableListOf(), System.currentTimeMillis()
            )
    }
}
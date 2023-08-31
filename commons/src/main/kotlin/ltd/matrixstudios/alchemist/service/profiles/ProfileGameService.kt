package ltd.matrixstudios.alchemist.service.profiles

import com.google.gson.JsonObject
import com.mongodb.BasicDBObject
import com.mongodb.client.model.Filters
import com.mongodb.client.model.Indexes
import io.github.nosequel.data.DataStoreType
import ltd.matrixstudios.alchemist.Alchemist
import ltd.matrixstudios.alchemist.cache.types.UUIDCache
import ltd.matrixstudios.alchemist.models.grant.types.RankGrant
import ltd.matrixstudios.alchemist.models.profile.GameProfile
import ltd.matrixstudios.alchemist.models.ranks.Rank
import ltd.matrixstudios.alchemist.punishments.PunishmentType
import ltd.matrixstudios.alchemist.service.GeneralizedService
import ltd.matrixstudios.alchemist.service.expirable.RankGrantService
import ltd.matrixstudios.alchemist.service.ranks.RankService
import org.bson.Document
import java.util.*
import java.util.concurrent.CompletableFuture
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.Executors
import java.util.concurrent.ForkJoinPool
import java.util.stream.Collectors


object ProfileGameService : GeneralizedService {


    var handler = Alchemist.dataHandler.createStoreType<UUID, GameProfile>(DataStoreType.MONGO)

    val collection = Alchemist.MongoConnectionPool.getCollection("gameprofile")

    var cache = ConcurrentHashMap<UUID, GameProfile?>()

    fun loadIndexes()
    {
        val fields = listOf("ip", "lowercasedUsername")

        for (f in fields)
        {
            collection.createIndex(Indexes.descending(f))
        }
    }

    fun getHighestGrant(uuid: UUID): RankGrant? {
        val grants = RankGrantService.getFromCache(uuid)

        grants.stream().sorted { o1, o2 -> o2.getGrantable().weight - o1.getGrantable().weight }
            .collect(Collectors.toList())

        return grants.firstOrNull()
    }

    fun byId(uuid: UUID): GameProfile? {
        return cache.computeIfAbsent(uuid) {
            return@computeIfAbsent handler.retrieveAsync(it).get()
        }
    }

    fun ipReportLookup() : CompletableFuture<MutableList<GameProfile>>
    {
        return CompletableFuture.supplyAsync {
            val ret = mutableListOf<GameProfile>()
            val allProfiles = handler.retrieveAll()

            for (profile in allProfiles)
            {
                for (alt in profile.getAltAccounts().join())
                {
                    if (alt.hasActivePunishment(PunishmentType.BLACKLIST) || alt.hasActivePunishment(PunishmentType.BAN) || alt.hasActivePunishment(PunishmentType.MUTE))
                    {
                        if (!profile.hasActivePunishment(PunishmentType.BAN) && !profile.hasActivePunishment(PunishmentType.BLACKLIST))
                        {
                            ret.add(profile)
                        }
                    }
                }
            }

            return@supplyAsync ret
        }
    }

    fun getHighestRank(uuid: UUID): Rank {
        val current = RankService.FALLBACK_RANK
        val profile = byId(uuid) ?: return current

        return profile.getCurrentRank()
    }

    fun byUsername(name: String): CompletableFuture<GameProfile?> {
        return CompletableFuture.supplyAsync {
            val cacheProfile = cache.values.firstOrNull { it!!.username.equals(name, ignoreCase = true) }

            if (cacheProfile != null) {
                return@supplyAsync cacheProfile
            }

            // TODO: https://www.mongodb.com/docs/manual/core/index-case-insensitive/ :)
            val mongoProfile = collection.find(Filters.eq("lowercasedUsername", name.toLowerCase())).firstOrNull()

            if (mongoProfile != null) {
                return@supplyAsync Alchemist.gson.fromJson(mongoProfile.toJson(), GameProfile::class.java)
            }

            null
        }
    }

    fun byUsernameWithList(name: String): CompletableFuture<List<GameProfile>> {
        return CompletableFuture.supplyAsync {
            val cacheProfile = cache.values.filter { it!!.username.equals(name, ignoreCase = true) }

            val entries = mutableListOf<GameProfile>()

            for (p in cacheProfile) {
                if (p == null) continue
                entries.add(p)
            }

            // TODO: https://www.mongodb.com/docs/manual/core/index-case-insensitive/ :)
            val mongoProfile = collection.find(Filters.eq("lowercasedUsername", name.toLowerCase()))

            for (d in mongoProfile) {
                val prof = (Alchemist.gson.fromJson(d.toJson(), GameProfile::class.java))

                if (entries.any { it.uuid == prof.uuid}) continue

                entries.add(prof)
            }

            return@supplyAsync entries
        }
    }

    fun getAllOutgoingFriendRequests(prof: GameProfile) : CompletableFuture<MutableList<GameProfile>>
    {
        return CompletableFuture.supplyAsync {
            val queryFilter = Document("friendInvites", Document("\$in", listOf(prof.uuid.toString())))
            val search = collection.find(queryFilter)
            val iterator = search.iterator()
            val final = mutableListOf<GameProfile>()

            while (iterator.hasNext()) {
                val json = Alchemist.gson.fromJson(iterator.next().toJson(), GameProfile::class.java)
                final.add(json)
            }

            return@supplyAsync final
        }
    }

    fun save(gameProfile: GameProfile) {
        cache[gameProfile.uuid] = gameProfile

        CompletableFuture.runAsync {
            handler.store(gameProfile.uuid, gameProfile)
        }
    }

    fun saveSync(gameProfile: GameProfile) {
        cache[gameProfile.uuid] = gameProfile
        handler.store(gameProfile.uuid, gameProfile)
    }

    fun loadProfile(uuid: UUID, username: String): GameProfile {
        val cached = cache[uuid] ?: handler.retrieve(uuid)

        return cached
            ?: GameProfile(
                uuid,
                username,
                username.toLowerCase(),
                JsonObject(),
                "",
                arrayListOf(),
                arrayListOf(),
                null,
                null,
                mutableListOf(),
                System.currentTimeMillis()
            )
    }
}

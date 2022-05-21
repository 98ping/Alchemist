package ltd.matrixstudios.alchemist.service.expirable

import com.mongodb.client.model.Filters
import io.github.nosequel.data.DataStoreType
import ltd.matrixstudios.alchemist.Alchemist
import ltd.matrixstudios.alchemist.models.grant.types.Punishment
import ltd.matrixstudios.alchemist.models.grant.types.RankGrant
import ltd.matrixstudios.alchemist.models.profile.GameProfile
import ltd.matrixstudios.alchemist.models.ranks.Rank
import ltd.matrixstudios.alchemist.punishments.PunishmentType
import ltd.matrixstudios.alchemist.punishments.actor.ActorType
import ltd.matrixstudios.alchemist.redis.RedisPacketManager
import java.util.*
import java.util.concurrent.CompletableFuture

object RankGrantService : ExpiringService<RankGrant>() {

    var handler = Alchemist.dataHandler.createStoreType<UUID, RankGrant>(DataStoreType.MONGO)

    var cache = hashMapOf<UUID, Collection<RankGrant>>()

    var internalCollection = Alchemist.MongoConnectionPool.getCollection("rankgrant")

    fun save(rankGrant: RankGrant) {
        handler.storeAsync(rankGrant.uuid, rankGrant)

        findGrantsFromRawDatabase(rankGrant.target).thenApplyAsync {
            cache[rankGrant.target] = it
        }
    }

    fun findByTarget(target: UUID) : Collection<RankGrant> {
        val grants = cache[target]

        if (grants != null) {
            return grants
        }

        val grantCallback = mutableListOf<RankGrant>()

        findGrantsFromRawDatabase(target).thenApply {
            grantCallback.addAll(it)
        }

        return grantCallback

    }

    fun collectUsersWithRank(rank: Rank) : CompletableFuture<Collection<UUID>> {
        return CompletableFuture.supplyAsync {
            val users = mutableListOf<UUID>()
            for (entry in cache.entries) {
                if (entry.value.any {
                        it.getGrantable()!!.id.equals(rank.id, ignoreCase = true) }
                )
                {
                    users.add(entry.key)
                }
            }

            return@supplyAsync users
        }


    }

    fun setupGrants(gameProfile: GameProfile)
    {
        findGrantsFromRawDatabase(gameProfile.uuid).thenApplyAsync {
            cache[gameProfile.uuid] = it
        }
    }

    fun smashThenReplaceGrants(rank: Rank) {
        collectUsersWithRank(rank).thenApplyAsync {
                uuids ->
            uuids.forEach  {
                    uuid ->
                findGrantsFromRawDatabase(uuid).apply {
                    this.thenAccept {
                        cache[uuid] = it
                    }
                }
            }
        }
    }

    fun findGrantsFromRawDatabase(uuid: UUID) : CompletableFuture<Collection<RankGrant>> {
        return CompletableFuture.supplyAsync {
            val iterable = internalCollection.find(Filters.eq("target", uuid.toString()))

            val mapped = iterable.into(mutableListOf()).map { Alchemist.gson.fromJson(it.toJson(), RankGrant::class.java) }

            return@supplyAsync mapped
        }
    }


    override fun clearOutModels() {
        for (grant in cache.values)
        {
            grant.forEach {
                if (!it.expirable.isActive() && it.removedBy == null) {
                    it.removedBy = UUID.fromString("00000000-0000-0000-0000-000000000000")
                    it.removedReason = "Expired"

                    save(it)
                }
            }
        }
    }

}
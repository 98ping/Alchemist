package ltd.matrixstudios.alchemist.service.expirable

import io.github.nosequel.data.DataStoreType
import ltd.matrixstudios.alchemist.Alchemist
import ltd.matrixstudios.alchemist.models.grant.types.RankGrant
import ltd.matrixstudios.alchemist.models.grant.types.scope.GrantScope
import ltd.matrixstudios.alchemist.models.profile.GameProfile
import org.bson.Document
import java.util.*
import java.util.concurrent.CompletableFuture

object RankGrantService : ExpiringService<RankGrant>() {

    var handler = Alchemist.dataHandler.createStoreType<UUID, RankGrant>(DataStoreType.MONGO)

    val collection = Alchemist.MongoConnectionPool.getCollection("rankgrant") //need this here because honey doesnt have a way to get raw collection

    var playerGrants = hashMapOf<UUID, MutableList<RankGrant>>()

    //default grant scope for use in models
    val global: GrantScope = GrantScope("Defaulted Grant Scope (Global)", mutableListOf(), true)

    fun getValues() : CompletableFuture<Collection<RankGrant>> {
        return handler.retrieveAllAsync()
    }

    fun findExecutedBy(executor: UUID) : MutableList<RankGrant>
    {
        val filter = Document("executor", executor.toString())
        val documents = collection.find(filter)
        val finalGrants = mutableListOf<RankGrant>()

        for (document in documents)
        {
            val obj = Alchemist.gson.fromJson(document.toJson(), RankGrant::class.java)

            finalGrants.add(obj)
        }

        return finalGrants
    }

    fun getFromCache(uuid: UUID): Collection<RankGrant> {
        return playerGrants.getOrDefault(uuid, findByTarget(uuid).get())
    }

    fun recalculatePlayer(gameProfile: GameProfile) {
        val time = System.currentTimeMillis()
        findByTarget(gameProfile.uuid).thenApply { playerGrants[gameProfile.uuid] = it }
    }

    fun recalculateUUID(gameProfile: UUID) {
        findByTarget(gameProfile).thenApply { playerGrants[gameProfile] = it }
    }

    fun remove(grant: RankGrant) {
        handler.delete(grant.uuid).also {
            playerGrants[grant.target]?.remove(grant)
        }
    }


    fun save(rankGrant: RankGrant) {
        handler.storeAsync(rankGrant.uuid, rankGrant)
    }

    fun findByTarget(target: UUID) : CompletableFuture<MutableList<RankGrant>> {
        return CompletableFuture.supplyAsync {
            val sorted = collection.find(Document("target", target.toString()))

            val toReturn = mutableListOf<RankGrant>()

            for (rawDoc in sorted)
            {
                val json = rawDoc.toJson()

                val gson = Alchemist.gson.fromJson(json, RankGrant::class.java)

                toReturn.add(gson)
            }

            return@supplyAsync toReturn
        }
    }

    override fun clearOutModels() { }
}
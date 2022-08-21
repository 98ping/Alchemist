package ltd.matrixstudios.alchemist.service.expirable

import io.github.nosequel.data.DataStoreType
import ltd.matrixstudios.alchemist.Alchemist
import ltd.matrixstudios.alchemist.models.grant.types.Punishment
import ltd.matrixstudios.alchemist.models.grant.types.RankGrant
import ltd.matrixstudios.alchemist.models.profile.GameProfile
import ltd.matrixstudios.alchemist.punishments.PunishmentType
import ltd.matrixstudios.alchemist.punishments.actor.ActorType
import org.bson.Document
import java.util.*
import java.util.concurrent.CompletableFuture

object PunishmentService : ExpiringService<Punishment>() {

    var handler = Alchemist.dataHandler.createStoreType<UUID, Punishment>(DataStoreType.MONGO)

    val collection = Alchemist.MongoConnectionPool.getCollection("punishment")

    var grants = mutableMapOf<UUID, Collection<Punishment>>()

    fun getValues() : CompletableFuture<Collection<Punishment>> {
        return handler.retrieveAllAsync()
    }

    fun save(punishment: Punishment) {
        handler.storeAsync(punishment.uuid, punishment)
    }

    fun getFromCache(uuid: UUID): Collection<Punishment> {
        return grants.getOrDefault(uuid, findByTarget(uuid).get())
    }

    fun recalculatePlayer(gameProfile: GameProfile) {
        findByTarget(gameProfile.uuid).thenApply { grants[gameProfile.uuid] = it }
    }

    fun findByTarget(target: UUID) : CompletableFuture<Collection<Punishment>> {
        return CompletableFuture.supplyAsync {
            val sorted = collection.find(Document("target", target.toString()))

            val toReturn = mutableListOf<Punishment>()

            for (rawDoc in sorted)
            {
                val json = rawDoc.toJson()

                val gson = Alchemist.gson.fromJson(json, Punishment::class.java)

                toReturn.add(gson)
            }

            return@supplyAsync toReturn
        }
    }

    fun sortByActorType(actorType: ActorType) : List<Punishment> {
        return getValues().get().filter { it.actor.actorType == actorType }
    }

    fun sortByPunishmentType(punishmentType: PunishmentType) : List<Punishment> {
        return getValues().get().filter { it.punishmentType == punishmentType.name }
    }


    override fun clearOutModels() { }

}
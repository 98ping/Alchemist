package ltd.matrixstudios.alchemist.service.expirable

import io.github.nosequel.data.DataStoreType
import ltd.matrixstudios.alchemist.Alchemist
import ltd.matrixstudios.alchemist.models.grant.types.Punishment
import ltd.matrixstudios.alchemist.models.profile.GameProfile
import ltd.matrixstudios.alchemist.punishments.PunishmentType
import ltd.matrixstudios.alchemist.punishments.actor.ActorType
import org.bson.Document
import java.util.*
import java.util.concurrent.CompletableFuture
import java.util.concurrent.ConcurrentHashMap

object PunishmentService : ExpiringService<Punishment>() {

    var handler = Alchemist.dataHandler.createStoreType<UUID, Punishment>(DataStoreType.MONGO)

    val collection = Alchemist.MongoConnectionPool.getCollection("punishment")

    var grants = ConcurrentHashMap<UUID, MutableList<Punishment>>()

    fun getValues() : CompletableFuture<Collection<Punishment>> {
        return handler.retrieveAllAsync()
    }

    fun save(punishment: Punishment) {
        handler.storeAsync(punishment.uuid, punishment)
        recalculateUUID(punishment.target)
    }

    fun saveSync(punishment: Punishment) {
        handler.store(punishment.uuid, punishment)
        recalculateUUID(punishment.target)
    }

    fun getFromCache(uuid: UUID): Collection<Punishment> {
        return if (grants.containsKey(uuid)) {
            grants[uuid]!!
        } else findByTarget(uuid).get()
    }

    fun recalculatePlayer(gameProfile: GameProfile) {
        findByTarget(gameProfile.uuid).thenApply { grants[gameProfile.uuid] = it }
    }

    fun recalculatePlayerSync(gameProfile: GameProfile) {
        val punishments = findByTarget(gameProfile.uuid).get()

        grants[gameProfile.uuid] = punishments
    }

    fun recalculateUUID(playerId: UUID) {
        findByTarget(playerId).thenApply { grants[playerId] = it }
    }

    fun findExecutorPunishments(executor: UUID) : List<Punishment>
    {
        val filter = Document("executor", executor.toString())
        val bson = collection.find(filter)
        val finalPunishments = mutableListOf<Punishment>()

        for (document in bson)
        {
            val model = Alchemist.gson.fromJson(document.toJson(), Punishment::class.java)

            finalPunishments.add(model)
        }

        return finalPunishments
    }

    fun searchFromId(punishmentId: String) : Punishment?
    {
        val filter = Document("easyFindId", punishmentId)

        val bson = collection.find(filter).first() ?: return null

        return Alchemist.gson.fromJson(bson.toJson(), Punishment::class.java)
    }

    fun findByTarget(target: UUID) : CompletableFuture<MutableList<Punishment>> {
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
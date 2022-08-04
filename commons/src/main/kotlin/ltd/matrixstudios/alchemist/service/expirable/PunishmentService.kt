package ltd.matrixstudios.alchemist.service.expirable

import io.github.nosequel.data.DataStoreType
import ltd.matrixstudios.alchemist.Alchemist
import ltd.matrixstudios.alchemist.models.grant.types.Punishment
import ltd.matrixstudios.alchemist.punishments.PunishmentType
import ltd.matrixstudios.alchemist.punishments.actor.ActorType
import java.util.*

object PunishmentService : ExpiringService<Punishment>() {

    var handler = Alchemist.dataHandler.createStoreType<UUID, Punishment>(DataStoreType.MONGO)


    fun getValues() : Collection<Punishment> {
        return handler.retrieveAllAsync().get()
    }

    fun save(punishment: Punishment) {
        handler.storeAsync(punishment.uuid, punishment)
    }

    fun sortByActorType(actorType: ActorType) : List<Punishment> {
        return getValues().filter { it.actor.actorType == actorType }
    }

    fun sortByPunishmentType(punishmentType: PunishmentType) : List<Punishment> {
        return getValues().filter { it.punishmentType == punishmentType.name }
    }


    override fun clearOutModels() { }

}
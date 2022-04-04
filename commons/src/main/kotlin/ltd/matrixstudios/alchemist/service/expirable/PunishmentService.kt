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
        return handler.retrieveAll()
    }

    fun save(punishment: Punishment) {
        handler.store(punishment.uuid, punishment)
    }

    fun sortByActorType(actorType: ActorType) : List<Punishment> {
        return getValues().filter { it.actor.actorType == actorType }
    }

    fun sortByPunishmentType(punishmentType: PunishmentType) : List<Punishment> {
        return getValues().filter { it.punishmentType == punishmentType.name }
    }


    override fun clearOutModels() {
        getValues().forEach {
            if (!it.expirable.isActive() && it.removedBy == null) {
                it.removedBy = UUID.fromString("00000000-0000-0000-0000-000000000000")
                it.removedReason = "Expired"

                save(it)
            }
        }
    }

}
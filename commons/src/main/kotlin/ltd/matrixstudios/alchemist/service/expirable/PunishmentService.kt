package ltd.matrixstudios.alchemist.service.expirable

import ltd.matrixstudios.alchemist.Alchemist
import ltd.matrixstudios.alchemist.models.grant.types.Punishment
import ltd.matrixstudios.alchemist.models.grant.types.RankGrant
import ltd.matrixstudios.alchemist.punishments.PunishmentType
import ltd.matrixstudios.alchemist.punishments.actor.ActorType
import java.util.*

object PunishmentService : ExpiringService<Punishment>() {

    override fun getValues(): List<Punishment> {
        return Alchemist.dataflow.createQuery(Punishment::class.java).toList()
    }

    override fun save(element: Punishment) {
        Alchemist.dataflow.save(element.uuid.toString(), element, Punishment::class.java)
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
package ltd.matrixstudios.alchemist.models.grant.types

import ltd.matrixstudios.alchemist.models.expirables.Expirable
import ltd.matrixstudios.alchemist.models.grant.Grantable
import ltd.matrixstudios.alchemist.punishments.PunishmentType
import ltd.matrixstudios.alchemist.punishments.actor.DefaultActor
import java.util.*


class Punishment(
    punishmentType: String,
    addedTo: UUID,
    addedBy: UUID,
    addedReason: String,
    duration: Long,
    actor: DefaultActor
) :
    Grantable<PunishmentType>(
        UUID.randomUUID(),
        addedTo,
        addedBy,
        addedReason,
        Expirable(false, System.currentTimeMillis(), duration, 0L),
        null,
        null
    ) {

    var punishmentType: String = punishmentType
    var actor: DefaultActor = actor

    override fun getGrantable(): PunishmentType {
        return PunishmentType.valueOf(punishmentType)
    }

}
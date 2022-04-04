package ltd.matrixstudios.alchemist.models.grant.types

import ltd.matrixstudios.alchemist.models.expirables.Expirable
import ltd.matrixstudios.alchemist.models.grant.Grantable
import ltd.matrixstudios.alchemist.models.ranks.Rank
import ltd.matrixstudios.alchemist.punishments.PunishmentType
import ltd.matrixstudios.alchemist.punishments.actor.Actor
import java.util.*


class Punishment(
    punishmentType: String,
    addedTo: UUID,
    addedBy: UUID,
    addedReason: String,
    duration: Long,
    actor: Actor
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
    var actor: Actor = actor

    override fun getGrantable(): PunishmentType {
        return PunishmentType.valueOf(punishmentType)
    }

}
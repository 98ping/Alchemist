package ltd.matrixstudios.alchemist.models.grant.types

import ltd.matrixstudios.alchemist.models.expirables.Expirable
import ltd.matrixstudios.alchemist.models.grant.Grantable
import ltd.matrixstudios.alchemist.models.tags.Tag
import ltd.matrixstudios.alchemist.punishments.actor.DefaultActor
import ltd.matrixstudios.alchemist.service.tags.TagService
import java.util.*


class TagGrant(
    id: String,
    addedTo: UUID,
    addedBy: UUID,
    addedReason: String,
    duration: Long,
    actor: DefaultActor
) :
    Grantable<Tag>(
        UUID.randomUUID(),
        addedTo,
        addedBy,
        addedReason,
        Expirable(false, System.currentTimeMillis(), duration, 0L),
        null,
        null
    ) {

    var internalActor: DefaultActor = actor
    var id: String = id

    override fun getGrantable(): Tag? {
        return TagService.byId(id)
    }

}
package ltd.matrixstudios.alchemist.models.grant.types


import ltd.matrixstudios.alchemist.models.expirables.Expirable
import ltd.matrixstudios.alchemist.models.grant.Grantable
import ltd.matrixstudios.alchemist.models.ranks.Rank
import ltd.matrixstudios.alchemist.punishments.actor.DefaultActor
import ltd.matrixstudios.alchemist.service.ranks.RankService
import java.util.*


class RankGrant(
    rankId: String,
    addedTo: UUID,
    addedBy: UUID,
    addedReason: String,
    duration: Long,
    actor: DefaultActor
) :
    Grantable<Rank>(
        UUID.randomUUID(),
        addedTo,
        addedBy,
        addedReason,
        Expirable(false, System.currentTimeMillis(), duration, 0L),
        null,
        null
    ) {

    var internalActor: DefaultActor = actor
    var rank: String = rankId

    override fun getGrantable(): Rank? {
       return RankService.byId(rank)
    }

}
package ltd.matrixstudios.alchemist.models.grant.types


import ltd.matrixstudios.alchemist.models.expirables.Expirable
import ltd.matrixstudios.alchemist.models.grant.Grantable
import ltd.matrixstudios.alchemist.models.ranks.Rank
import ltd.matrixstudios.alchemist.service.ranks.RankService
import ltd.matrixstudios.mongo.annotation.Collection
import java.util.*


@Collection("rankgrants")
class RankGrant(
    rankId: String,
    addedTo: UUID,
    addedBy: UUID,
    addedReason: String,
    duration: Long
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


    var rank: String = rankId

    override fun getGrantable(): Rank? {
       return RankService.byId(rank)
    }

}
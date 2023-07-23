package ltd.matrixstudios.alchemist.models.grant.types


import ltd.matrixstudios.alchemist.models.expirables.Expirable
import ltd.matrixstudios.alchemist.models.grant.Grantable
import ltd.matrixstudios.alchemist.models.grant.types.scope.GrantScope
import ltd.matrixstudios.alchemist.models.ranks.Rank
import ltd.matrixstudios.alchemist.punishments.actor.DefaultActor
import ltd.matrixstudios.alchemist.service.expirable.RankGrantService
import ltd.matrixstudios.alchemist.service.profiles.ProfileGameService
import ltd.matrixstudios.alchemist.service.ranks.RankService
import java.util.*


data class RankGrant(
    val rankId: String,
    val addedTo: UUID,
    val addedBy: UUID,
    val addedReason: String,
    val duration: Long,
    val actor: DefaultActor,
    val constructorScope: GrantScope? = null
) :
    Grantable<Rank>(
        UUID.randomUUID(),
        addedTo,
        addedBy,
        addedReason,
        Expirable(false, System.currentTimeMillis(), duration, 0L),
        null, null
    ) {

    var internalActor: DefaultActor = actor
    var rank: String = rankId
    var scope: GrantScope? = constructorScope


    fun verifyGrantScope() : GrantScope {
        if (scope == null) return RankGrantService.global

        return scope!!
    }

    fun getIssuedByName() : String
    {
        val profile = ProfileGameService.byId(this.executor) ?: return "Console"

        return profile.username
    }

    override fun getGrantable(): Rank {
       val optional = RankService.byId(rank)

        if (optional != null) {
            return optional
        }

        return RankService.FALLBACK_RANK
    }

}
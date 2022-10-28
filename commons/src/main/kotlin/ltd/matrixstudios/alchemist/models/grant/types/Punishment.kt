package ltd.matrixstudios.alchemist.models.grant.types

import ltd.matrixstudios.alchemist.models.expirables.Expirable
import ltd.matrixstudios.alchemist.models.grant.Grantable
import ltd.matrixstudios.alchemist.models.grant.types.proof.ProofEntry
import ltd.matrixstudios.alchemist.punishments.PunishmentType
import ltd.matrixstudios.alchemist.punishments.actor.DefaultActor
import ltd.matrixstudios.alchemist.service.profiles.ProfileGameService
import java.util.*


class Punishment(
    punishmentType: String,
    easyFindId: String,
    proof: MutableList<ProofEntry>,
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
    var easyFindId: String = easyFindId
    var proof: List<ProofEntry> = proof

    fun getIssuedByName() : String
    {
        val profile = ProfileGameService.byId(this.executor)
        if (profile == null)
        {
            return "Console"
        }

        return profile.username
    }

    override fun getGrantable(): PunishmentType {
        return PunishmentType.valueOf(punishmentType)
    }

}
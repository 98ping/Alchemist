package ltd.matrixstudios.alchemist.models.profile

import com.google.gson.JsonObject
import ltd.matrixstudios.alchemist.models.grant.types.Punishment
import ltd.matrixstudios.alchemist.models.ranks.Rank
import ltd.matrixstudios.alchemist.punishments.PunishmentType
import ltd.matrixstudios.alchemist.service.expirable.PunishmentService
import ltd.matrixstudios.alchemist.service.expirable.RankGrantService
import ltd.matrixstudios.alchemist.service.ranks.RankService
import java.util.*

data class GameProfile(
    var uuid: UUID,
    var username: String,
    var metadata: JsonObject,
    var usedIps: ArrayList<String>,
) {

    fun getPunishments() : Collection<Punishment> {
        return PunishmentService.getValues().filter { it.target == uuid }
    }


    fun hasActivePunishment(type: PunishmentType) : Boolean {
        return getPunishments().find { it.getGrantable() == type } != null
    }

    fun getCurrentRank(): Rank? {
        var currentGrant: Rank? = RankService.findFirstAvailableDefaultRank()

        RankGrantService.findByTarget(uuid).forEach { grant ->
            if (grant.expirable.isActive() && grant.getGrantable()!!.weight > currentGrant!!.weight) {
                currentGrant = grant.getGrantable()
            }
        }

        return currentGrant
    }
}
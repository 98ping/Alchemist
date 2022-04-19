package ltd.matrixstudios.alchemist.models.profile

import com.google.gson.JsonObject
import com.mongodb.BasicDBList
import ltd.matrixstudios.alchemist.models.grant.types.Punishment
import ltd.matrixstudios.alchemist.models.ranks.Rank
import ltd.matrixstudios.alchemist.punishments.PunishmentType
import ltd.matrixstudios.alchemist.service.expirable.PunishmentService
import ltd.matrixstudios.alchemist.service.expirable.RankGrantService
import ltd.matrixstudios.alchemist.service.profiles.ProfileGameService
import ltd.matrixstudios.alchemist.service.ranks.RankService
import java.util.*
import java.util.concurrent.CompletableFuture
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

data class GameProfile(
    var uuid: UUID,
    var username: String,
    var metadata: JsonObject,
    var usedIps: ArrayList<String>,
    var friends: ArrayList<UUID>,
    var friendInvites: ArrayList<UUID>
) {

    fun getPunishments(): Collection<Punishment> {
        return PunishmentService.getValues().filter { it.target == uuid }
    }

    fun supplyFriendsAsProfiles() : CompletableFuture<List<GameProfile?>> {
        return CompletableFuture.supplyAsync {
            friends.map { ProfileGameService.byId(it) }.filter { Objects.nonNull(it) }
        }
    }

    fun getActivePunishments(type: PunishmentType): Collection<Punishment> {
        return getPunishments().filter { it.getGrantable() == type && it.expirable.isActive() }
    }

    fun getPermissions(): HashMap<String?, Boolean?> {
        val returnedPerms = hashMapOf<String?, Boolean?>()
        val allPerms = arrayListOf<String>()

        allPerms.addAll(getCurrentRank()!!.permissions)

        val parents = getCurrentRank()!!.parents.map {
            RankService.byId(it)
        }.filter {
            Objects.nonNull(it)
        }

        parents.forEach { rank ->
            rank!!.permissions.forEach {
                if (!allPerms.contains(it)) {
                    allPerms.add(it)
                }
            }
        }


        allPerms.forEach {
            returnedPerms[it] = true
        }

        return returnedPerms
    }


    fun hasActivePunishment(type: PunishmentType): Boolean {
        return getPunishments().find { it.expirable.isActive() && it.getGrantable() == type } != null
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
package ltd.matrixstudios.alchemist.models.profile

import com.google.gson.JsonObject
import ltd.matrixstudios.alchemist.Alchemist
import ltd.matrixstudios.alchemist.models.grant.types.Punishment
import ltd.matrixstudios.alchemist.models.ranks.Rank
import ltd.matrixstudios.alchemist.models.tags.Tag
import ltd.matrixstudios.alchemist.punishments.PunishmentType
import ltd.matrixstudios.alchemist.service.expirable.PunishmentService
import ltd.matrixstudios.alchemist.service.expirable.RankGrantService
import ltd.matrixstudios.alchemist.service.expirable.TagGrantService
import ltd.matrixstudios.alchemist.service.profiles.ProfileGameService
import ltd.matrixstudios.alchemist.service.ranks.RankService
import ltd.matrixstudios.alchemist.service.tags.TagService
import org.bson.Document
import java.util.*
import java.util.concurrent.CompletableFuture
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

data class GameProfile(
    var uuid: UUID,
    var username: String,
    var lowercasedUsername: String,
    var metadata: JsonObject,
    var ip: String,
    var friends: ArrayList<UUID>,
    var friendInvites: ArrayList<UUID>,
    var activePrefix: String?,
    var lastSeenAt: Long
) {

    fun getPunishments(): Collection<Punishment> {
        return PunishmentService.getValues().filter { it.target == uuid }
    }

    fun accuracyOfRelation(profile: GameProfile) : Int {
        var assurance = 85

        if (ip != profile.ip)
        {
            assurance -= 50
        }

        if (ip == profile.ip)
        {
            if (getActivePunishments().isEmpty()) {
                assurance -= 70
            }
        }

        return assurance
    }

    fun getActivePunishments() : Collection<Punishment> {
        return getPunishments().filter { it.expirable.isActive() }
    }

    fun getAltAccounts(): MutableList<GameProfile> {
        val finalAccounts = arrayListOf<GameProfile>()
        val targetDocuments = ProfileGameService.collection.find(Document("ip", ip))


        for (document in targetDocuments) {
            val documentJson = document.toJson()

            val profile = Alchemist.gson.fromJson(documentJson, GameProfile::class.java)

            finalAccounts.add(profile)

        }

        return finalAccounts
    }

    fun hasActivePrefix(): Boolean {
        return activePrefix != null
    }

    fun getActivePrefix(): Tag? {
        val tag = TagService.byId(activePrefix!!) ?: return null

        return tag
    }

    fun canUse(tag: Tag): Boolean {
        return TagGrantService.getValues().get()
            .filter {
                it.target == uuid && it.expirable.isActive()
            }.firstOrNull {
                it.getGrantable()!!.id == tag.id
            } != null
    }

    fun isOnline(): Boolean {
        if (metadata.get("server") == null) return false

        return metadata.get("server").asString != "None"
    }

    fun supplyFriendsAsProfiles(): CompletableFuture<List<GameProfile?>> {
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
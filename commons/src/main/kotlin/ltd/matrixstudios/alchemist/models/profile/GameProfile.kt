package ltd.matrixstudios.alchemist.models.profile

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import ltd.matrixstudios.alchemist.Alchemist
import ltd.matrixstudios.alchemist.models.chatcolor.ChatColor
import ltd.matrixstudios.alchemist.models.grant.types.Punishment
import ltd.matrixstudios.alchemist.models.grant.types.RankGrant
import ltd.matrixstudios.alchemist.models.grant.types.scope.GrantScope
import ltd.matrixstudios.alchemist.models.profile.auth.AuthStatus
import ltd.matrixstudios.alchemist.models.profile.disguise.RankDisguiseAttribute
import ltd.matrixstudios.alchemist.models.profile.disguise.SkinDisguiseAttribute
import ltd.matrixstudios.alchemist.models.profile.notes.ProfileNote
import ltd.matrixstudios.alchemist.models.ranks.Rank
import ltd.matrixstudios.alchemist.models.server.UniqueServer
import ltd.matrixstudios.alchemist.models.sessions.Session
import ltd.matrixstudios.alchemist.models.tags.Tag
import ltd.matrixstudios.alchemist.punishments.PunishmentType
import ltd.matrixstudios.alchemist.punishments.actor.ActorType
import ltd.matrixstudios.alchemist.punishments.actor.DefaultActor
import ltd.matrixstudios.alchemist.punishments.actor.executor.Executor
import ltd.matrixstudios.alchemist.service.expirable.PunishmentService
import ltd.matrixstudios.alchemist.service.expirable.RankGrantService
import ltd.matrixstudios.alchemist.service.expirable.TagGrantService
import ltd.matrixstudios.alchemist.service.profiles.ProfileGameService
import ltd.matrixstudios.alchemist.service.ranks.RankService
import ltd.matrixstudios.alchemist.service.session.SessionService
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
    var metadata: JsonObject = JsonObject(),
    var ip: String,
    var friends: ArrayList<UUID> = ArrayList(),
    var friendInvites: ArrayList<UUID> = ArrayList(),
    var activeColor: ChatColor? = null,
    var activePrefix: String? = null,
    var authStatus: AuthStatus? = null,
    var permissions: MutableList<String> = ArrayList(),
    var lastSeenAt: Long,
    var websiteNeedsVerification: Boolean = false,
    var websiteVerificationToken: String? = null,
    var skinDisguiseAttribute: SkinDisguiseAttribute? = null,
    var rankDisguiseAttribute: RankDisguiseAttribute? = null,
    var coins: Int = 0,
    val notes: MutableList<ProfileNote> = ArrayList(),
    var siblings: MutableList<UUID> = ArrayList()
) {

    @Transient
    var currentSession: Session? = null

    fun getAllSiblings() : MutableList<UUID> {
        if (siblings == null) {
            this.siblings = mutableListOf()

            return siblings
        }

        return siblings
    }

    @JvmName("getAuthStatus1")
    fun getAuthStatus() : AuthStatus
    {
        return if (authStatus == null)
        {
            AuthStatus(
                0L,
                hasSetup2fa = false,
                authBypassed = false,
                "",
                mutableListOf(ip)
            )
        } else authStatus!!
    }

    fun getPunishments(): Collection<Punishment> {
        return PunishmentService.getFromCache(uuid)
    }

    fun getRankDisplay() : String {
        val rank = getCurrentRank()

        return rank.color + username
    }

    fun getExecutedCountByType(type: PunishmentType) : Int = PunishmentService.findExecutorPunishments(uuid).count { it.punishmentType == type.name }

    fun getActivePunishments() : Collection<Punishment> {
        return getPunishments().filter { it.expirable.isActive() }
    }

    fun getActivePunishmentsFilteredByImportance() : Collection<Punishment> {
        val bindings = hashMapOf(
            PunishmentType.BLACKLIST to 5,
            PunishmentType.BAN to 4,
            PunishmentType.WARN to 1,
            PunishmentType.MUTE to 3,
            PunishmentType.GHOST_MUTE to 2
        )
        return getActivePunishments().sortedByDescending { bindings[it.getGrantable()]!! }
    }

    @Transient
    private var backingCachedAlternateAccounts: MutableList<GameProfile>? = null

    fun getAltAccounts() = if (backingCachedAlternateAccounts == null)
        CompletableFuture
            .supplyAsync(::_getAltAccounts)
            .thenApply {
                backingCachedAlternateAccounts = it
                it
            }
    else
        CompletableFuture.completedFuture(backingCachedAlternateAccounts!!)

    private fun _getAltAccounts(): MutableList<GameProfile> {
        val finalAccounts = arrayListOf<GameProfile>()
        val targetDocuments = ProfileGameService.collection.find(Document("ip", ip))


        for (document in targetDocuments) {
            val documentJson = document.toJson()

            val profile = Alchemist.gson.fromJson(documentJson, GameProfile::class.java)

            finalAccounts.add(profile)

        }

        return finalAccounts
    }

    fun getAltMostSeriousPunishment() : String
    {
        if (alternateAccountHasBlacklist()) return "&4Blacklist"
        if (alternateAccountHasBan()) return "&cBan"
        return "&fNone"
    }

    fun getPunishmentedPrefix() : String
    {
        if (alternateAccountHasBlacklist())
        {
            return "&4"
        }

        if (alternateAccountHasBan())
        {
            return "&c"
        }

        if (alternateAccountHasMute())
        {
            return "&f&o"
        }

        return "&7"
    }

    fun alternateAccountHasBlacklist() : Boolean {
        val alts = getAltAccounts().join()

        for (acc in alts) {
            if (acc.hasActivePunishment(PunishmentType.BLACKLIST)) return true
        }

        return false
    }

    fun alternateAccountHasBan() : Boolean {
        val alts = getAltAccounts().join()

        for (acc in alts) {
            if (acc.hasActivePunishment(PunishmentType.BAN)) return true
        }

        return false
    }

    fun alternateAccountHasMute() : Boolean {
        val alts = getAltAccounts().join()

        for (acc in alts) {
            if (acc.hasActivePunishment(PunishmentType.MUTE)) return true
        }

        return false
    }

    fun getFirstBlacklistFromAlts() : Punishment?
    {
        val alts = getAltAccounts().join()

        for (acc in alts)
        {
            if (acc.hasActivePunishment(PunishmentType.BLACKLIST))
            {
                return acc.getActivePunishments(PunishmentType.BLACKLIST).firstOrNull()
            }
        }
        return null
    }

    fun createNewSession(server: UniqueServer) : Session
    {
        val session = Session(UUID.randomUUID().toString().substring(0, 4), uuid, mutableMapOf(), System.currentTimeMillis(), 0L)

        session.serversJoined[System.currentTimeMillis()] = server

        SessionService.save(session)
        SessionService.loadIntoCache(this)

        return session
    }

    fun hasActivePrefix(): Boolean {
        return activePrefix != null
    }

    fun hasMetadata(key: String) : Boolean
    {
        return metadata.get(key) != null
    }

    fun getActivePrefix(): Tag? {
        val tag = TagService.byId(activePrefix!!) ?: return null

        return tag
    }

    fun canUse(tag: Tag): Boolean {
        return TagGrantService.getValues().get()
            .filter {
                it.target == uuid && it.expirable.isActive() && it.getGrantable() != null
            }.firstOrNull {
                it.getGrantable()!!.id == tag.id
            } != null
    }

    fun isOnline(): Boolean {
        if (metadata.get("server") == null) return false

        return metadata.get("server").asString != "None"
    }

    fun supplyFriendsAsProfiles(): CompletableFuture<List<GameProfile>> {
        return CompletableFuture.supplyAsync {
            friends.map { ProfileGameService.byId(it) }.filter { Objects.nonNull(it) }.map { it!! }
        }
    }

    fun getActivePunishments(type: PunishmentType): Collection<Punishment> {
        return getPunishments().filter { it.getGrantable() == type && it.expirable.isActive() }
    }

    fun getPunishments(type: PunishmentType): Collection<Punishment> {
        return getPunishments().filter { it.getGrantable() == type }
    }

    fun getPermissionsAsList(): MutableList<String> {
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


        return allPerms
    }

    // TODO: handle false permissions in the map
    fun getPermissions(): Map<String, Boolean> {
        val allPerms = getCurrentRank().permissions
            .toMutableList()

        getCurrentRank()
            .parents
            .mapNotNull(RankService::byId)
            .forEach { rank ->
                allPerms += rank.getAllPermissions()
            }

        return listOf(permissions, allPerms)
            .flatten()
            .associateWith {
                !it.startsWith("*")
            }
    }

    fun getPermissionsExclusivelyGlobal(): Map<String, Boolean> {
        val allPerms = getHighestGlobalRank().permissions
            .toMutableList()

        getHighestGlobalRank()
            .parents
            .mapNotNull(RankService::byId)
            .forEach { rank ->
                allPerms += rank.getAllPermissions()
            }

        return listOf(permissions, allPerms)
            .flatten()
            .associateWith {
                !it.startsWith("*")
            }
    }


    fun hasActivePunishment(type: PunishmentType): Boolean {
        return getPunishments().find { it.expirable.isActive() && it.getGrantable() == type } != null
    }

    fun getHighestGlobalRank(): Rank {
        val currentGrant: Rank? = RankService.findFirstAvailableDefaultRank()

        /*
            Same method as below except using global parameters
         */
        val filteredRank = RankGrantService.getFromCache(uuid).filter {
            it.expirable.isActive() && (it.verifyGrantScope().global)
        }.sortedByDescending { it.getGrantable().weight }.firstOrNull()

        if (filteredRank == null || filteredRank.getGrantable().weight < (currentGrant?.weight ?: 0))
        {
            return currentGrant ?: RankService.FALLBACK_RANK
        }

        return filteredRank.getGrantable()
    }

    fun getCurrentGrant(): RankGrant {
        val currentGrant: Rank? = RankService.findFirstAvailableDefaultRank()
        val globalServer = Alchemist.globalServer

        val filteredRank = RankGrantService.getFromCache(uuid).filter {
            it.expirable.isActive()
                    && (it.verifyGrantScope().global || it.verifyGrantScope().appliesOn(globalServer))
                    && (it.getGrantable().getRankScope().global || it.getGrantable().getRankScope().appliesOn(globalServer)
                    )
        }.sortedByDescending { it.getGrantable().weight }.firstOrNull()

        if (filteredRank == null || filteredRank.getGrantable().weight < (currentGrant?.weight ?: 0))
        {
            if (currentGrant == null)
            {
                return RankService.FALLBACK_GRANT
            }

            return RankGrant(
                currentGrant.id,
                UUID.randomUUID(),
                UUID.randomUUID(),
                "Fallback Grant",
                Long.MAX_VALUE,
                DefaultActor(Executor.CONSOLE, ActorType.GAME),
                GrantScope("Fallback Grant", mutableListOf(), true)
            )
        }

        return filteredRank
    }

    fun getCurrentRank(): Rank {
        val currentGrant: Rank? = RankService.findFirstAvailableDefaultRank()
        val globalServer = Alchemist.globalServer


        /*
            Get a total collection of all active grants and then
            we make a full collection of grants that are global (apply
            on every server) and scope specific ranks. From here we can just
            check which one comes out on top

            Checks include:
                - Duration is still valid
                - Grant scope is global or applies on instance
                - Rank scope is global or applies on instance

            In the event that everything here fails, return fallback rank (Thanks Lunar.gg)
         */
        val filteredRank = RankGrantService.getFromCache(uuid).filter {
            it.expirable.isActive()
                    && (it.verifyGrantScope().global || it.verifyGrantScope().appliesOn(globalServer))
                    && (it.getGrantable().getRankScope().global || it.getGrantable().getRankScope().appliesOn(globalServer)
            )
        }.sortedByDescending { it.getGrantable().weight }.firstOrNull()

        if (filteredRank == null || filteredRank.getGrantable().weight < (currentGrant?.weight ?: 0))
        {
            return currentGrant ?: RankService.FALLBACK_RANK
        }

        return filteredRank.getGrantable()
    }
}

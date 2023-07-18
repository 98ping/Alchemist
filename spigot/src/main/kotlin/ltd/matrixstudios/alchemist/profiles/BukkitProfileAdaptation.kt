package ltd.matrixstudios.alchemist.profiles

import ltd.matrixstudios.alchemist.Alchemist
import ltd.matrixstudios.alchemist.AlchemistSpigotPlugin
import ltd.matrixstudios.alchemist.api.AlchemistAPI
import ltd.matrixstudios.alchemist.metric.Metric
import ltd.matrixstudios.alchemist.metric.MetricService
import ltd.matrixstudios.alchemist.models.grant.types.Punishment
import ltd.matrixstudios.alchemist.packets.StaffGeneralMessagePacket
import ltd.matrixstudios.alchemist.permissions.AccessiblePermissionHandler
import ltd.matrixstudios.alchemist.profiles.postlog.BukkitPostLoginConnection
import ltd.matrixstudios.alchemist.profiles.prelog.BukkitPreLoginConnection
import ltd.matrixstudios.alchemist.punishments.PunishmentType
import ltd.matrixstudios.alchemist.redis.AsynchronousRedisSender
import ltd.matrixstudios.alchemist.service.expirable.PunishmentService
import ltd.matrixstudios.alchemist.service.expirable.RankGrantService
import ltd.matrixstudios.alchemist.service.profiles.ProfileGameService
import ltd.matrixstudios.alchemist.util.Chat
import ltd.matrixstudios.alchemist.util.SHA
import ltd.matrixstudios.alchemist.util.TimeUtil
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.player.AsyncPlayerPreLoginEvent
import java.util.UUID
import java.util.concurrent.CompletableFuture
import java.util.logging.Level

/**
 * Class created on 5/27/2023

 * @author 98ping
 * @project Alchemist
 * @website https://solo.to/redis
 */
object BukkitProfileAdaptation
{

    fun loadAllPreLoginEvents()
    {
        BukkitPreLoginConnection.registerNewCallback { event ->
            loadAndEquipProfile(event)
        }

        BukkitPreLoginConnection.registerNewCallback { event ->
            calculateAndPostGrantables(event.uniqueId)
        }

        BukkitPreLoginConnection.registerNewLazyCallback { event ->
            handlePunishmentsUsingEvent(event.uniqueId, event)
        }

        BukkitPostLoginConnection.registerNewCallback { player ->
            dispatchPermissionAttatchment(player)
        }

        BukkitPostLoginConnection.registerNewLazyCallback { player ->
            ensurePlayerIsNotBanEvading(player.uniqueId)
        }
    }

    fun loadAndEquipProfile(event: AsyncPlayerPreLoginEvent) {
        val start = System.currentTimeMillis()
        val profile = ProfileGameService.loadProfile(event.uniqueId, event.name)

        Bukkit.getLogger().log(Level.INFO, "Profile of " + event.name + " loaded in " + System.currentTimeMillis().minus(start) + "ms")
        MetricService.addMetric("Profile Service", Metric("Profile Service", System.currentTimeMillis().minus(start), System.currentTimeMillis()))

        val hostAddress = event.address.hostAddress
        val output = SHA.toHexString(hostAddress)!!
        val currentServer = Alchemist.globalServer

        profile.lastSeenAt = System.currentTimeMillis()
        profile.ip = output
        profile.currentSession = profile.createNewSession(currentServer)

        ProfileGameService.save(profile)
    }

    fun dispatchPermissionAttatchment(player: Player) {
        val profile = ProfileGameService.byId(player.uniqueId) ?: return

        val startPerms = System.currentTimeMillis()
        CompletableFuture.runAsync {
            AccessiblePermissionHandler.update(player, profile.getPermissions())
        }

        MetricService.addMetric("Permission Handler", Metric("Permission Handler", System.currentTimeMillis().minus(startPerms), System.currentTimeMillis()))
    }

    fun handlePunishmentsUsingEvent(profileId: UUID, event: AsyncPlayerPreLoginEvent) {
        val profile = AlchemistAPI.syncFindProfile(profileId) ?: return
        PunishmentService.recalculateUUID(profileId)

        if (profile.hasActivePunishment(PunishmentType.BAN) || profile.hasActivePunishment(PunishmentType.BLACKLIST)) {
            val option = profile.hasActivePunishment(PunishmentType.BAN)
            val punishment = profile.getActivePunishments(if (option) PunishmentType.BAN else PunishmentType.BLACKLIST).firstOrNull()
            val msgs = AlchemistSpigotPlugin.instance.config.getStringList("${if (option) "banned" else "blacklisted"}-join")

            msgs.replaceAll { it.replace("<reason>", punishment!!.reason) }
            msgs.replaceAll { it.replace("<expires>", if (punishment!!.expirable.duration == Long.MAX_VALUE) "Never" else TimeUtil.formatDuration(punishment.expirable.addedAt + punishment.expirable.duration - System.currentTimeMillis())) }

            event.loginResult = AsyncPlayerPreLoginEvent.Result.KICK_BANNED
            event.kickMessage = msgs.map { Chat.format(it) }.joinToString("\n")
        } else if (profile.alternateAccountHasBlacklist()) {
            val detectedPunishment: Punishment = profile.getFirstBlacklistFromAlts() ?: return

            val msgs = AlchemistSpigotPlugin.instance.config.getStringList("blacklisted-join-related")

            msgs.replaceAll { it.replace("<reason>", detectedPunishment.reason) }
            msgs.replaceAll { it.replace("<related>", AlchemistAPI.syncFindProfile(detectedPunishment.target)?.username ?: "N/A") }

            msgs.replaceAll { it.replace("<expires>", if (detectedPunishment.expirable.duration == Long.MAX_VALUE) "Never" else TimeUtil.formatDuration(detectedPunishment.expirable.addedAt + detectedPunishment.expirable.duration - System.currentTimeMillis())) }
            event.kickMessage = msgs.map { Chat.format(it) }.joinToString("\n")
        }
    }

    fun ensurePlayerIsNotBanEvading(profileId: UUID) {
        val profile = AlchemistAPI.syncFindProfile(profileId) ?: return
        CompletableFuture.supplyAsync {
            return@supplyAsync profile.getAltAccounts()
        }.thenApply { alts ->
            val isBanEvading = alts.size >= 1 && alts.any { it.hasActivePunishment(PunishmentType.BAN) || it.hasActivePunishment(PunishmentType.BLACKLIST) }

            if (isBanEvading) {
                AsynchronousRedisSender.send(StaffGeneralMessagePacket("&b[S] &3[${Alchemist.globalServer.displayName}] ${AlchemistAPI.getRankWithPrefix(profileId)} &3may be using an alt to evade a punishment!"))
            }
        }
    }

    fun calculateAndPostGrantables(profileId: UUID) {
        val profile = AlchemistAPI.syncFindProfile(profileId) ?: return

        val startGrants = System.currentTimeMillis()
        RankGrantService.recalculatePlayer(profile)
        MetricService.addMetric("Grants Service", Metric("Grants Service", System.currentTimeMillis().minus(startGrants), System.currentTimeMillis()))

        val startPunishments = System.currentTimeMillis()
        PunishmentService.recalculatePlayer(profile)
        MetricService.addMetric("Punishment Service", Metric("Punishment Service", System.currentTimeMillis().minus(startPunishments), System.currentTimeMillis()))
    }
}
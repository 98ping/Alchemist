package ltd.matrixstudios.alchemist.profiles

import ltd.matrixstudios.alchemist.AlchemistSpigotPlugin
import ltd.matrixstudios.alchemist.api.AlchemistAPI
import ltd.matrixstudios.alchemist.metric.Metric
import ltd.matrixstudios.alchemist.metric.MetricService
import ltd.matrixstudios.alchemist.models.grant.types.Punishment
import ltd.matrixstudios.alchemist.profiles.prelog.BukkitPreLoginConnection
import ltd.matrixstudios.alchemist.punishments.PunishmentType
import ltd.matrixstudios.alchemist.service.expirable.PunishmentService
import ltd.matrixstudios.alchemist.service.expirable.RankGrantService
import ltd.matrixstudios.alchemist.util.Chat
import ltd.matrixstudios.alchemist.util.TimeUtil
import org.bukkit.event.player.AsyncPlayerPreLoginEvent
import java.util.UUID

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
            calculateAndPostGrantables(event.uniqueId)
        }

        BukkitPreLoginConnection.registerNewLazyCallback { event ->
            handlePunishmentsUsingEvent(event.uniqueId, event)
        }
    }

    fun handlePunishmentsUsingEvent(profileId: UUID, event: AsyncPlayerPreLoginEvent)
    {
        val profile = AlchemistAPI.syncFindProfile(profileId) ?: return

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

    fun calculateAndPostGrantables(profileId: UUID)
    {
        val profile = AlchemistAPI.syncFindProfile(profileId) ?: return

        val startGrants = System.currentTimeMillis()
        RankGrantService.recalculatePlayer(profile)
        MetricService.addMetric("Grants Service", Metric("Grants Service", System.currentTimeMillis().minus(startGrants), System.currentTimeMillis()))

        val startPunishments = System.currentTimeMillis()
        PunishmentService.recalculatePlayer(profile)
        MetricService.addMetric("Punishment Service", Metric("Punishment Service", System.currentTimeMillis().minus(startPunishments), System.currentTimeMillis()))
    }
}
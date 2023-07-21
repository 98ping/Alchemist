package ltd.matrixstudios.alchemist.profiles.prelog.tasks

import ltd.matrixstudios.alchemist.AlchemistSpigotPlugin
import ltd.matrixstudios.alchemist.api.AlchemistAPI
import ltd.matrixstudios.alchemist.models.grant.types.Punishment
import ltd.matrixstudios.alchemist.profiles.prelog.BukkitPreLoginTask
import ltd.matrixstudios.alchemist.punishments.PunishmentType
import ltd.matrixstudios.alchemist.service.expirable.PunishmentService
import ltd.matrixstudios.alchemist.util.Chat
import ltd.matrixstudios.alchemist.util.TimeUtil
import org.bukkit.event.player.AsyncPlayerPreLoginEvent

/**
 * Class created on 7/20/2023

 * @author 98ping
 * @project Alchemist
 * @website https://solo.to/redis
 */
object HandlePunishments : BukkitPreLoginTask {

    override fun run(event: AsyncPlayerPreLoginEvent) {
        val profileId = event.uniqueId
        val profile = AlchemistAPI.syncFindProfile(profileId) ?: return

        if (profile.hasActivePunishment(PunishmentType.BAN) || profile.hasActivePunishment(PunishmentType.BLACKLIST)) {
            val option = profile.hasActivePunishment(PunishmentType.BAN)
            val punishment = profile.getActivePunishments(if (option) PunishmentType.BAN else PunishmentType.BLACKLIST).firstOrNull()
            val msgs = AlchemistSpigotPlugin.instance.config.getStringList("${if (option) "banned" else "blacklisted"}-join")

            msgs.replaceAll { it.replace("<reason>", punishment!!.reason) }
            msgs.replaceAll { it.replace("<expires>", if (punishment!!.expirable.duration == Long.MAX_VALUE) "Never" else TimeUtil.formatDuration(punishment.expirable.addedAt + punishment.expirable.duration - System.currentTimeMillis())) }

            event.loginResult = AsyncPlayerPreLoginEvent.Result.KICK_BANNED
            event.kickMessage = msgs.map { Chat.format(it) }.joinToString("\n")

            return
        }

        if (profile.alternateAccountHasBlacklist()) {
            val detectedPunishment: Punishment = profile.getFirstBlacklistFromAlts() ?: return

            val msgs = AlchemistSpigotPlugin.instance.config.getStringList("blacklisted-join-related")

            msgs.replaceAll { it.replace("<reason>", detectedPunishment.reason) }
            msgs.replaceAll { it.replace("<related>", AlchemistAPI.syncFindProfile(detectedPunishment.target)?.username ?: "N/A") }

            msgs.replaceAll { it.replace("<expires>", if (detectedPunishment.expirable.duration == Long.MAX_VALUE) "Never" else TimeUtil.formatDuration(detectedPunishment.expirable.addedAt + detectedPunishment.expirable.duration - System.currentTimeMillis())) }
            event.kickMessage = msgs.map { Chat.format(it) }.joinToString("\n")
        }
    }

    override fun shouldBeLazy(): Boolean {
        return true
    }
}
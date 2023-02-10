package ltd.matrixstudios.alchemist.listeners.profile

import com.google.common.base.Stopwatch
import com.google.gson.JsonObject
import ltd.matrixstudios.alchemist.AlchemistSpigotPlugin
import ltd.matrixstudios.alchemist.api.AlchemistAPI
import ltd.matrixstudios.alchemist.metric.Metric
import ltd.matrixstudios.alchemist.metric.MetricService
import ltd.matrixstudios.alchemist.permissions.AccessiblePermissionHandler
import ltd.matrixstudios.alchemist.punishments.PunishmentType
import ltd.matrixstudios.alchemist.service.expirable.PunishmentService
import ltd.matrixstudios.alchemist.service.expirable.RankGrantService
import ltd.matrixstudios.alchemist.service.profiles.ProfileGameService
import ltd.matrixstudios.alchemist.util.Chat
import ltd.matrixstudios.alchemist.util.SHA
import ltd.matrixstudios.alchemist.util.TimeUtil
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerChatEvent
import org.bukkit.event.player.AsyncPlayerPreLoginEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import java.util.*
import java.util.concurrent.CompletableFuture
import java.util.concurrent.TimeUnit
import java.util.logging.Level
import kotlin.collections.ArrayList


class ProfileJoinListener : Listener {

    @EventHandler
    fun autoFormatChat(event: AsyncPlayerChatEvent) {
        var prefixString = ""

        val profile = AlchemistAPI.quickFindProfile(event.player.uniqueId).get() ?: return

        if (profile.hasActivePunishment(PunishmentType.MUTE)) {
            val mute = profile.getActivePunishments(PunishmentType.MUTE).first()
            event.isCancelled = true

            val msgs = AlchemistSpigotPlugin.instance.config.getStringList("muted-chat")

            msgs.replaceAll { it.replace("<reason>", mute!!.reason) }
            msgs.replaceAll { it.replace("<expires>", if (mute!!.expirable.duration == Long.MAX_VALUE) "Never" else TimeUtil.formatDuration(mute.expirable.addedAt + mute.expirable.duration - System.currentTimeMillis())) }

            msgs.forEach { event.player.sendMessage(Chat.format(it)) }
            return
        }

        var colorString = ""

        if (profile.hasActivePrefix()) {

            val prefix = profile.getActivePrefix()

            if (prefix != null) {
                prefixString = prefix.prefix
            }
        }

        if (profile.activeColor != null)
        {
            colorString = profile.activeColor!!.chatColor
        }



        event.format = Chat.format((prefixString) + profile.getCurrentRank()!!.prefix + profile.getCurrentRank()!!.color + "%1\$s&7: &r${colorString}%2\$s")
    }

    @EventHandler
    fun applyPerms(event: PlayerJoinEvent) {
        val player = event.player
        val profile = ProfileGameService.byId(player.uniqueId) ?: return

        val startPerms = System.currentTimeMillis()
        CompletableFuture.runAsync {
            AccessiblePermissionHandler.update(player, profile.getPermissions())
        }

        MetricService.addMetric("Permission Handler", Metric("Permission Handler", System.currentTimeMillis().minus(startPerms), System.currentTimeMillis()))
    }

    @EventHandler
    fun join(event: AsyncPlayerPreLoginEvent) {
        val start = System.currentTimeMillis()
        val profile = ProfileGameService.loadProfile(event.uniqueId, event.name)

        Bukkit.getLogger().log(Level.INFO, "Profile of " + event.name + " loaded in " + System.currentTimeMillis().minus(start) + "ms")
        MetricService.addMetric("Profile Service", Metric("Profile Service", System.currentTimeMillis().minus(start), System.currentTimeMillis()))

         profile.lastSeenAt = System.currentTimeMillis()

        val hostAddress = event.address.hostAddress
        val output = SHA.toHexString(hostAddress)!!

        profile.ip = output

        val startGrants = System.currentTimeMillis()
        RankGrantService.recalculatePlayer(profile)
        MetricService.addMetric("Grants Service", Metric("Grants Service", System.currentTimeMillis().minus(startGrants), System.currentTimeMillis()))

        val startPunishments = System.currentTimeMillis()
        PunishmentService.recalculatePlayer(profile)
        MetricService.addMetric("Punishment Service", Metric("Punishment Service", System.currentTimeMillis().minus(startPunishments), System.currentTimeMillis()))

        if (profile.hasActivePunishment(PunishmentType.BAN)) {
            val punishment = profile.getActivePunishments(PunishmentType.BAN).firstOrNull()
            val msgs = AlchemistSpigotPlugin.instance.config.getStringList("banned-join")

            msgs.replaceAll { it.replace("<reason>", punishment!!.reason) }
            msgs.replaceAll { it.replace("<expires>", if (punishment!!.expirable.duration == Long.MAX_VALUE) "Never" else TimeUtil.formatDuration(punishment.expirable.addedAt + punishment.expirable.duration - System.currentTimeMillis())) }

            event.loginResult = AsyncPlayerPreLoginEvent.Result.KICK_BANNED
            event.kickMessage = msgs.joinToString("\n")
        } else if (profile.hasActivePunishment(PunishmentType.BLACKLIST)) {
            val punishment = profile.getActivePunishments(PunishmentType.BLACKLIST).firstOrNull()
            event.loginResult = AsyncPlayerPreLoginEvent.Result.KICK_BANNED
            val msgs = AlchemistSpigotPlugin.instance.config.getStringList("blacklisted-join")

            msgs.replaceAll { it.replace("<reason>", punishment!!.reason) }
            msgs.replaceAll { it.replace("<expires>", if (punishment!!.expirable.duration == Long.MAX_VALUE) "Never" else TimeUtil.formatDuration(punishment.expirable.addedAt + punishment.expirable.duration - System.currentTimeMillis())) }

            event.kickMessage = msgs.joinToString("\n")
        }

        val currentServer = AlchemistSpigotPlugin.instance.globalServer


        profile.currentSession = profile.createNewSession(currentServer)


        //doing this for syncing purposes and because the network manager needs to track when they were last on
        ProfileGameService.handler.storeAsync(profile.uuid, profile)

    }

    @EventHandler
    fun leave(event: PlayerQuitEvent)
    {
        val player = event.player

        AccessiblePermissionHandler.remove(player)

    }
}


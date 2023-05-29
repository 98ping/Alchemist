package ltd.matrixstudios.alchemist.profiles

import ltd.matrixstudios.alchemist.AlchemistSpigotPlugin
import ltd.matrixstudios.alchemist.api.AlchemistAPI
import ltd.matrixstudios.alchemist.metric.Metric
import ltd.matrixstudios.alchemist.metric.MetricService
import ltd.matrixstudios.alchemist.permissions.AccessiblePermissionHandler
import ltd.matrixstudios.alchemist.profiles.prelog.BukkitPreLoginConnection
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
import java.util.concurrent.CompletableFuture
import java.util.logging.Level


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
        val allCallbacks = mutableListOf<(AsyncPlayerPreLoginEvent) -> Unit>().also {
            it.addAll(BukkitPreLoginConnection.allCallbacks + BukkitPreLoginConnection.allLazyCallbacks)
        }

        for (cback in allCallbacks) cback.invoke(event)
    }

    @EventHandler
    fun leave(event: PlayerQuitEvent)
    {
        val player = event.player

        AccessiblePermissionHandler.remove(player)
    }
}


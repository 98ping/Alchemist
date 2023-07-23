package ltd.matrixstudios.alchemist.profiles

import ltd.matrixstudios.alchemist.Alchemist
import ltd.matrixstudios.alchemist.AlchemistSpigotPlugin
import ltd.matrixstudios.alchemist.api.AlchemistAPI
import ltd.matrixstudios.alchemist.models.ranks.Rank
import ltd.matrixstudios.alchemist.packets.StaffMessagePacket
import ltd.matrixstudios.alchemist.profiles.permissions.AccessiblePermissionHandler
import ltd.matrixstudios.alchemist.profiles.connection.postlog.BukkitPostLoginConnection
import ltd.matrixstudios.alchemist.profiles.connection.prelog.BukkitPreLoginConnection
import ltd.matrixstudios.alchemist.punishments.PunishmentType
import ltd.matrixstudios.alchemist.redis.AsynchronousRedisSender
import ltd.matrixstudios.alchemist.service.ranks.RankService
import ltd.matrixstudios.alchemist.util.Chat
import ltd.matrixstudios.alchemist.util.TimeUtil
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerChatEvent
import org.bukkit.event.player.AsyncPlayerPreLoginEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent


class ProfileJoinListener : Listener {

    @EventHandler
    fun autoFormatChat(event: AsyncPlayerChatEvent) {
        var prefixString = ""

        val profile = AlchemistAPI.quickFindProfile(event.player.uniqueId).get() ?: return

        if (event.player.hasPermission("alchemist.staff") && profile.hasMetadata("allMSGSC")) {
            event.isCancelled = true
            val message = event.message
            AsynchronousRedisSender.send(StaffMessagePacket(message, Alchemist.globalServer.displayName, event.player.uniqueId))

            return
        }

        if (profile.hasActivePunishment(PunishmentType.MUTE)) {
            val mute = profile.getActivePunishments(PunishmentType.MUTE).first()
            event.isCancelled = true

            val msgs = AlchemistSpigotPlugin.instance.config.getStringList("muted-chat")

            msgs.replaceAll { it.replace("<reason>", mute.reason) }
            msgs.replaceAll { it.replace("<expires>", if (mute.expirable.duration == Long.MAX_VALUE) "Never" else TimeUtil.formatDuration(mute.expirable.addedAt + mute.expirable.duration - System.currentTimeMillis())) }

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

        if (profile.activeColor != null) {
            colorString = profile.activeColor!!.chatColor
        }

        var rank = RankService.FALLBACK_RANK

        if (profile.rankDisguiseAttribute != null) {
            val curr = RankService.byId(profile.rankDisguiseAttribute!!.rank)
            if (curr != null) {
                rank = curr
            }
        }

        if (profile.getCurrentRank() != null) {
            rank = profile.getCurrentRank()!!
        }


        event.format = Chat.format((prefixString) + rank.prefix + rank.color + "%1\$s&7: &r${colorString}%2\$s")
    }

    @EventHandler(priority = EventPriority.LOWEST)
    fun applyPerms(event: PlayerJoinEvent) {
        val player = event.player

        val allCallbacks = mutableListOf<(Player) -> Unit>().also {
            it.addAll(BukkitPostLoginConnection.allCallbacks + BukkitPostLoginConnection.allLazyCallbacks)
        }

        for (cback in allCallbacks) {
            cback.invoke(player)
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    fun join(event: AsyncPlayerPreLoginEvent) {
        val allCallbacks = mutableListOf<(AsyncPlayerPreLoginEvent) -> Unit>().also {
            it.addAll(BukkitPreLoginConnection.allCallbacks + BukkitPreLoginConnection.allLazyCallbacks)
        }

        for (cback in allCallbacks) {
            cback.invoke(event)
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    fun leave(event: PlayerQuitEvent)
    {
        val player = event.player

        AccessiblePermissionHandler.remove(player)
    }
}


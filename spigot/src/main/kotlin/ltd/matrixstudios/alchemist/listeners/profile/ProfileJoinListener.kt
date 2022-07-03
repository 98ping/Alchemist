package ltd.matrixstudios.alchemist.listeners.profile

import com.google.common.base.Stopwatch
import com.google.gson.JsonObject
import ltd.matrixstudios.alchemist.AlchemistSpigotPlugin
import ltd.matrixstudios.alchemist.api.AlchemistAPI
import ltd.matrixstudios.alchemist.models.grant.types.Punishment
import ltd.matrixstudios.alchemist.models.profile.GameProfile
import ltd.matrixstudios.alchemist.permissions.AccessiblePermissionHandler
import ltd.matrixstudios.alchemist.punishments.PunishmentType
import ltd.matrixstudios.alchemist.service.expirable.RankGrantService
import ltd.matrixstudios.alchemist.service.profiles.ProfileGameService
import ltd.matrixstudios.alchemist.service.profiles.ProfileSearchService
import ltd.matrixstudios.alchemist.service.tags.TagService
import ltd.matrixstudios.alchemist.util.Chat
import ltd.matrixstudios.alchemist.util.SHA
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.event.player.AsyncPlayerChatEvent
import org.bukkit.event.player.AsyncPlayerPreLoginEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.scheduler.BukkitRunnable
import sun.java2d.cmm.Profile
import java.util.*
import java.util.concurrent.CompletableFuture
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList
import kotlin.concurrent.thread


class ProfileJoinListener : Listener {

    @EventHandler
    fun autoFormatChat(event: AsyncPlayerChatEvent) {
        var prefixString = ""

        val profile = AlchemistAPI.quickFindProfile(event.player.uniqueId).get() ?: return

        if (profile.hasActivePunishment(PunishmentType.MUTE)) {
            event.isCancelled = true
            event.player.sendMessage(Chat.format("&cYou are currently muted."))
            return
        }

        if (profile.hasActivePrefix()) {

            val prefix = profile.getActivePrefix()

            if (prefix != null) {
                prefixString = prefix.prefix
            }
        }

        event.format = Chat.format((prefixString) + profile.getCurrentRank()!!.prefix + "%1\$s&7: &r%2\$s")
    }

    @EventHandler
    fun applyPerms(event: PlayerJoinEvent) {
        val player = event.player

        val perms = AccessiblePermissionHandler.pendingLoadPermissions.getOrDefault(player.uniqueId, mapOf())

        CompletableFuture.runAsync {
            AccessiblePermissionHandler.update(
                player,
                perms
            )
        }


    }

    @EventHandler
    fun join(event: AsyncPlayerPreLoginEvent) {
        ProfileSearchService.exists(event.uniqueId).thenAcceptAsync {

            if (!it) {
                val stopwatch = Stopwatch.createStarted()

                val profile = GameProfile(
                    event.uniqueId,
                    event.name,
                    JsonObject(),
                    ArrayList(),
                    ArrayList(),
                    ArrayList(),
                    null
                )

                ProfileGameService.save(
                    profile
                )

                stopwatch.stop()

                println("Profile creation for " + event.name + " took " + stopwatch.elapsed(TimeUnit.MILLISECONDS) + "ms")

            }
        }

        ProfileSearchService.getAsync(event.uniqueId).thenAccept {

            ProfileGameService.cache[event.uniqueId] = it

            if (it == null) {
                event.loginResult = AsyncPlayerPreLoginEvent.Result.KICK_OTHER
                event.kickMessage = Chat.format("&cYour profile failed to load")

                return@thenAccept
            }

            if (it.hasActivePunishment(PunishmentType.BAN)) {
                event.loginResult = AsyncPlayerPreLoginEvent.Result.KICK_BANNED
                event.kickMessage = Chat.format("&cYou are currently banned from the server")
            } else if (it.hasActivePunishment(PunishmentType.BLACKLIST)) {
                event.loginResult = AsyncPlayerPreLoginEvent.Result.KICK_BANNED
                event.kickMessage = Chat.format("&cYou are currently blacklisted from the server")
            }

            AccessiblePermissionHandler.setupPlayer(event.uniqueId, it.getPermissions())
        }
    }

}


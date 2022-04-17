package ltd.matrixstudios.alchemist.listeners.profile

import com.google.common.base.Stopwatch
import com.google.gson.JsonObject
import ltd.matrixstudios.alchemist.models.profile.GameProfile
import ltd.matrixstudios.alchemist.permissions.AccessiblePermissionHandler
import ltd.matrixstudios.alchemist.punishments.PunishmentType
import ltd.matrixstudios.alchemist.service.profiles.ProfileGameService
import ltd.matrixstudios.alchemist.util.Chat
import ltd.matrixstudios.alchemist.util.SHA
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.event.player.AsyncPlayerPreLoginEvent
import org.bukkit.event.player.PlayerJoinEvent
import sun.java2d.cmm.Profile
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList


class ProfileJoinListener : Listener {

    @EventHandler
    fun applyPerms(event: PlayerJoinEvent) {
        val player = event.player

        val profile = ProfileGameService.byId(player.uniqueId)

        if (profile == null) {
            player.kickPlayer(Chat.format("&cYour profile was not able to be loaded so permissions could not be applied"))
            return
        }

        AccessiblePermissionHandler.update(player, profile.getPermissions())
    }


    @EventHandler
    fun join(event: AsyncPlayerPreLoginEvent) {
        if (ProfileGameService.byId(event.uniqueId) == null) {
            val stopwatch = Stopwatch.createStarted()

            ProfileGameService.save(GameProfile(event.uniqueId, event.name, JsonObject(), ArrayList()))

            stopwatch.stop()

            println("Profile creation for " + event.name + " took " + stopwatch.elapsed(TimeUnit.MILLISECONDS) + "ms")

        }

        val profile = ProfileGameService.byId(event.uniqueId)

        if (profile == null) {
            event.loginResult = AsyncPlayerPreLoginEvent.Result.KICK_OTHER
            event.kickMessage = Chat.format("&cYour profile failed to load")

            return
        }

        if (profile.hasActivePunishment(PunishmentType.BAN)) {
            event.loginResult = AsyncPlayerPreLoginEvent.Result.KICK_BANNED
            event.kickMessage = Chat.format("&cYou are currently banned from the server")
        } else if (profile.hasActivePunishment(PunishmentType.BLACKLIST)) {
            event.loginResult = AsyncPlayerPreLoginEvent.Result.KICK_BANNED
            event.kickMessage = Chat.format("&cYou are currently blacklisted from the server")
        }
    }
}
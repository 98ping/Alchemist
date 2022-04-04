package ltd.matrixstudios.alchemist.listeners.profile

import com.google.common.base.Stopwatch
import com.google.gson.JsonObject
import ltd.matrixstudios.alchemist.models.profile.GameProfile
import ltd.matrixstudios.alchemist.service.profiles.ProfileGameService
import ltd.matrixstudios.alchemist.util.SHA
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerPreLoginEvent
import java.util.*
import java.util.concurrent.TimeUnit


class ProfileJoinListener : Listener {

    @EventHandler
    fun join(event: AsyncPlayerPreLoginEvent) {
        if (ProfileGameService.byId(event.uniqueId) == null) {
            val stopwatch = Stopwatch.createUnstarted()
            stopwatch.start()

            val profile = GameProfile(event.uniqueId, event.name, JsonObject(), Collections.singletonList(SHA.toHexString(event.address.hostAddress)!!))
            ProfileGameService.save(profile)

            stopwatch.stop()

            println("Profile creation for " + event.name + " took " + stopwatch.elapsed(TimeUnit.MILLISECONDS) + "ms")
        }
    }
}
package ltd.matrixstudios.alchemist.profiles

import ltd.matrixstudios.alchemist.service.profiles.ProfileGameService
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

class EnsureProfileListener : Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    fun onJoin(event: PlayerJoinEvent) {
        ProfileGameService.ensureProfile(event.player.uniqueId, event.player.name)
    }
}

package ltd.matrixstudios.alchemist.network.listener

import ltd.matrixstudios.alchemist.AlchemistSpigotPlugin
import ltd.matrixstudios.alchemist.api.AlchemistAPI
import ltd.matrixstudios.alchemist.service.profiles.ProfileGameService
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerPreLoginEvent
import org.bukkit.event.player.PlayerQuitEvent

class NetworkJoinAndLeaveListener : Listener {

    @EventHandler
    fun disconnect(e: PlayerQuitEvent) {
        AlchemistAPI.quickFindProfile(e.player.uniqueId).thenApply {
            if (it != null) {
                it.metadata.addProperty("server", "None")

                it.lastSeenAt = System.currentTimeMillis()

                ProfileGameService.save(it)
            }
        }
    }

    @EventHandler
    fun asyncJoin(e: AsyncPlayerPreLoginEvent) {
        AlchemistAPI.quickFindProfile(e.uniqueId).thenApply {
            if (it != null) {
                it.metadata.addProperty("server", AlchemistSpigotPlugin.instance.globalServer.id)

                it.lastSeenAt = System.currentTimeMillis()

                ProfileGameService.save(it)
            }
        }
    }

}
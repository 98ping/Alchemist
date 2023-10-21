package ltd.matrixstudios.alchemist.network.listener

import ltd.matrixstudios.alchemist.Alchemist
import ltd.matrixstudios.alchemist.api.AlchemistAPI
import ltd.matrixstudios.alchemist.service.profiles.ProfileGameService
import ltd.matrixstudios.alchemist.service.session.SessionService
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerPreLoginEvent
import org.bukkit.event.player.PlayerQuitEvent

class NetworkJoinAndLeaveListener : Listener
{

    @EventHandler
    fun disconnect(e: PlayerQuitEvent)
    {
        AlchemistAPI.quickFindProfile(e.player.uniqueId).thenApply {
            if (it != null)
            {
                it.metadata.addProperty("server", "None")

                it.lastSeenAt = System.currentTimeMillis()

                if (it.currentSession != null)
                {
                    it.currentSession!!.leftAt = System.currentTimeMillis()
                    SessionService.save(it.currentSession!!)

                    it.currentSession = null
                }


                ProfileGameService.save(it)
            }
        }
    }

    @EventHandler
    fun asyncJoin(e: AsyncPlayerPreLoginEvent)
    {
        val profile = AlchemistAPI.syncFindProfile(e.uniqueId) ?: return

        profile.metadata.addProperty("server", Alchemist.globalServer.id)
        profile.lastSeenAt = System.currentTimeMillis()

        ProfileGameService.save(profile)
    }
}
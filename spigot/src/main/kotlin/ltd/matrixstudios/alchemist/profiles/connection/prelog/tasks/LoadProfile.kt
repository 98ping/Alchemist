package ltd.matrixstudios.alchemist.profiles.connection.prelog.tasks

import ltd.matrixstudios.alchemist.Alchemist
import ltd.matrixstudios.alchemist.cache.types.UUIDCache
import ltd.matrixstudios.alchemist.metric.Metric
import ltd.matrixstudios.alchemist.metric.MetricService
import ltd.matrixstudios.alchemist.profiles.connection.prelog.BukkitPreLoginTask
import ltd.matrixstudios.alchemist.service.profiles.ProfileGameService
import ltd.matrixstudios.alchemist.util.SHA
import org.bukkit.Bukkit
import org.bukkit.event.player.AsyncPlayerPreLoginEvent
import java.util.*
import java.util.logging.Level

/**
 * Class created on 7/20/2023

 * @author 98ping
 * @project Alchemist
 * @website https://solo.to/redis
 */
object LoadProfile : BukkitPreLoginTask
{
    override fun run(event: AsyncPlayerPreLoginEvent)
    {
        try
        {
            val start = System.currentTimeMillis()
            val profile = ProfileGameService.loadProfile(event.uniqueId, event.name)

            Bukkit.getLogger().log(
                Level.INFO,
                "Profile of " + event.name + " loaded in " + System.currentTimeMillis().minus(start) + "ms"
            )
            MetricService.addMetric(
                "Profile Service",
                Metric("Profile Service", System.currentTimeMillis().minus(start), System.currentTimeMillis())
            )

            val hostAddress = event.address.hostAddress
            val output = SHA.toHexString(hostAddress)!!
            val currentServer = Alchemist.globalServer

            profile.lastSeenAt = System.currentTimeMillis()
            profile.username = event.name
            profile.lowercasedUsername = event.name.lowercase(Locale.getDefault())
            profile.ip = output

            if (profile.currentSession == null)
            {
                profile.currentSession = profile.createNewSession(currentServer)
            }

            UUIDCache.addToFirstCache(profile.uuid, profile.lowercasedUsername)
            UUIDCache.addToSecondCache(profile.lowercasedUsername, profile.uuid)

            ProfileGameService.saveSync(profile)
        } catch (e: Exception) {
            Bukkit.getLogger().log(Level.SEVERE, "Caught an exception: ${e.message}")
        }
    }

    override fun shouldBeLazy(): Boolean
    {
        return false
    }
}
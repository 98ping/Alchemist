package ltd.matrixstudios.alchemist.profiles.connection.postlog.tasks

import ltd.matrixstudios.alchemist.metric.Metric
import ltd.matrixstudios.alchemist.metric.MetricService
import ltd.matrixstudios.alchemist.profiles.permissions.AccessiblePermissionHandler
import ltd.matrixstudios.alchemist.profiles.connection.postlog.BukkitPostLoginTask
import ltd.matrixstudios.alchemist.service.profiles.ProfileGameService
import org.bukkit.entity.Player
import java.util.concurrent.CompletableFuture

/**
 * Class created on 7/20/2023

 * @author 98ping
 * @project Alchemist
 * @website https://solo.to/redis
 */
object LoadPermissions : BukkitPostLoginTask {

    override fun run(player: Player) {
        val profile = ProfileGameService.byId(player.uniqueId) ?: return

        val startPerms = System.currentTimeMillis()
        AccessiblePermissionHandler.update(player, profile.getPermissions())

        MetricService.addMetric("Permission Handler", Metric("Permission Handler", System.currentTimeMillis().minus(startPerms), System.currentTimeMillis()))
    }
}

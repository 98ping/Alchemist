package ltd.matrixstudios.alchemist.profiles.connection.prelog.tasks

import ltd.matrixstudios.alchemist.api.AlchemistAPI
import ltd.matrixstudios.alchemist.metric.Metric
import ltd.matrixstudios.alchemist.metric.MetricService
import ltd.matrixstudios.alchemist.profiles.connection.prelog.BukkitPreLoginTask
import ltd.matrixstudios.alchemist.service.expirable.PunishmentService
import ltd.matrixstudios.alchemist.service.expirable.RankGrantService
import org.bukkit.event.player.AsyncPlayerPreLoginEvent

/**
 * Class created on 7/20/2023

 * @author 98ping
 * @project Alchemist
 * @website https://solo.to/redis
 */
object CalculateGrantables : BukkitPreLoginTask {

    override fun run(event: AsyncPlayerPreLoginEvent) {
        val profileId = event.uniqueId
        val profile = AlchemistAPI.syncFindProfile(profileId) ?: return

        val startGrants = System.currentTimeMillis()
        RankGrantService.recalculatePlayerSync(profile)
        MetricService.addMetric("Grants Service", Metric("Grants Service", System.currentTimeMillis().minus(startGrants), System.currentTimeMillis()))

        val startPunishments = System.currentTimeMillis()
        PunishmentService.recalculatePlayerSync(profile)
        MetricService.addMetric("Punishment Service", Metric("Punishment Service", System.currentTimeMillis().minus(startPunishments), System.currentTimeMillis()))
    }

    override fun shouldBeLazy(): Boolean {
        return false
    }
}
package ltd.matrixstudios.alchemist.statistic

import ltd.matrixstudios.alchemist.AlchemistSpigotPlugin
import ltd.matrixstudios.alchemist.service.profiles.ProfileGameService
import org.bukkit.Bukkit
import java.util.concurrent.Callable


object StatisticManager {

    lateinit var metrics: Metric

    fun loadStats()
    {
        val pluginId = 16260

        this.metrics = Metric(AlchemistSpigotPlugin.instance, pluginId)

        metrics.addCustomChart(
            Metric.SingleLineChart(
                "profiles",
                Callable {
                    ProfileGameService.handler.retrieveAll().size
                })
        )
    }

}
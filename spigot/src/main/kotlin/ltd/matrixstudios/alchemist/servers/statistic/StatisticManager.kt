package ltd.matrixstudios.alchemist.servers.statistic

import ltd.matrixstudios.alchemist.AlchemistSpigotPlugin


object StatisticManager
{

    lateinit var metrics: Metric

    fun loadStats()
    {
        val pluginId = 16260

        metrics = Metric(AlchemistSpigotPlugin.instance, pluginId)
    }

}
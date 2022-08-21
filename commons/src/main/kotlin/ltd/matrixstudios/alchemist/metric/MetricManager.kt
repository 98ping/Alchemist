package ltd.matrixstudios.alchemist.metric

import ltd.matrixstudios.alchemist.metric.types.ProfileTimings
import ltd.matrixstudios.alchemist.metric.types.RankGrantTimings

object MetricManager {

    var metrics = hashMapOf<String, Metric>()

    fun loadMetrics()
    {
        metrics["profile"] = ProfileTimings()
        metrics["grants"] = RankGrantTimings()
    }

    fun get(value: String) : String
    {
        val metric = metrics[value]!!
        return getStatusColor(metric.fetch()) + metric.fetch() + "ms"
    }

    fun getStatusColor(value: Long)  : String
    {
        val toInt = value.toInt()

        if (toInt < 20)
        {
            return "&a"
        }

        if (toInt < 40)
        {
            return "&e"
        }

        if (toInt > 40)
        {
            return "&c"
        }

        return "&a"
    }
}
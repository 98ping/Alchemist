package ltd.matrixstudios.alchemist.statistics

data class ConnectionStatistics(
    val pings: Long,
    val attempts: Long,
    val successful: Long
)

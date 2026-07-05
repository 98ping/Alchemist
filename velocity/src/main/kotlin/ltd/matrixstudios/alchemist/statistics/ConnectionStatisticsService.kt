package ltd.matrixstudios.alchemist.statistics

import ltd.matrixstudios.alchemist.redis.RedisPacketManager
import java.util.concurrent.CompletableFuture

object ConnectionStatisticsService {

    private const val STATISTICS_KEY = "Alchemist:ConnectionStatistics"

    fun trackServerListPing() = increment("pings")

    fun trackConnectionAttempt() = increment("attempts")

    fun trackSuccessfulConnection() = increment("successful")

    private fun increment(field: String) {
        CompletableFuture.runAsync {
            RedisPacketManager.pool.resource.use { jedis ->
                jedis.hincrBy(STATISTICS_KEY, field, 1L)
            }
        }
    }
}

package ltd.matrixstudios.alchemist.statistics

import ltd.matrixstudios.alchemist.redis.RedisPacketManager
import java.util.concurrent.CompletableFuture

object ConnectionStatisticsService {

    private const val STATISTICS_KEY = "Alchemist:ConnectionStatistics"

    fun trackServerListPing() = increment("pings")

    fun trackConnectionAttempt() = increment("attempts")

    fun trackSuccessfulConnection() = increment("successful")

    fun fetchStatistics(): ConnectionStatistics {
        return RedisPacketManager.pool.resource.use { jedis ->
            val entries = jedis.hgetAll(STATISTICS_KEY) ?: emptyMap()
            ConnectionStatistics(
                pings = entries["pings"]?.toLongOrNull() ?: 0L,
                attempts = entries["attempts"]?.toLongOrNull() ?: 0L,
                successful = entries["successful"]?.toLongOrNull() ?: 0L
            )
        }
    }

    fun reset() {
        RedisPacketManager.pool.resource.use { jedis ->
            jedis.del(STATISTICS_KEY)
        }
    }

    private fun increment(field: String) {
        CompletableFuture.runAsync {
            RedisPacketManager.pool.resource.use { jedis ->
                jedis.hincrBy(STATISTICS_KEY, field, 1L)
            }
        }
    }
}

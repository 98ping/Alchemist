package ltd.matrixstudios.alchemist.service.fakeplayers

import ltd.matrixstudios.alchemist.redis.RedisPacketManager
import java.util.Locale
import java.util.concurrent.atomic.AtomicInteger

object FakePlayerCountService
{

    private const val KEY = "Alchemist:FakePlayerCounts"
    private const val STALE_AFTER_MS = 15000L

    private val cachedTotal = AtomicInteger(0)

    fun publish(serverId: String, count: Int)
    {
        RedisPacketManager.pool.resource.use { jedis ->
            jedis.hset(
                KEY,
                serverId.lowercase(Locale.getDefault()),
                count.toString() + "|" + System.currentTimeMillis()
            )
        }
    }

    fun clear(serverId: String)
    {
        RedisPacketManager.pool.resource.use { jedis ->
            jedis.hdel(KEY, serverId.lowercase(Locale.getDefault()))
        }
    }

    fun refreshTotal()
    {
        cachedTotal.set(fetchTotal())
    }

    fun cachedTotal(): Int
    {
        return cachedTotal.get()
    }

    fun fetchTotal(): Int
    {
        return RedisPacketManager.pool.resource.use { jedis ->
            val entries = jedis.hgetAll(KEY) ?: return@use 0
            val now = System.currentTimeMillis()
            var total = 0

            for ((serverId, value) in entries)
            {
                val parts = value.split("|")
                val count = parts.getOrNull(0)?.toIntOrNull()
                val updatedAt = parts.getOrNull(1)?.toLongOrNull()

                if (count == null || updatedAt == null || now - updatedAt > STALE_AFTER_MS)
                {
                    jedis.hdel(KEY, serverId)
                    continue
                }

                total += count
            }

            total
        }
    }
}

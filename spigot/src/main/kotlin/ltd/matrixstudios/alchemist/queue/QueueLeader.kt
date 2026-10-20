package ltd.matrixstudios.alchemist.queue

import ltd.matrixstudios.alchemist.Alchemist
import ltd.matrixstudios.alchemist.redis.RedisPacketManager
import redis.clients.jedis.params.SetParams

object QueueLeader
{

    private const val KEY = "Alchemist:QueueLeader"
    private const val LEASE_MS = 10000L

    fun claim(): Boolean
    {
        val id = Alchemist.globalServer.id

        return RedisPacketManager.pool.resource.use { jedis ->
            if (jedis.get(KEY) == id)
            {
                jedis.pexpire(KEY, LEASE_MS) == 1L
            } else
            {
                jedis.set(KEY, id, SetParams().nx().px(LEASE_MS)) != null
            }
        }
    }
}

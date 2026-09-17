package ltd.matrixstudios.alchemist.lockdown

import com.velocitypowered.api.proxy.Player
import ltd.matrixstudios.alchemist.redis.RedisPacketManager
import java.util.concurrent.atomic.AtomicBoolean

object LockdownManager {

    private const val LOCKDOWN_KEY = "Alchemist:Lockdown"

    private val cachedLockdown = AtomicBoolean(false)

    fun serverIsOnLockdown(): Boolean {
        RedisPacketManager.pool.resource.use { jedis ->
            return jedis.get(LOCKDOWN_KEY)?.toBoolean() ?: false
        }
    }

    fun setLockdown(active: Boolean) {
        RedisPacketManager.pool.resource.use { jedis ->
            jedis.set(LOCKDOWN_KEY, active.toString())
        }
        cachedLockdown.set(active)
    }

    fun refreshCache() {
        cachedLockdown.set(serverIsOnLockdown())
    }

    fun cachedServerIsOnLockdown(): Boolean {
        return cachedLockdown.get()
    }

    fun hasClearance(player: Player): Boolean {
        return player.hasPermission("alchemist.clearance")
    }
}

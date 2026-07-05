package ltd.matrixstudios.alchemist.lockdown

import com.velocitypowered.api.proxy.Player
import ltd.matrixstudios.alchemist.redis.RedisPacketManager

object LockdownManager {

    private const val LOCKDOWN_KEY = "Alchemist:Lockdown"

    fun serverIsOnLockdown(): Boolean {
        RedisPacketManager.pool.resource.use { jedis ->
            return jedis.get(LOCKDOWN_KEY)?.toBoolean() ?: false
        }
    }

    fun setLockdown(active: Boolean) {
        RedisPacketManager.pool.resource.use { jedis ->
            jedis.set(LOCKDOWN_KEY, active.toString())
        }
    }

    fun hasClearance(player: Player): Boolean {
        return player.hasPermission("alchemist.clearance")
    }
}

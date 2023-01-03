package ltd.matrixstudios.alchemist.staff.requests.handlers

import org.bukkit.entity.Player
import java.util.UUID
import java.util.concurrent.TimeUnit

object RequestHandler {
    val requestCooldowns = mutableMapOf<UUID, Long>()
    val reportCooldowns = mutableMapOf<UUID, Long>()

    fun isOnRequestCooldown(player: Player) : Boolean
    {
        if (!requestCooldowns.containsKey(player.uniqueId)) return false

        val value = requestCooldowns[player.uniqueId]!!

        return (System.currentTimeMillis().minus(value) >= TimeUnit.MINUTES.toMillis(5L))
    }

    fun isOnReportCooldown(player: Player) : Boolean
    {
        if (!reportCooldowns.containsKey(player.uniqueId)) return false

        val value = reportCooldowns[player.uniqueId]!!

        return (System.currentTimeMillis().minus(value) >= TimeUnit.MINUTES.toMillis(5L))
    }
}
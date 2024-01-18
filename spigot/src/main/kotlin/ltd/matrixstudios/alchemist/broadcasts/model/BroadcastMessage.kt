package ltd.matrixstudios.alchemist.broadcasts.model

import ltd.matrixstudios.alchemist.broadcasts.BroadcastService
import ltd.matrixstudios.alchemist.broadcasts.condition.BroadcastCondition
import org.bukkit.entity.Player

/**
 * Class created on 6/17/2023

 * @author 98ping
 * @project Alchemist
 * @website https://solo.to/redis
 */
class BroadcastMessage(
    var id: String,
    var lines: MutableList<String> = mutableListOf(),
    val conditions: MutableList<BroadcastCondition> = mutableListOf()
)
{
    fun canBeSentToPlayer(player: Player) = conditions.all { it.shouldShowToPlayer(player) }

    fun save()
    {
        val cached = BroadcastService.cached()
            ?: return

        cached.broadcasts[id] = this
        BroadcastService.cache(cached)
    }
}
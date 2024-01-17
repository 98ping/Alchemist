package ltd.matrixstudios.alchemist.broadcasts.condition.types

import ltd.matrixstudios.alchemist.broadcasts.condition.ConditionType
import ltd.matrixstudios.alchemist.profiles.getCurrentRank
import org.bukkit.entity.Player

/**
 * Class created on 1/16/2024

 * @author 98ping
 * @project Alchemist
 * @website https://solo.to/redis
 */
class UserRankCondition : ConditionType()
{
    override fun testFor(player: Player, value: String): Boolean
    {
        val rankWeight = value.toIntOrNull()
            ?: return false

        val playerRank = player.getCurrentRank()

        return playerRank.weight <= rankWeight
    }
}
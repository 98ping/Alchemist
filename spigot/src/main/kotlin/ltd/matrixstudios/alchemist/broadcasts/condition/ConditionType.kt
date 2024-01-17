package ltd.matrixstudios.alchemist.broadcasts.condition

import org.bukkit.entity.Player

/**
 * Class created on 1/16/2024

 * @author 98ping
 * @project Alchemist
 * @website https://solo.to/redis
 */
abstract class ConditionType {

    abstract fun testFor(player: Player, value: String): Boolean
}
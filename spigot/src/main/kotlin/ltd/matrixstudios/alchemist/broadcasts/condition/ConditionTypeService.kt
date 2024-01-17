package ltd.matrixstudios.alchemist.broadcasts.condition

import ltd.matrixstudios.alchemist.broadcasts.condition.types.UserRankCondition

/**
 * Class created on 1/16/2024

 * @author 98ping
 * @project Alchemist
 * @website https://solo.to/redis
 */
object ConditionTypeService
{
    val allTypes = mutableMapOf(
        "rank" to UserRankCondition()
    )
}
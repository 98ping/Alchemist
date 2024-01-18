package ltd.matrixstudios.alchemist.broadcasts.condition

import org.bukkit.entity.Player

data class BroadcastCondition(
    var logicGate: LogicGate,
    var condition: String,
    val reference: String? = null
)
{

    fun shouldShowToPlayer(player: Player): Boolean
    {
        val split = condition.split(":")
        val key = split[0]
        val value = split[1]

        val conditionType = ConditionTypeService.allTypes[key]
            ?: return false

        when (logicGate)
        {
            LogicGate.And ->
            {
                return conditionType.testFor(player, value)
            }

            LogicGate.Not ->
            {
                return !conditionType.testFor(player, value)
            }
            LogicGate.Or ->
            {

            }
        }

        return true
    }

    enum class LogicGate(val chatColor: String, val displayName: String)
    {
        And("&a", "And"),
        Not("&c", "Not"),
        Or("&9", "Or")
    }
}
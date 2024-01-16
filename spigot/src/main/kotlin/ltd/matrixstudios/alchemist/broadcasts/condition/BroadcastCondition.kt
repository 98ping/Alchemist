package ltd.matrixstudios.alchemist.broadcasts.condition

import org.bukkit.ChatColor


data class BroadcastCondition(
    val logicGate: LogicGate,
    val condition: String,
    val reference: String
)
{

    enum class LogicGate(val chatColor: String, val displayName: String)
    {
        And("&a", "And"),
        Not("&c", "Not"),
        Or("&9", "Or")
    }
}
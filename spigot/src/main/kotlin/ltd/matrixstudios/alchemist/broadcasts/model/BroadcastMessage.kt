package ltd.matrixstudios.alchemist.broadcasts.model

import ltd.matrixstudios.alchemist.broadcasts.condition.BroadcastCondition

/**
 * Class created on 6/17/2023

 * @author 98ping
 * @project Alchemist
 * @website https://solo.to/redis
 */
class BroadcastMessage(
    var id: String,
    val lines: MutableList<String> = mutableListOf(),
    val conditions: MutableList<BroadcastCondition> = mutableListOf()
)
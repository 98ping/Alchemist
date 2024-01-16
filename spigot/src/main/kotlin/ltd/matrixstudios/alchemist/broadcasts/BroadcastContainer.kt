package ltd.matrixstudios.alchemist.broadcasts

import ltd.matrixstudios.alchemist.broadcasts.model.BroadcastMessage

data class BroadcastContainer(
    val broadcasts: MutableMap<String, BroadcastMessage> = mutableMapOf()
)
{
    fun getBroadcastMessage(id: String) = broadcasts[id]
}

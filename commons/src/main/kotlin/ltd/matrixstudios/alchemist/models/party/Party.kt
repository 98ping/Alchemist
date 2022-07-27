package ltd.matrixstudios.alchemist.models.party

import java.util.UUID

data class Party(
    var id: UUID,
    var leader: UUID,
    var moderators: MutableList<UUID>,
    var members: MutableList<UUID>,
    var invited: MutableMap<UUID, Long>,
    var createdAt: Long,
    var alive: Boolean
) {
    fun getAllMembers(): MutableList<UUID> {
        return (moderators + members + leader).toMutableList()
    }
}
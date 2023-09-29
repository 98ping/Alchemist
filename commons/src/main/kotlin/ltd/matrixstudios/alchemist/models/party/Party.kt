package ltd.matrixstudios.alchemist.models.party

import ltd.matrixstudios.alchemist.redis.RedisPacketManager
import java.util.UUID

data class Party(
    var id: UUID,
    var leader: UUID,
    var members: MutableList<Pair<UUID, PartyElevation>>,
    var invited: MutableMap<UUID, Long>,
    var createdAt: Long,
    var alive: Boolean
) {

    fun removeMember(uuid: UUID)
    {
        val pairMember = members.firstOrNull { it.first.toString() == uuid.toString() }

        members.remove(pairMember)
    }

    fun isMember(uuid: UUID) : Boolean
    {
        return leader == uuid || members.any { it.first == uuid }
    }

    fun isLeader(uuid: UUID) : Boolean {
        return uuid.toString() == leader.toString()
    }
}
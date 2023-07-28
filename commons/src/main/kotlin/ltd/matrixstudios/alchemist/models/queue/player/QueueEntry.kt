package ltd.matrixstudios.alchemist.models.queue.player

import ltd.matrixstudios.alchemist.service.profiles.ProfileGameService
import java.util.UUID

/**
 * Class created on 7/12/2023

 * @author 98ping
 * @project Alchemist
 * @website https://solo.to/redis
 */
data class QueueEntry(
    val id: UUID,
    val joinedAt: Long
) : Comparable<Any> {

    override fun compareTo(other: Any): Int {
        var result = 0

        if (other is QueueEntry) {

            val playerPriority = ProfileGameService.getHighestRank(this.id).weight
            val otherPriority = ProfileGameService.getHighestRank(other.id).weight

            if ((if (playerPriority > otherPriority) 0 else 1.also { result = it }) == 1) {
                return if (this.joinedAt < other.joinedAt) {
                    -1
                } else 1
            }
        }

        return result
    }
}
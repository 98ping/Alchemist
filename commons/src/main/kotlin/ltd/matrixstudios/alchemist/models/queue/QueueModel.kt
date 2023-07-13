package ltd.matrixstudios.alchemist.models.queue

import ltd.matrixstudios.alchemist.models.queue.comparator.QueueComparator
import ltd.matrixstudios.alchemist.models.queue.player.QueueEntry
import ltd.matrixstudios.alchemist.service.profiles.ProfileGameService
import ltd.matrixstudios.alchemist.service.server.UniqueServerService
import java.util.*
import kotlin.collections.HashMap

/**
 * Class created on 7/12/2023

 * @author 98ping
 * @project Alchemist
 * @website https://solo.to/redis
 */
data class QueueModel(
    val id: String,
    var displayName: String,
    var sendDelay: Int,
    var status: QueueStatus,
    var limit: Int,
    var uniqueServerId: String,
    var lastPull: Long,
    var material: String,
    val playersInQueue: PriorityQueue<QueueEntry> = PriorityQueue(QueueComparator()),
) {

    fun isAvailable(uuid: UUID): Boolean {
        val server = UniqueServerService.byId(uniqueServerId) ?: return false

        if (!server.online) return false

        if (status == QueueStatus.PAUSED) {
            return ProfileGameService.getHighestRank(uuid).staff
        }

        return status !== QueueStatus.CLOSED
    }


    fun getPlayerAt(i: Int): QueueEntry? {
        return playersInQueue.firstOrNull { queuePlayer -> getPosition(queuePlayer.id) == i }
    }

    fun containsPlayer(uuid: UUID): Boolean {
        for (player in playersInQueue) {
            if (player.id != uuid) {
                continue
            }

            return true
        }

        return false
    }

    fun removePlayer(uuid: UUID) {
        if (!containsPlayer(uuid)) return

        this.playersInQueue.removeIf { it.id == uuid }
    }

    fun getPosition(uuid: UUID): Int {
        if (!containsPlayer(uuid)) {
            return 0
        }

        val queue = PriorityQueue(playersInQueue)
        var position = 0

        while (!queue.isEmpty() && queue.poll().id != uuid) {
            position++
        }

        return position + 1
    }

}
package ltd.matrixstudios.alchemist.models.queue

import ltd.matrixstudios.alchemist.models.queue.comparator.QueueComparator
import ltd.matrixstudios.alchemist.models.queue.player.QueueEntry
import java.util.*
import kotlin.collections.HashMap

/**
 * Class created on 7/12/2023

 * @author 98ping
 * @project Alchemist
 * @website https://solo.to/redis
 */
data class QueueModel(
    val players: MutableList<UUID>,
    val id: String,
    var displayName: String,
    var sendDelay: Int,
    var status: QueueStatus,
    var limit: Int,
    var lastPull: Long,
    val playersInQueue: PriorityQueue<QueueEntry> = PriorityQueue(QueueComparator()),
    val offlinePlayersInQueue: HashMap<UUID, Long> = hashMapOf()
)
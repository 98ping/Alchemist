package ltd.matrixstudios.alchemist.models.queue.comparator

import ltd.matrixstudios.alchemist.models.queue.player.QueueEntry

/**
 * Class created on 7/12/2023

 * @author 98ping
 * @project Alchemist
 * @website https://solo.to/redis
 */
class QueueComparator : Comparator<QueueEntry> {

    override fun compare(firstPlayer: QueueEntry, secondPlayer: QueueEntry): Int {
        return secondPlayer.compareTo(firstPlayer)
    }
}
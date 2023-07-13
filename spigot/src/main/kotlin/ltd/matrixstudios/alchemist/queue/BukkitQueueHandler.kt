package ltd.matrixstudios.alchemist.queue

import ltd.matrixstudios.alchemist.AlchemistSpigotPlugin
import ltd.matrixstudios.alchemist.queue.task.QueueSendTask
import ltd.matrixstudios.alchemist.service.queue.QueueService

/**
 * Class created on 7/12/2023

 * @author 98ping
 * @project Alchemist
 * @website https://solo.to/redis
 */
object BukkitQueueHandler {

    fun load() {
        for (queue in QueueService.cache.values) {
            QueueSendTask(queue).runTaskTimer(
                AlchemistSpigotPlugin.instance,
                queue.sendDelay * 20L,
                queue.sendDelay * 20L
            )
        }
    }
}
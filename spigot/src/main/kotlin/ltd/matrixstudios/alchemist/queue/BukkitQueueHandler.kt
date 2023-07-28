package ltd.matrixstudios.alchemist.queue

import ltd.matrixstudios.alchemist.AlchemistSpigotPlugin
import ltd.matrixstudios.alchemist.queue.task.QueueRemindUsersTask
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
        if (AlchemistSpigotPlugin.instance.config.getBoolean("modules.queue")) {
            QueueSendTask().runTaskTimerAsynchronously(
                AlchemistSpigotPlugin.instance,
                2 * 20L,
                2 * 20L
            )

            QueueRemindUsersTask().runTaskTimerAsynchronously(
                AlchemistSpigotPlugin.instance,
                15 * 20L,
                15 * 20L
            )
        }
    }

}
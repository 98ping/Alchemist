package ltd.matrixstudios.alchemist.queue.task

import ltd.matrixstudios.alchemist.queue.packet.QueueRemovePlayerPacket
import ltd.matrixstudios.alchemist.queue.packet.QueueSendPlayerPacket
import ltd.matrixstudios.alchemist.queue.packet.QueueUpdatePacket
import ltd.matrixstudios.alchemist.redis.AsynchronousRedisSender
import ltd.matrixstudios.alchemist.service.queue.QueueService
import org.bukkit.scheduler.BukkitRunnable

/**
 * Class created on 7/12/2023

 * @author 98ping
 * @project Alchemist
 * @website https://solo.to/redis
 */
class QueueSendTask : BukkitRunnable()
{

    override fun run()
    {
        for (queue in QueueService.cache.values)
        {
            if (queue.playersInQueue.isEmpty() || queue.getPlayerAt(1) == null)
            {
                continue
            }

            val firstPlayer = queue.getPlayerAt(1)!!
            val uuid = firstPlayer.id

            if (!queue.isAvailable(uuid)) continue

            queue.lastPull = System.currentTimeMillis()

            AsynchronousRedisSender.send(QueueSendPlayerPacket(uuid, queue.id))
            AsynchronousRedisSender.send(QueueRemovePlayerPacket(queue.id, uuid))
            AsynchronousRedisSender.send(QueueUpdatePacket())
        }
    }
}
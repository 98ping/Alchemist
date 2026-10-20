package ltd.matrixstudios.alchemist.queue.task

import ltd.matrixstudios.alchemist.queue.QueueLeader
import ltd.matrixstudios.alchemist.queue.packet.QueueRemovePlayerPacket
import ltd.matrixstudios.alchemist.queue.packet.QueueSendPlayerPacket
import ltd.matrixstudios.alchemist.queue.packet.QueueUpdatePacket
import ltd.matrixstudios.alchemist.redis.AsynchronousRedisSender
import ltd.matrixstudios.alchemist.service.queue.QueueService
import org.bukkit.scheduler.BukkitRunnable

class QueueSendTask : BukkitRunnable()
{

    override fun run()
    {
        if (!QueueLeader.claim()) return

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

package ltd.matrixstudios.alchemist.queue.packet

import ltd.matrixstudios.alchemist.redis.RedisPacket
import ltd.matrixstudios.alchemist.service.queue.QueueService

/**
 * Class created on 7/12/2023

 * @author 98ping
 * @project Alchemist
 * @website https://solo.to/redis
 */
class QueueUpdatePacket : RedisPacket("queue-update-packet")
{

    override fun action()
    {
        QueueService.loadAllQueues()
    }
}
package ltd.matrixstudios.alchemist.service.queue

import com.google.gson.reflect.TypeToken
import ltd.matrixstudios.alchemist.Alchemist
import ltd.matrixstudios.alchemist.models.queue.QueueModel
import ltd.matrixstudios.alchemist.redis.RedisPacketManager
import ltd.matrixstudios.alchemist.service.GeneralizedService
import java.lang.reflect.Type
import java.util.*

/**
 * Class created on 7/12/2023

 * @author 98ping
 * @project Alchemist
 * @website https://solo.to/redis
 */
object QueueService : GeneralizedService {

    var cache = hashMapOf<String, QueueModel>()
    val type: Type = object : TypeToken<List<QueueModel>>() {}.type

    fun loadAllQueues() {
        RedisPacketManager.pool.use {
            val resource = it.resource

            val list = resource.get("Alchemist:Queues")
            val serialize = Alchemist.gson.fromJson<List<QueueModel>>(list, type)

            for (entry in serialize) {
                cache[entry.id] = entry
            }
        }
    }

    fun saveQueue(model: QueueModel) {

    }
}
package ltd.matrixstudios.alchemist.service.queue

import com.google.gson.reflect.TypeToken
import io.github.nosequel.data.DataStoreType
import ltd.matrixstudios.alchemist.Alchemist
import ltd.matrixstudios.alchemist.models.queue.QueueModel
import ltd.matrixstudios.alchemist.redis.RedisPacketManager
import ltd.matrixstudios.alchemist.service.GeneralizedService
import java.lang.reflect.Type
import java.util.*
import java.util.concurrent.CompletableFuture
import java.util.concurrent.ConcurrentHashMap

/**
 * Class created on 7/12/2023

 * @author 98ping
 * @project Alchemist
 * @website https://solo.to/redis
 */
object QueueService : GeneralizedService {

    var cache = ConcurrentHashMap<String, QueueModel>()
    val handler = Alchemist.dataHandler.createStoreType<String, QueueModel>(DataStoreType.MONGO)

    fun loadAllQueues() {
        cache.clear()
        handler.retrieveAllAsync().thenAccept {
            for (queue in it) {
                cache[queue.id] = queue
            }
        }
    }

    fun playerAlreadyQueued(uuid: UUID): QueueModel? {
        return cache.values.firstOrNull { it.containsPlayer(uuid) }
    }

    fun byId(id: String): CompletableFuture<QueueModel?> {
        if (cache.containsKey(id.toLowerCase())) return CompletableFuture.completedFuture(cache[id.toLowerCase()])

        return handler.retrieveAsync(id.toLowerCase())
    }

    fun saveQueue(model: QueueModel) {
        cache[model.id] = model
        handler.storeAsync(model.id, model)
    }
}
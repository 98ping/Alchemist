package ltd.matrixstudios.alchemist.redis.data

import ltd.matrixstudios.alchemist.redis.RedisPacket
import ltd.matrixstudios.alchemist.redis.RedisPacketManager
import ltd.matrixstudios.alchemist.redis.data.packet.RedisModelPopulationPacket
import java.lang.reflect.Type
import java.util.concurrent.CompletableFuture
import java.util.concurrent.ForkJoinPool

// thank u growlyx for the idea hes a great guy really
abstract class RedisDataSync<V>(private val identifier: String, val clazz: Class<V>)
{
    abstract fun destination(): String

    abstract fun key(): String

    fun cache(value: V)
    {
        RedisDataSyncService.syncModel(identifier, value)

        CompletableFuture.runAsync {
            RedisPacketManager.pool.resource.use { jedis ->
                jedis.hset(
                    destination(),
                    key(),
                    RedisPacketManager.gson.toJson(value, clazz)
                )
            }
        }.whenComplete { _, _ ->
            sync()
        }
    }

    fun load()
    {
        var model: V?

        RedisPacketManager.pool.resource.use { jedis ->
            val json = jedis.hget(destination(), key())
            model = RedisPacketManager.gson.fromJson(json, clazz)
        }

        if (model != null)
        {
            RedisDataSyncService.syncModel(identifier, model)
            println("[data-sync] loaded redis data for sync service $identifier")
        }
    }

    fun sync()
    {
        val packet =
            RedisModelPopulationPacket(identifier, RedisPacketManager.gson.toJson(RedisDataSyncService.dataSyncModels[identifier]!!.value), clazz)

        ForkJoinPool.commonPool().execute {
            RedisPacketManager.pool.resource.use { jedis ->
                val encodedPacket = packet.javaClass.name + "|" + RedisPacketManager.gson.toJson(packet)
                jedis.publish("Alchemist||Packets||", encodedPacket)
            }
        }
    }

    fun cached(): V?
    {
        return if (RedisDataSyncService.dataSyncModels[identifier] == null)
        {
            null
        } else
        {
            RedisDataSyncService.dataSyncModels[identifier]!!.value!! as V
        }

    }
}
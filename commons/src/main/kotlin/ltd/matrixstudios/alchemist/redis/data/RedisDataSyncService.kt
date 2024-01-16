package ltd.matrixstudios.alchemist.redis.data

object RedisDataSyncService
{
    val dataSyncModels = mutableMapOf<String, RedisObjectWrapper<*>>()
    val syncServices = mutableMapOf<String, RedisDataSync<*>>()

    fun <V> syncModel(id: String, newModel: V)
    {
        dataSyncModels[id] = RedisObjectWrapper(newModel)
    }
}
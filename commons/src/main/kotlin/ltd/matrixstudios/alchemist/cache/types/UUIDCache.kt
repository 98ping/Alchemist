package ltd.matrixstudios.alchemist.cache.types

import ltd.matrixstudios.alchemist.cache.RedisCache
import java.util.UUID
import java.util.concurrent.CompletableFuture

object UUIDCache : RedisCache<UUID, String>("Alchemist:Caches:UUID")
{
    fun findById(id: UUID) : CompletableFuture<String?>
    {
        return CompletableFuture.supplyAsync {
            return@supplyAsync this.aToBCache[id]
        }
    }

    fun findByUsername(name: String) : CompletableFuture<UUID?>
    {
        return CompletableFuture.supplyAsync {
            return@supplyAsync this.btoACache[name]
        }
    }
}
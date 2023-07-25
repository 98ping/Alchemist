package ltd.matrixstudios.alchemist.service.tags

import io.github.nosequel.data.DataStoreType
import ltd.matrixstudios.alchemist.Alchemist
import ltd.matrixstudios.alchemist.models.tags.Tag
import ltd.matrixstudios.alchemist.service.GeneralizedService
import java.util.concurrent.CompletableFuture
import java.util.concurrent.ConcurrentHashMap

object TagService : GeneralizedService {

    var handler = Alchemist.dataHandler.createStoreType<String, Tag>(DataStoreType.MONGO)
    var cache = ConcurrentHashMap<String, Tag>()

    fun loadTags() {
        getValues().thenAccept {
            for (tag in it) {
                cache[tag.id] = tag
            }
        }
    }

    fun getValues() : CompletableFuture<Collection<Tag>> {
        return handler.retrieveAllAsync()
    }

    fun save(tag: Tag) {
        cache[tag.id] = tag
        handler.storeAsync(tag.id, tag)
    }


    fun byId(id: String) : Tag? {
        if (cache.containsKey(id)) {
            return cache[id]
        }

        val future = getValues().thenApply {
            for (tag in it) {
                if (tag.id.equals(id, ignoreCase = true))
                {
                    return@thenApply tag
                }
            }

            return@thenApply null
        }

        return future.get()
    }
}

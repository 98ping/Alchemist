package ltd.matrixstudios.alchemist.service.tags

import io.github.nosequel.data.DataStoreType
import ltd.matrixstudios.alchemist.Alchemist
import ltd.matrixstudios.alchemist.models.ranks.Rank
import ltd.matrixstudios.alchemist.models.tags.Tag
import java.util.concurrent.CompletableFuture
import java.util.stream.Collectors

object TagService {

    var handler = Alchemist.dataHandler.createStoreType<String, Tag>(DataStoreType.MONGO)

    fun getValues() : CompletableFuture<Collection<Tag>> {
        return handler.retrieveAllAsync()
    }

    fun save(tag: Tag) {
        handler.storeAsync(tag.id, tag)
    }


    fun byId(id: String) : Tag? {
        return getValues().get().firstOrNull { it.id.equals(id, ignoreCase = true) }
    }
}

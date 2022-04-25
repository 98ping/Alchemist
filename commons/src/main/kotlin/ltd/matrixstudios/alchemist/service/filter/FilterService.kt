package ltd.matrixstudios.alchemist.service.filter

import io.github.nosequel.data.DataStoreType
import ltd.matrixstudios.alchemist.Alchemist
import ltd.matrixstudios.alchemist.models.filter.Filter
import ltd.matrixstudios.alchemist.models.profile.GameProfile
import java.util.*
import java.util.concurrent.CompletableFuture

object FilterService {


    var handler = Alchemist.dataHandler.createStoreType<UUID, Filter>(DataStoreType.MONGO)


    fun getValues(): Collection<Filter> {
        return handler.retrieveAllAsync().get()
    }

    fun save(filter: Filter) {
        handler.storeAsync(filter.id, filter)
    }

    fun byWord(word: String): Filter? {
        return getValues().firstOrNull { it.word.equals(word, ignoreCase = true) }
    }

}
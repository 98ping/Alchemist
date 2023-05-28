package ltd.matrixstudios.alchemist.service.filter

import io.github.nosequel.data.DataStoreType
import ltd.matrixstudios.alchemist.Alchemist
import ltd.matrixstudios.alchemist.models.filter.Filter
import ltd.matrixstudios.alchemist.service.GeneralizedService
import org.bson.Document
import java.util.*

object FilterService : GeneralizedService {


    var handler = Alchemist.dataHandler.createStoreType<UUID, Filter>(DataStoreType.MONGO)
    val collection = Alchemist.MongoConnectionPool.getCollection("filter")

    val cache = mutableMapOf<String, Filter>()

    fun loadIntoCache()
    {
        for (value in getValues())
        {
            cache[value.word] = value
        }
    }

    fun getValues(): Collection<Filter> {
        return handler.retrieveAll()
    }

    fun save(filter: Filter) {
        handler.storeAsync(filter.id, filter)

        cache[filter.word] = filter
    }

    fun byWord(word: String): Filter? {
        val filter = Document("word", word)
        val finder = collection.find(filter).first()

        return Alchemist.gson.fromJson(finder.toJson(), Filter::class.java)
    }

    fun findInMessage(message: String) : Filter?
    {
        for (filter in cache.values)
        {
            if (message.toLowerCase().contains(filter.word.toLowerCase()))
            {
                return filter
            }
        }

        return null
    }

}
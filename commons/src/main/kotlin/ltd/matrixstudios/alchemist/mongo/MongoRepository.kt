package ltd.matrixstudios.alchemist.mongo

import com.mongodb.client.MongoCollection
import com.mongodb.client.result.UpdateResult
import org.litote.kmongo.*
import kotlin.reflect.KClass

class MongoRepository<K : Any, V : Any>(val type: KClass<V>, val collection: MongoCollection<V>) {

    fun saveEntry(
        key: K,
        value: V
    ) : UpdateResult {
        return collection.updateOneById(key, value)
    }

    fun lookupAll(): MutableList<V> {
        val iterator = collection.find().iterator()
        val res = mutableListOf<V>()

        while (iterator.hasNext()) {
            res.add(iterator.next())
        }

        return res
    }

    fun search(
        key: K
    ) : V? {
        return collection.findOne("{_id:'${key}'")
    }

}
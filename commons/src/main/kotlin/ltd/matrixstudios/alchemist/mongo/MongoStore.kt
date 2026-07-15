package ltd.matrixstudios.alchemist.mongo

import com.mongodb.client.MongoCollection
import com.mongodb.client.model.Filters
import com.mongodb.client.model.UpdateOptions
import ltd.matrixstudios.alchemist.Alchemist
import org.bson.Document
import java.util.concurrent.CompletableFuture

class MongoStore<K, V>(collectionName: String, private val serialClass: Class<V>)
{
    private val collection: MongoCollection<Document> = MongoManager.getCollection(collectionName)

    fun store(key: K, value: V)
    {
        collection.updateOne(
            Filters.eq("_id", key.toString()),
            Document("\$set", Document.parse(Alchemist.gson.toJson(value))),
            UpdateOptions().upsert(true)
        )
    }

    fun storeAsync(key: K, value: V): CompletableFuture<Void> = CompletableFuture.runAsync { store(key, value) }

    fun retrieve(key: K): V?
    {
        val document = collection.find(Filters.eq("_id", key.toString())).first() ?: return null
        return Alchemist.gson.fromJson(document.toJson(), serialClass)
    }

    fun retrieveAsync(key: K): CompletableFuture<V?> = CompletableFuture.supplyAsync { retrieve(key) }

    fun retrieveAll(): Collection<V>
    {
        val items = mutableListOf<V>()

        for (document in collection.find())
        {
            items.add(Alchemist.gson.fromJson(document.toJson(), serialClass))
        }

        return items
    }

    fun retrieveAllAsync(): CompletableFuture<Collection<V>> = CompletableFuture.supplyAsync { retrieveAll() }

    fun delete(key: K)
    {
        collection.deleteMany(Filters.eq("_id", key.toString()))
    }

    fun deleteAsync(key: K): CompletableFuture<Void> = CompletableFuture.runAsync { delete(key) }
}

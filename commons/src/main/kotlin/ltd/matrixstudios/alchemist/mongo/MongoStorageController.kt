package ltd.matrixstudios.alchemist.mongo

import com.mongodb.client.model.Filters
import com.mongodb.client.model.UpdateOptions
import ltd.matrixstudios.alchemist.Alchemist
import ltd.matrixstudios.alchemist.mongo.extensions.deserialize
import ltd.matrixstudios.alchemist.mongo.extensions.eq
import org.bson.Document
import org.bson.conversions.Bson
import java.util.concurrent.CompletableFuture
import kotlin.reflect.KProperty

/**
 * Class created on 9/27/2023

 * @author 98ping
 * @project Alchemist
 * @website https://solo.to/redis
 */
class MongoStorageController<K, V>(
    collectionName: String,
    private val serialClass: Class<V>
) {
    private val collection = Alchemist.MongoConnectionPool.getCollection(collectionName)

    fun retrieve(
        key: K
    ): CompletableFuture<V?>
    {
        return CompletableFuture.supplyAsync {
            val cursor = collection
                .find(Filters.eq("_id", key.toString()))
                .cursor()

            while (cursor.hasNext())
            {
                val obj = cursor.next() deserialize serialClass

                if (obj != null)
                {
                    return@supplyAsync obj
                }
            }

            null
        }
    }

    fun aggregate(
        constraints: List<Bson>
    ): CompletableFuture<MutableList<V>>
    {
        return CompletableFuture.supplyAsync {
            val items = mutableListOf<V>()

            val cursor = collection
                .aggregate(constraints)
                .cursor()

            while (cursor.hasNext())
            {
                val obj = cursor.next() deserialize serialClass

                if (obj != null)
                {
                    items.add(obj)
                }
            }

            items
        }
    }

    fun getAll(): CompletableFuture<MutableList<V>>
    {
        return CompletableFuture.supplyAsync {
            val items = mutableListOf<V>()

            val cursor = collection
                .find()
                .cursor()

            while (cursor.hasNext())
            {
                val obj = cursor.next() deserialize serialClass

                if (obj != null)
                {
                    items.add(obj)
                }
            }

            items
        }
    }

    fun insertAsynchronously(
        key: K,
        value: V
    ): CompletableFuture<V>
    {
        return CompletableFuture.supplyAsync {
            collection.updateOne(
                Filters.eq("_id", key.toString()),
                Document(
                    "\$set",
                    Document.parse(
                        Alchemist.gson.toJson(value)
                    )
                ),
                UpdateOptions().upsert(true)
            )

            value
        }
    }

    fun insertSynchronously(
        key: K,
        value: V
    ) {
        collection.updateOne(
            Filters.eq("_id", key.toString()),
            Document(
                "\$set",
                Document.parse(
                    Alchemist.gson.toJson(value)
                )
            ),
            UpdateOptions().upsert(true)
        )
    }


    fun <T> filter(
        property: KProperty<T>,
        value: String
    ): CompletableFuture<MutableList<V>>
    {
        return CompletableFuture.supplyAsync {
            val items = mutableListOf<V>()

            val cursor = collection
                .find(property eq value)
                .cursor()

            while (cursor.hasNext())
            {
                val obj = Alchemist.gson.fromJson(
                    cursor.next().toJson(),
                    serialClass
                )

                if (obj != null)
                {
                    items.add(obj)
                }
            }

            items
        }
    }
}

package ltd.matrixstudios.alchemist.mongo

import com.mongodb.client.MongoClient
import com.mongodb.client.MongoDatabase
import org.litote.kmongo.*

object MongoManager {

    lateinit var kMongoClient: MongoClient
    lateinit var kMongoDatabase: MongoDatabase


    fun load(database: String)
    {
        this.kMongoClient = KMongo.createClient()
        this.kMongoDatabase = kMongoClient.getDatabase(database)
    }

    inline fun <reified K : Any, reified V : Any> createRepository() : MongoRepository<K, V> = MongoRepository(V::class, kMongoDatabase.getCollection<V>(V::class.simpleName ?: "Unknown"))


}
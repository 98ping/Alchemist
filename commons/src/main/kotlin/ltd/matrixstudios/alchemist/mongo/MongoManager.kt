package ltd.matrixstudios.alchemist.mongo

import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import org.bson.Document

object MongoManager
{
    lateinit var client: MongoClient
    lateinit var database: MongoDatabase

    fun connect(uri: String, databaseName: String)
    {
        client = MongoClients.create(uri)
        database = client.getDatabase(databaseName)
    }

    fun getCollection(name: String): MongoCollection<Document>
    {
        return database.getCollection(name)
    }
}

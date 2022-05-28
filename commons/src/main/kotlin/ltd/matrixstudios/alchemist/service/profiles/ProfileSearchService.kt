package ltd.matrixstudios.alchemist.service.profiles

import com.mongodb.client.model.Filters
import com.mongodb.client.model.Indexes
import ltd.matrixstudios.alchemist.Alchemist
import ltd.matrixstudios.alchemist.models.profile.GameProfile
import java.util.*
import java.util.concurrent.CompletableFuture

object ProfileSearchService {

    val owningCollection = Alchemist.MongoConnectionPool.getCollection("gameprofile")

    fun <T> searchAsync(key: String, value: T) : CompletableFuture<GameProfile?> {
        return CompletableFuture.supplyAsync {
            val rawDoc = owningCollection.find(Filters.eq(key, value)).firstOrNull() ?: return@supplyAsync null

            return@supplyAsync Alchemist.gson.fromJson(rawDoc.toJson()!!, GameProfile::class.java)
        }
    }

     fun getByUsername(username: String) : CompletableFuture<GameProfile?> {
        return CompletableFuture.supplyAsync {
            val profileInMongo = owningCollection.find(Filters.eq("username", username)).firstOrNull()

            return@supplyAsync Alchemist.gson.fromJson(profileInMongo!!.toJson(), GameProfile::class.java)
        }
    }

    fun exists(uuid: UUID) : CompletableFuture<Boolean> {
        return CompletableFuture.supplyAsync {
            val profileInMongo = owningCollection.find(Filters.eq("uuid", uuid.toString())).firstOrNull()

            return@supplyAsync profileInMongo != null
        }
    }

    fun getAsync(uuid: UUID) :  CompletableFuture<GameProfile?> {
        return CompletableFuture.supplyAsync {

            val inhashmap = ProfileGameService.cache.getOrDefault(uuid,
                Alchemist.gson.fromJson(owningCollection.find(
                    Filters.eq("uuid",
                        uuid.toString())
                ).first()!!.toJson(),
                    GameProfile::class.java)
            )

            return@supplyAsync inhashmap

        }
    }


}
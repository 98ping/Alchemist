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
            val rawDoc = owningCollection.find(Filters.eq(key, value)).firstOrNull()!!.toJson() ?: return@supplyAsync null

            return@supplyAsync Alchemist.gson.fromJson(rawDoc, GameProfile::class.java)
        }
    }

    fun getAsync(uuid: UUID) : CompletableFuture<GameProfile?> {
        return CompletableFuture.supplyAsync {

            val inhashmap = ProfileGameService.cache.getOrDefault(uuid, null)

            //end supplier here if it found it
            if (inhashmap != null) {
                return@supplyAsync inhashmap
            }


            //scan if it didnt
            val document = owningCollection.find(Filters.eq("uuid", uuid.toString())).first()


            return@supplyAsync Alchemist.gson.fromJson(document.toJson(), GameProfile::class.java)
        }
    }


}
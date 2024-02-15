package ltd.matrixstudios.discord.sync

import com.mongodb.client.model.Filters
import ltd.matrixstudios.alchemist.Alchemist
import ltd.matrixstudios.alchemist.models.profile.GameProfile
import ltd.matrixstudios.alchemist.service.profiles.ProfileGameService
import java.util.concurrent.CompletableFuture

/**
 * Class created on 2/14/2024

 * @author 98ping
 * @project Alchemist
 * @website https://solo.to/redis
 */
object SyncService
{
    private val collection = ProfileGameService.collection
    fun getSyncCodeForUser(username: String): CompletableFuture<String?>
    {
        return CompletableFuture.supplyAsync {
            val user = collection.find(Filters.eq("lowercasedUsername", username.lowercase())).firstOrNull()
                ?: return@supplyAsync null

            return@supplyAsync Alchemist.gson.fromJson(user.toJson(), GameProfile::class.java).syncCode
        }
    }
}
package ltd.matrixstudios.alchemist.profiles

import co.aikar.commands.ConditionFailedException
import ltd.matrixstudios.alchemist.models.profile.GameProfile
import ltd.matrixstudios.alchemist.service.profiles.ProfileGameService
import java.util.concurrent.CompletableFuture

/**
 * Class created on 7/26/2023

 * @author 98ping
 * @project Alchemist
 * @website https://solo.to/redis
 */
data class AsyncGameProfile(
    val future: CompletableFuture<List<GameProfile>>
) {

    fun use(
        action: (GameProfile) -> Unit
    ) : CompletableFuture<Void> {
        return future.thenAccept {
            if (it.isEmpty()) {
                throw ConditionFailedException("This profile was unable to be found!")
            } else {
                action.invoke(it.first())
            }
        }
    }

    companion object {
        fun name(
            name: String
        ) : AsyncGameProfile {
            return AsyncGameProfile(
                ProfileGameService.byUsernameWithList(name)
            )
        }
    }
}
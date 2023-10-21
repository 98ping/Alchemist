package ltd.matrixstudios.alchemist.profiles.connection.postlog.tasks

import ltd.matrixstudios.alchemist.profiles.BukkitProfileAdaptation
import ltd.matrixstudios.alchemist.profiles.connection.postlog.BukkitPostLoginTask
import ltd.matrixstudios.alchemist.profiles.getProfile
import ltd.matrixstudios.alchemist.service.profiles.ProfileGameService
import org.bukkit.entity.Player
import java.util.concurrent.CompletableFuture

/**
 * Class created on 9/14/2023

 * @author 98ping
 * @project Alchemist
 * @website https://solo.to/redis
 */
object EnsureTOTP : BukkitPostLoginTask
{
    override fun run(player: Player)
    {
        CompletableFuture.runAsync {
            val profile = player.getProfile() ?: return@runAsync

            if (BukkitProfileAdaptation.playerNeedsAuthenticating(profile, player))
            {
                profile.metadata.addProperty("needsAuthetication", "true")
                ProfileGameService.saveSync(profile)
            }
        }
    }
}
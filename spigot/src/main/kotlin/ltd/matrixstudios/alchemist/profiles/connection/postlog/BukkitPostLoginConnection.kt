package ltd.matrixstudios.alchemist.profiles.connection.postlog

import ltd.matrixstudios.alchemist.models.connection.ConnectionMethod
import ltd.matrixstudios.alchemist.profiles.connection.postlog.tasks.*
import org.bukkit.entity.Player

/**
 * Class created on 5/27/2023

 * @author 98ping
 * @project Alchemist
 * @website https://solo.to/redis
 */
object BukkitPostLoginConnection : ConnectionMethod<Player>()
{

    fun getAllTasks(): List<BukkitPostLoginTask>
    {
        return listOf(
            LoadPermissions,
            SendStaffWelcome,
            CheckBanEvasion,
            SendLoadedProfileMessage,
            EnsureTOTP
        )
    }
}
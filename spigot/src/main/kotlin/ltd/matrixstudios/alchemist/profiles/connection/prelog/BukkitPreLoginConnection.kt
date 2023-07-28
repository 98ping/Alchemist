package ltd.matrixstudios.alchemist.profiles.connection.prelog

import ltd.matrixstudios.alchemist.models.connection.ConnectionMethod
import ltd.matrixstudios.alchemist.profiles.connection.prelog.tasks.CalculateGrantables
import ltd.matrixstudios.alchemist.profiles.connection.prelog.tasks.HandlePunishments
import ltd.matrixstudios.alchemist.profiles.connection.prelog.tasks.LoadProfile
import org.bukkit.entity.Player
import org.bukkit.event.player.AsyncPlayerPreLoginEvent

/**
 * Class created on 5/27/2023

 * @author 98ping
 * @project Alchemist
 * @website https://solo.to/redis
 */
object BukkitPreLoginConnection : ConnectionMethod<AsyncPlayerPreLoginEvent>() {

    fun getAllTasks() : List<BukkitPreLoginTask> {
        return listOf(
            LoadProfile,
            HandlePunishments,
            CalculateGrantables
        )
    }
}
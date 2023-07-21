package ltd.matrixstudios.alchemist.profiles.prelog

import ltd.matrixstudios.alchemist.models.connection.ConnectionMethod
import ltd.matrixstudios.alchemist.profiles.postlog.BukkitPostLoginTask
import ltd.matrixstudios.alchemist.profiles.postlog.tasks.CheckBanEvasion
import ltd.matrixstudios.alchemist.profiles.postlog.tasks.LoadPermissions
import ltd.matrixstudios.alchemist.profiles.postlog.tasks.SendStaffWelcome
import ltd.matrixstudios.alchemist.profiles.prelog.tasks.CalculateGrantables
import ltd.matrixstudios.alchemist.profiles.prelog.tasks.HandlePunishments
import ltd.matrixstudios.alchemist.profiles.prelog.tasks.LoadProfile
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
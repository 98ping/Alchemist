package ltd.matrixstudios.alchemist.profiles.connection.postlog

import org.bukkit.entity.Player

/**
 * Class created on 7/20/2023

 * @author 98ping
 * @project Alchemist
 * @website https://solo.to/redis
 */
interface BukkitPostLoginTask {

    fun run(player: Player)
}
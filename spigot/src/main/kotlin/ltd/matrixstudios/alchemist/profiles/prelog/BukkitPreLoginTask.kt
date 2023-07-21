package ltd.matrixstudios.alchemist.profiles.prelog

import org.bukkit.entity.Player
import org.bukkit.event.player.AsyncPlayerPreLoginEvent

/**
 * Class created on 7/20/2023

 * @author 98ping
 * @project Alchemist
 * @website https://solo.to/redis
 */
interface BukkitPreLoginTask {

    fun run(event: AsyncPlayerPreLoginEvent)

    fun shouldBeLazy() : Boolean
}
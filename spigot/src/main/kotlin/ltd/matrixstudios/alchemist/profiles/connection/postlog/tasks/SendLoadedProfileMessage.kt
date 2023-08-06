package ltd.matrixstudios.alchemist.profiles.connection.postlog.tasks

import ltd.matrixstudios.alchemist.AlchemistSpigotPlugin
import ltd.matrixstudios.alchemist.api.AlchemistAPI
import ltd.matrixstudios.alchemist.profiles.connection.postlog.BukkitPostLoginTask
import ltd.matrixstudios.alchemist.util.Chat
import org.bukkit.Bukkit
import org.bukkit.entity.Player

/**
 * Class created on 7/20/2023

 * @author 98ping
 * @project Alchemist
 * @website https://solo.to/redis
 */
object SendLoadedProfileMessage : BukkitPostLoginTask {

    override fun run(player: Player) {
        Bukkit.getScheduler().runTaskLater(AlchemistSpigotPlugin.instance, {
            val config = AlchemistSpigotPlugin.instance.config

            if (config.getBoolean("profiles.load.sendMessage"))
            {
                val msg = config.getString("profiles.load.message")
                player.sendMessage(Chat.format(msg))

            }
        }, 10L)
    }
}
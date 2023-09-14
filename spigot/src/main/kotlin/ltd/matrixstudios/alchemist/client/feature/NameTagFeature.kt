package ltd.matrixstudios.alchemist.client.feature

import com.lunarclient.bukkitapi.LunarClientAPI
import com.lunarclient.bukkitapi.nethandler.client.LCPacketNametagsOverride
import ltd.matrixstudios.alchemist.Alchemist
import ltd.matrixstudios.alchemist.AlchemistSpigotPlugin
import ltd.matrixstudios.alchemist.profiles.getCurrentRank
import ltd.matrixstudios.alchemist.staff.mode.StaffSuiteManager
import ltd.matrixstudios.alchemist.util.Chat
import org.bukkit.Bukkit
import org.bukkit.entity.Entity
import org.bukkit.entity.Player
import java.util.*
import java.util.concurrent.CompletableFuture


/**
 * Class created on 9/13/2023

 * @author 98ping
 * @project Alchemist
 * @website https://solo.to/redis
 */
object NameTagFeature {
    fun sendNameTag(target: Player, viewer: Player) {
        LunarClientAPI.getInstance().overrideNametag(
            target,
            mutableListOf(
                Chat.format(target.displayName),
                Chat.format("&7[Mod Mode]")
            ),
            viewer
        )
    }

    fun startNametagUpdateTask() {
        Bukkit.getScheduler().runTaskTimer(
            AlchemistSpigotPlugin.instance, {
                for (player in Bukkit.getOnlinePlayers())
                {
                    if (StaffSuiteManager.isModMode(player))
                    {
                        for (other in Bukkit.getOnlinePlayers())
                        {
                            if (StaffSuiteManager.isModMode(other))
                            {
                                sendNameTag(player, other)
                            }
                        }

                        sendNameTag(player, player)
                        TeamViewFeature.sendStaffTeamView(player)
                    }
                }
            }, 0L, 20L
        )
    }

    fun removeNameTag(player: Player) {
        Bukkit.getServer().onlinePlayers.forEach { staff ->
            LunarClientAPI.getInstance().resetNametag(player, staff)
        }
    }
}
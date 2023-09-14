package ltd.matrixstudios.alchemist.client.feature

import com.lunarclient.bukkitapi.LunarClientAPI
import com.lunarclient.bukkitapi.nethandler.client.LCPacketTeammates
import ltd.matrixstudios.alchemist.staff.mode.StaffSuiteManager
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerMoveEvent
import org.bukkit.event.player.PlayerQuitEvent
import java.util.*

/**
 * Class created on 9/13/2023

 * @author 98ping
 * @project Alchemist
 * @website https://solo.to/redis
 */
object TeamViewFeature {
    fun sendStaffTeamView(player: Player) {
        //thanks @dash for code :D
        LunarClientAPI.getInstance().sendTeammates(player, LCPacketTeammates(
            player.uniqueId,
            System.currentTimeMillis(),
            mutableMapOf<UUID, MutableMap<String, Double>>().also {
                Bukkit.getServer().onlinePlayers.filter(StaffSuiteManager::isModMode).forEach { staff ->
                    val map = mutableMapOf<String, Double>()

                    map["x"] = staff.location.x
                    map["y"] = staff.location.y
                    map["z"] = staff.location.z
                    it[staff.uniqueId] = map
                }
            }
        ))
    }

    fun clearTeamView(player: Player) {
        LunarClientAPI.getInstance().sendTeammates(
            player, LCPacketTeammates(player.uniqueId, System.currentTimeMillis(), emptyMap())
        )
    }
}
package ltd.matrixstudios.alchemist.staff.mode

import org.bukkit.Bukkit
import org.bukkit.entity.Player

object StaffSuiteVisibilityHandler {

    fun onDisableVisbility(player: Player) {
        Bukkit.getOnlinePlayers().forEach {
            it.showPlayer(player)
        }

    }

    fun onEnableVisibility(player: Player) {
        Bukkit.getOnlinePlayers().filter { !it.hasPermission("alchemist.staff") }.forEach { it.hidePlayer(player) }

        Bukkit.getOnlinePlayers().filter {
            it.hasPermission("alchemistw.staff")
        }.forEach {
            player.showPlayer(it)
        }
    }
}
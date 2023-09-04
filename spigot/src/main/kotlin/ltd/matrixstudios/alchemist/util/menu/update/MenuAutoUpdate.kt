package ltd.matrixstudios.alchemist.util.menu.update

import ltd.matrixstudios.alchemist.util.menu.MenuController
import org.bukkit.Bukkit
import org.bukkit.scheduler.BukkitRunnable

class MenuAutoUpdate : BukkitRunnable()
{
    override fun run() {
        for (paginated in MenuController.paginatedMenuMap.entries)
        {
            val player = Bukkit.getPlayer(paginated.key)

            if (player == null || !player.isOnline) return

            if (player.openInventory.topInventory == null) return

            val menu = paginated.value
            menu.handleAutoUpdate(player)
        }
    }
}
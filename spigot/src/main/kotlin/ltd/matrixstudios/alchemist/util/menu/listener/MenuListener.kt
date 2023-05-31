package ltd.matrixstudios.alchemist.util.menu.listener

import ltd.matrixstudios.alchemist.util.menu.MenuController
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import java.util.*

class MenuListener : Listener {

    val timestamps = mutableMapOf<UUID, Long>()

    @EventHandler
    fun onPaginatedMenuClick(event: InventoryClickEvent) {
        val menu = MenuController.paginatedMenuMap.get(event.whoClicked.uniqueId)
        val player = event.whoClicked
        if (menu != null) {
            val slot = event.slot
            val click = event.click

            if (timestamps.containsKey(player.uniqueId)) {
                val time = timestamps[player.uniqueId]

                if (System.currentTimeMillis().minus(time!!) < 300) {
                    event.isCancelled = true
                    timestamps.remove(player.uniqueId)

                    return
                }
            }

            timestamps[player.uniqueId] = System.currentTimeMillis()

            event.isCancelled = true
            if (click != ClickType.SHIFT_RIGHT && click != ClickType.SHIFT_LEFT)
            {
                if (menu.getHeaderItems(event.whoClicked as Player)[slot] != null)
                {
                    menu.getHeaderItems(event.whoClicked as Player)[slot]!!.onClick(event.whoClicked as Player, slot, click)
                }

                if (menu.getButtonsInRange().get()[slot] != null)
                {
                    menu.getButtonsInRange().get()[slot]!!.onClick(event.whoClicked as Player, slot, click)
                }

                if (menu.getPageNavigationButtons()[slot] != null)
                {
                    menu.getPageNavigationButtons()[slot]!!.onClick(event.whoClicked as Player, slot, click)
                }
            }
        }
    }

    @EventHandler
    fun closeMenuPaginated(event: InventoryCloseEvent) {
        val menu = MenuController.paginatedMenuMap[event.player.uniqueId]

        if (menu != null) {
            MenuController.paginatedMenuMap.remove(event.player.uniqueId)
        }
    }


    @EventHandler
    fun closeMenu(event: InventoryCloseEvent) {
        val menu = MenuController.menuMap[event.player.uniqueId]

        if (menu != null) {
            MenuController.menuMap.remove(event.player.uniqueId)
        }
    }

    @EventHandler
    fun onMenuClick(event: InventoryClickEvent) {
        val menu = MenuController.menuMap[event.whoClicked.uniqueId]
        val player = event.whoClicked

        if (menu != null) {
            val slot = event.slot
            val click = event.click

            if (timestamps.containsKey(player.uniqueId)) {
                val time = timestamps[player.uniqueId]

                if (System.currentTimeMillis().minus(time!!) < 300) {
                    event.isCancelled = true
                    timestamps.remove(player.uniqueId)

                    return
                }
            }

            timestamps[player.uniqueId] = System.currentTimeMillis()

            if (!menu.stealable) {
                event.isCancelled = true
            }

            if (click != ClickType.SHIFT_RIGHT && click != ClickType.SHIFT_LEFT)
            {
               if (menu.getAllButtons()[slot] != null)
               {
                   menu.getAllButtons()[slot]!!.onClick(event.whoClicked as Player, slot, click)
               }
            }
        }
    }
}
package me.ninetyeightping.pinglib.menus.pagination

import me.ninetyeightping.pinglib.menus.Button
import me.ninetyeightping.pinglib.menus.MenuController
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.entity.HumanEntity
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import java.util.*

abstract class PaginatedMenu(
    private val size: Int,
    private val player: Player
) {

    var page = 1;

    abstract fun getPagesButtons(player: Player): MutableMap<Int, Button>
    abstract fun getTitle(player: Player): String

    fun getButtonsInRange() : MutableMap<Int, Button> {
        val buttons = hashMapOf<Int, Button>()
        
        val buttonSlots = getAllPagesButtonSlots()
        if (buttonSlots.isEmpty()) {
            val minIndex = ((page - 1) * 18)
            val maxIndex = (page * 18)

            for (entry in getPagesButtons(player).entries) {
                var ind = entry.key
                if (ind in minIndex until maxIndex) {
                    ind -= (18 * (page - 1)) - 9
                    buttons[0 + ind] = entry.value
                }
            }
        } else {
            val maxPerPage = buttonSlots.size
            val minIndex = (page - 1) * maxPerPage
            val maxIndex = page * maxPerPage

            for ((index, entry) in getPagesButtons(player).entries.withIndex()) {
                if (index in minIndex until Math.min(maxIndex, buttonSlots.size)) {
                    buttons[buttonSlots[index]] = entry.value
                }
            }
        }

        return buttons

    }

    fun getPageNavigationButtons() : MutableMap<Int, Button> {
        val buttons = hashMapOf<Int, Button>()

        buttons[0] = object : Button() {
            override fun getMaterial(player: Player): Material {
                return Material.FEATHER
            }

            override fun getDescription(player: Player): MutableList<String>? {
                return Collections.singletonList(ChatColor.translateAlternateColorCodes('&', "&cNavigate to previous page"))
            }

            override fun getDisplayName(player: Player): String? {
                return ChatColor.translateAlternateColorCodes('&', "&cCurrent Page: &f$page")
            }

            override fun onClick(player: Player, slot: Int, type: ClickType) {
                if (page == 1) {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou are already on the last page!"))
                    return
                }
                page -= 1
                updateMenu()
            }
        }

        buttons[8] = object : Button() {
            override fun getMaterial(player: Player): Material {
                return Material.FEATHER
            }

            override fun getDescription(player: Player): MutableList<String>? {
                return Collections.singletonList(ChatColor.translateAlternateColorCodes('&', "&cNavigate to next page"))
            }

            override fun getDisplayName(player: Player): String? {
                return ChatColor.translateAlternateColorCodes('&', "&cCurrent Page: &f$page")
            }

            override fun onClick(player: Player, slot: Int, type: ClickType) {
                page += 1
                updateMenu()
            }
        }

        return buttons
    }

    open fun getAllPagesButtonSlots(): List<Int> {
        return emptyList()
    }

    fun getMaximumPages(player: Player) : Int {
        val buttons = getPagesButtons(player)

        return if (buttons.size == 0) {
            1
        } else {
            Math.ceil(buttons.size / (18).toDouble()).toInt()
        }
    }


    fun updateMenu() {
        val inventory = Bukkit.createInventory(null, (size + 9), getTitle(player))


        for (entry in getPageNavigationButtons()) {
            inventory.setItem(entry.key, entry.value.constructItemStack(player))
        }

        for (entry in getButtonsInRange()) {
            inventory.setItem(entry.key, entry.value.constructItemStack(player))
        }

        player.openInventory(inventory)
        player.updateInventory()
        MenuController.paginatedMenuMap.put(player.uniqueId, this)
    }

}



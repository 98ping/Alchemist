package ltd.matrixstudios.alchemist.util.menu.pagination

import ltd.matrixstudios.alchemist.util.menu.Button
import ltd.matrixstudios.alchemist.util.menu.MenuController
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import java.util.*
import java.util.concurrent.CompletableFuture

abstract class PaginatedMenu(
    private val size: Int,
    private val player: Player
) {

    var page = 1;

    abstract fun getPagesButtons(player: Player): MutableMap<Int, Button>
    abstract fun getTitle(player: Player): String

    fun getButtonsInRange() : CompletableFuture<MutableMap<Int, Button>> {
        return CompletableFuture.supplyAsync {
            val buttons = hashMapOf<Int, Button>()

            val buttonSlots = getAllPagesButtonSlots()
            if (buttonSlots.isEmpty()) {
                val minIndex = ((page - 1) * size)
                val maxIndex = (page * size)

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
            return@supplyAsync buttons
        }

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

            override fun getData(player: Player): Short {
                return 0
            }

            override fun onClick(player: Player, slot: Int, type: ClickType) {
                if (page == 1) {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou are already on the first page!"))
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

            override fun getData(player: Player): Short {
                return 0
            }

            override fun onClick(player: Player, slot: Int, type: ClickType) {
                if (page >= getMaximumPages(player)) {
                    player.sendMessage("${ChatColor.RED}You have already reached the last page!")
                    return
                }

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

    open fun getHeaderItems(player: Player) : MutableMap<Int, Button> {
        return mutableMapOf()
    }
    
    fun updateMenu() {
        val inventory = Bukkit.createInventory(null, (size + 9), getTitle(player))


        for (entry in getPageNavigationButtons()) {
            inventory.setItem(entry.key, entry.value.constructItemStack(player))
        }

        for (entry in getHeaderItems(player)) {
            inventory.setItem(entry.key, entry.value.constructItemStack(player))
        }

        CompletableFuture.runAsync {
            for (entry in getButtonsInRange().get()) {
                inventory.setItem(entry.key, entry.value.constructItemStack(player))
            }
        }

        player.openInventory(inventory)
        player.updateInventory()
        MenuController.paginatedMenuMap[player.uniqueId] = this
    }

}



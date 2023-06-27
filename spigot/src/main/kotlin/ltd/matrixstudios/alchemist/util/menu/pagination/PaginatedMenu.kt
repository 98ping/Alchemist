package ltd.matrixstudios.alchemist.util.menu.pagination

import ltd.matrixstudios.alchemist.util.Chat
import ltd.matrixstudios.alchemist.util.menu.Button
import ltd.matrixstudios.alchemist.util.menu.MenuController
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import java.util.*
import java.util.concurrent.CompletableFuture
import kotlin.math.ceil
import kotlin.math.roundToInt

abstract class PaginatedMenu(
    private val displaySize: Int,
    private val player: Player
) {

    private var currentPage = 1
    private var maxPages = 1

    abstract fun getPagesButtons(player: Player): MutableMap<Int, Button>
    abstract fun getTitle(player: Player): String

    fun getButtonsInRange(player: Player): MutableMap<Int, Button> {
        val buttons = mutableMapOf<Int, Button>()

        buttons[getPageButtonPositions().first] = getPreviousPageButton()
        buttons[getPageButtonPositions().second] = getNextPageButton()

        val paginatedButtons = getPagesButtons(player)

        var current = getButtonsStartAt()
        val buttonAmount = paginatedButtons.size

        maxPages = if (buttonAmount == 0) {
            1
        } else {
            ceil(buttonAmount / getButtonsPerPage().toDouble()).toInt()
        }

        for ((slot, button) in getHeaderItems(player)) {
            buttons[slot] = button
        }


        val minIndex = ((currentPage - 1) * getButtonsPerPage())
        val maxIndex = (currentPage * getButtonsPerPage())

        var i = 0

        val positions = getButtonPositions()
        var lastPos = positions.first()
        var lastIndex = 0

        for (button in paginatedButtons) {
            if (current >= displaySize) return buttons

            if (lastIndex - 1 >= positions.size) return buttons


            if (i !in minIndex until maxIndex) {
                i++
                continue
            }

            if (positions.isNotEmpty()) {
                buttons[lastPos] = button.value
                try {
                    lastPos = positions[lastIndex + 1]
                } catch (e: IndexOutOfBoundsException) {
                    return buttons
                }
                lastIndex++
                continue
            }

            if (!buttons.containsKey(current)) {
                buttons[current] = button.value
            }

            current++
            i++
        }

        return buttons
    }

    open fun getButtonPositions(): List<Int> {
        val mutableList = mutableListOf<Int>()

        for (int in 9 until displaySize + 9)
        {
            mutableList.add(int)
        }

        return mutableList.toList()
    }

    fun getPreviousPageButton(): Button {
        val button = object : Button() {
            override fun getMaterial(player: Player): Material {
                return Material.FEATHER
            }

            override fun getDescription(player: Player): MutableList<String>? {
                return Collections.singletonList(
                    ChatColor.translateAlternateColorCodes(
                        '&',
                        "&cNavigate to previous page"
                    )
                )
            }

            override fun getDisplayName(player: Player): String? {
                return ChatColor.translateAlternateColorCodes('&', "&cCurrent Page: &f$currentPage")
            }

            override fun getData(player: Player): Short {
                return 0
            }

            override fun onClick(player: Player, slot: Int, type: ClickType) {
                if (currentPage == 1) {
                    player.sendMessage(
                        ChatColor.translateAlternateColorCodes(
                            '&',
                            "&cYou are already on the first page!"
                        )
                    )
                    return
                }
                currentPage -= 1
                updateMenu()
            }
        }

        return button
    }


    fun getNextPageButton(): Button {
        val button = object : Button() {
            override fun getMaterial(player: Player): Material {
                return Material.FEATHER
            }

            override fun getDescription(player: Player): MutableList<String>? {
                return Collections.singletonList(ChatColor.translateAlternateColorCodes('&', "&cNavigate to next page"))
            }

            override fun getDisplayName(player: Player): String? {
                return ChatColor.translateAlternateColorCodes('&', "&cCurrent Page: &f$currentPage")
            }

            override fun getData(player: Player): Short {
                return 0
            }

            override fun onClick(player: Player, slot: Int, type: ClickType) {
                if (currentPage >= maxPages) {
                    player.sendMessage("${ChatColor.RED}You have already reached the last page!")
                    return
                }

                currentPage += 1
                updateMenu()
            }
        }

        return button
    }

    open fun getPageButtonPositions(): Pair<Int, Int> {
        return Pair(0, 8)
    }

    open fun getButtonsStartAt(): Int {
        return 9
    }

    open fun getButtonsPerPage(): Int {
        return 18
    }

    open fun getHeaderItems(player: Player): MutableMap<Int, Button> {
        return mutableMapOf()
    }

    fun updateMenu() {
        val buttons = getButtonsInRange(player)

        val inventory = Bukkit.createInventory(null, (displaySize + 9), Chat.format("($currentPage/${if (maxPages == 0) 1 else maxPages}) ") + getTitle(player))

        CompletableFuture.runAsync {
            for (entry in buttons) {
                inventory.setItem(entry.key, entry.value.constructItemStack(player))
            }
        }

        player.openInventory(inventory)
        player.updateInventory()
        MenuController.paginatedMenuMap[player.uniqueId] = this
    }

}



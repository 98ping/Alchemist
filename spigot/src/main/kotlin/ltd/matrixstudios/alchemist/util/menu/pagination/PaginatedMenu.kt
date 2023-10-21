package ltd.matrixstudios.alchemist.util.menu.pagination

import ltd.matrixstudios.alchemist.AlchemistSpigotPlugin
import ltd.matrixstudios.alchemist.util.Chat
import ltd.matrixstudios.alchemist.util.menu.Button
import ltd.matrixstudios.alchemist.util.menu.MenuController
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import org.bukkit.inventory.Inventory
import java.util.*
import java.util.concurrent.CompletableFuture
import kotlin.math.ceil

abstract class PaginatedMenu(
    private val displaySize: Int,
    private val player: Player
)
{

    private var currentPage = 1
    private var maxPages = 1

    abstract fun getPagesButtons(player: Player): MutableMap<Int, Button>
    abstract fun getTitle(player: Player): String

    fun getButtonsInRange(player: Player): MutableMap<Int, Button>
    {
        val buttons = mutableMapOf<Int, Button>()

        val paginatedButtons = getPagesButtons(player)

        var current = getButtonsStartAt()
        val buttonAmount = paginatedButtons.size

        maxPages = if (buttonAmount == 0)
        {
            1
        } else
        {
            ceil(buttonAmount / getButtonsPerPage().toDouble()).toInt()
        }

        for ((slot, button) in getHeaderItems(player))
        {
            buttons[slot] = button
        }

        buttons[getPageButtonPositions().first] = getPreviousPageButton()
        buttons[getPageButtonPositions().second] = getNextPageButton()


        val minIndex = ((currentPage - 1) * getButtonsPerPage())
        val maxIndex = (currentPage * getButtonsPerPage())

        var i = 0

        val positions = getButtonPositions()
        var lastPos = positions.first()
        var lastIndex = 0

        for (button in paginatedButtons)
        {
            if (current >= displaySize) return buttons

            if (lastIndex - 1 >= positions.size) return buttons


            if (i !in minIndex until maxIndex)
            {
                i++
                continue
            }

            if (positions.isNotEmpty())
            {
                buttons[lastPos] = button.value
                try
                {
                    lastPos = positions[lastIndex + 1]
                } catch (e: IndexOutOfBoundsException)
                {
                    return buttons
                }
                lastIndex++
                continue
            }

            if (!buttons.containsKey(current))
            {
                buttons[current] = button.value
            }

            current++
            i++
        }

        return buttons
    }

    open fun getButtonPositions(): List<Int>
    {
        val mutableList = mutableListOf<Int>()

        for (int in 9 until displaySize + 9)
        {
            mutableList.add(int)
        }

        return mutableList.toList()
    }

    fun getPreviousPageButton(): Button
    {
        val button = object : Button()
        {
            override fun getMaterial(player: Player): Material
            {
                return Material.MELON
            }

            override fun getDescription(player: Player): MutableList<String>?
            {
                return Collections.singletonList(
                    ChatColor.translateAlternateColorCodes(
                        '&',
                        "&eNavigate to previous page"
                    )
                )
            }

            override fun getDisplayName(player: Player): String?
            {
                return ChatColor.translateAlternateColorCodes('&', "&cPrevious Page &7(&e$currentPage&7/&e$maxPages&7)")
            }

            override fun getData(player: Player): Short
            {
                return 0
            }

            override fun onClick(player: Player, slot: Int, type: ClickType)
            {
                if (currentPage == 1)
                {
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


    fun getNextPageButton(): Button
    {
        val button = object : Button()
        {
            override fun getMaterial(player: Player): Material
            {
                return Material.MELON
            }

            override fun getDescription(player: Player): MutableList<String>?
            {
                return Collections.singletonList(ChatColor.translateAlternateColorCodes('&', "&eNavigate to next page"))
            }

            override fun getDisplayName(player: Player): String?
            {
                return ChatColor.translateAlternateColorCodes('&', "&aNext page &7(&e$currentPage&7/&e$maxPages&7)")
            }

            override fun getData(player: Player): Short
            {
                return 0
            }

            override fun onClick(player: Player, slot: Int, type: ClickType)
            {
                if (currentPage >= maxPages)
                {
                    player.sendMessage("${ChatColor.RED}You have already reached the last page!")
                    return
                }

                currentPage += 1
                updateMenu()
            }
        }

        return button
    }

    open fun getPageButtonPositions(): Pair<Int, Int>
    {
        return Pair(0, 8)
    }

    open fun getButtonsStartAt(): Int
    {
        return 9
    }

    open fun getButtonsPerPage(): Int
    {
        return 18
    }

    open fun getHeaderItems(player: Player): MutableMap<Int, Button>
    {
        return mutableMapOf()
    }

    fun handleAutoUpdate(player: Player)
    {
        val inventory = player.openInventory.topInventory ?: return

        if (!MenuController.paginatedMenuMap.containsKey(player.uniqueId)) return

        if (MenuController.menuMap.containsKey(player.uniqueId)) return

        retrieveInventory().thenAccept {
            try
            {
                if (inventory.size != it.size || inventory.viewers.isEmpty()) return@thenAccept

                inventory.contents = it.contents
            } catch (e: IllegalArgumentException)
            {
                if (player.openInventory.topInventory != null)
                {
                    Bukkit.getScheduler().runTask(AlchemistSpigotPlugin.instance) {
                        player.closeInventory()
                    }
                    player.sendMessage(Chat.format("&cCould not set contents. Please try again"))
                }
            }
        }.whenComplete { item, throwable ->
            if (throwable != null)
            {
                throwable.printStackTrace()
                player.sendMessage(
                    "${ChatColor.RED}Failed to update menu."
                )

                if (MenuController.paginatedMenuMap.containsKey(player.uniqueId))
                {
                    MenuController.paginatedMenuMap.remove(player.uniqueId)
                }

                Bukkit.getScheduler().runTask(AlchemistSpigotPlugin.instance) {
                    player.closeInventory()
                }

                return@whenComplete
            }

            Bukkit.getScheduler()
                .runTask(
                    AlchemistSpigotPlugin.instance
                ) {
                    player.updateInventory()

                    MenuController.paginatedMenuMap[player.uniqueId] = this
                }
        }
    }

    fun updateMenu()
    {
        val buttons = getButtonsInRange(player)

        val inventory = Bukkit.createInventory(
            null,
            (displaySize + 9),
            Chat.format("($currentPage/${if (maxPages == 0) 1 else maxPages}) ") + getTitle(player)
        )

        if (MenuController.menuMap.containsKey(player.uniqueId))
        {
            MenuController.menuMap.remove(player.uniqueId)
        }

        CompletableFuture.runAsync {
            for (entry in buttons)
            {
                inventory.setItem(entry.key, entry.value.constructItemStack(player))
            }
        }.whenComplete { item, throwable ->
            if (throwable != null)
            {
                throwable.printStackTrace()
                player.sendMessage(
                    "${ChatColor.RED}Failed to open menu."
                )

                if (MenuController.paginatedMenuMap.containsKey(player.uniqueId))
                {
                    MenuController.paginatedMenuMap.remove(player.uniqueId)
                }

                return@whenComplete
            }

            Bukkit.getScheduler()
                .runTask(
                    AlchemistSpigotPlugin.instance
                ) {
                    player.openInventory(inventory)
                    player.updateInventory()

                    MenuController.paginatedMenuMap[player.uniqueId] = this
                }
        }
    }

    fun retrieveInventory(): CompletableFuture<Inventory>
    {
        val buttons = getButtonsInRange(player)

        val inventory = Bukkit.createInventory(
            null,
            (displaySize + 9),
            Chat.format("($currentPage/${if (maxPages == 0) 1 else maxPages}) ") + getTitle(player)
        )


        return CompletableFuture.runAsync {
            for (entry in buttons)
            {
                inventory.setItem(entry.key, entry.value.constructItemStack(player))
            }
        }.thenApply {
            return@thenApply inventory
        }
    }
}



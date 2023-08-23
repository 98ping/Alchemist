package ltd.matrixstudios.alchemist.commands.coins.shop

import ltd.matrixstudios.alchemist.Alchemist
import ltd.matrixstudios.alchemist.commands.coins.CoinShopManager
import ltd.matrixstudios.alchemist.commands.coins.category.CoinShopCategory
import ltd.matrixstudios.alchemist.util.Chat
import ltd.matrixstudios.alchemist.util.menu.Button
import ltd.matrixstudios.alchemist.util.menu.Menu
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType

/**
 * Class created on 7/4/2023

 * @author 98ping
 * @project Alchemist
 * @website https://solo.to/redis
 */
class CoinShopMenu(val player: Player) : Menu(player) {
    override fun getButtons(player: Player): MutableMap<Int, Button> {
        val buttons = mutableMapOf<Int, Button>()

        for (category in CoinShopManager.categoryMap.values)
        {
            if (category.activeOn.contains(Alchemist.globalServer.id)) {
                buttons[category.menuSlot] = CategoryDisplayButton(category)
            }
        }

        return buttons
    }

    override fun getTitle(player: Player): String {
        return "Coin Shop"
    }

    class CategoryDisplayButton(val category: CoinShopCategory) : Button() {
        override fun getMaterial(player: Player): Material {
            return Material.getMaterial(category.displayItem) ?: Material.PAPER
        }

        override fun getDescription(player: Player): MutableList<String>? {
            return category.desc.map { Chat.format(it) }.toMutableList()
        }

        override fun getDisplayName(player: Player): String? {
            return Chat.format(category.displayName)
        }

        override fun getData(player: Player): Short {
            return category.data
        }

        override fun onClick(player: Player, slot: Int, type: ClickType) {

        }

    }
}
package ltd.matrixstudios.alchemist.commands.coins.shop.sub

import ltd.matrixstudios.alchemist.commands.coins.category.CoinShopCategory
import ltd.matrixstudios.alchemist.commands.coins.shop.CoinShopMenu
import ltd.matrixstudios.alchemist.profiles.commands.sibling.menu.SiblingCheckMenu
import ltd.matrixstudios.alchemist.util.Chat
import ltd.matrixstudios.alchemist.util.menu.Button
import ltd.matrixstudios.alchemist.util.menu.buttons.SimpleActionButton
import ltd.matrixstudios.alchemist.util.menu.pagination.PaginatedMenu
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType

/**
 * Class created on 8/23/2023

 * @author 98ping
 * @project Alchemist
 * @website https://solo.to/redis
 */
class CoinShopDisplayCategoriesMenu(val player: Player, val parent: CoinShopCategory) : PaginatedMenu(18, player) {
    override fun getPagesButtons(player: Player): MutableMap<Int, Button> {
        var i = 0
        val buttons = mutableMapOf<Int, Button>()

        for (sibling in parent.getCategoriesThatParentThisOne()) {
            buttons[i++] = CategoryDisplayButton(sibling)
        }

        return buttons
    }

    override fun getButtonPositions(): List<Int> {
        return listOf(10, 11, 12, 13, 14, 15, 16)
    }

    override fun getButtonsPerPage(): Int {
        return 7
    }

    override fun getHeaderItems(player: Player): MutableMap<Int, Button> {
        return mutableMapOf(
            1 to Button.placeholder(),
            2 to Button.placeholder(),
            3 to Button.placeholder(),
            4 to SimpleActionButton(Material.PAPER, mutableListOf(), Chat.format("&eGo Back"), 0).setBody { player, i, clickType ->
                CoinShopMenu(player).openMenu()
            },
            5 to Button.placeholder(),
            6 to Button.placeholder(),
            7 to Button.placeholder(),
            9 to Button.placeholder(),
            17 to Button.placeholder(),
            18 to Button.placeholder(),
            19 to Button.placeholder(),
            20 to Button.placeholder(),
            21 to Button.placeholder(),
            22 to Button.placeholder(),
            23 to Button.placeholder(),
            24 to Button.placeholder(),
            25 to Button.placeholder(),
            26 to Button.placeholder()
        )
    }

    override fun getTitle(player: Player): String {
        return Chat.format("&7Find a sub category")
    }

    class CategoryDisplayButton(val category: CoinShopCategory) : Button()
    {
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
            CoinShopDisplayProductsMenu(category, player).updateMenu()
        }

    }
}
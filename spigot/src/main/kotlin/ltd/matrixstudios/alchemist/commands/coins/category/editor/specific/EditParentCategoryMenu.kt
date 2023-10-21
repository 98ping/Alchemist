package ltd.matrixstudios.alchemist.commands.coins.category.editor.specific

import ltd.matrixstudios.alchemist.commands.coins.CoinShopManager
import ltd.matrixstudios.alchemist.commands.coins.category.CoinShopCategory
import ltd.matrixstudios.alchemist.commands.coins.category.editor.EditCategoryAttributesMenu
import ltd.matrixstudios.alchemist.util.Chat
import ltd.matrixstudios.alchemist.util.menu.Button
import ltd.matrixstudios.alchemist.util.menu.buttons.SimpleActionButton
import ltd.matrixstudios.alchemist.util.menu.pagination.PaginatedMenu
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType

/**
 * Class created on 8/22/2023

 * @author 98ping
 * @project Alchemist
 * @website https://solo.to/redis
 */
class EditParentCategoryMenu(val player: Player, val category: CoinShopCategory) : PaginatedMenu(18, player)
{

    override fun getHeaderItems(player: Player): MutableMap<Int, Button>
    {
        return mutableMapOf(
            4 to SimpleActionButton(Material.PAPER, mutableListOf(), "&cGo Back", 0).setBody { player, i, clickType ->
                EditCategoryAttributesMenu(player, category).openMenu()
            }
        )
    }

    override fun getPagesButtons(player: Player): MutableMap<Int, Button>
    {
        val buttons = mutableMapOf<Int, Button>()
        var i = 0

        for (other in CoinShopManager.categoryMap.values)
        {
            if (other.id == category.id) continue

            buttons[i++] = CategoryButton(category, other)
        }

        return buttons
    }

    override fun getTitle(player: Player): String
    {
        return "Select Parent Category"
    }

    class CategoryButton(val item: CoinShopCategory, val other: CoinShopCategory) : Button()
    {
        override fun getMaterial(player: Player): Material
        {
            return Material.CHEST
        }

        override fun getDescription(player: Player): MutableList<String>
        {
            return mutableListOf()
        }

        override fun getDisplayName(player: Player): String
        {
            return Chat.format(other.displayName)
        }

        override fun getData(player: Player): Short
        {
            return 0
        }

        override fun onClick(player: Player, slot: Int, type: ClickType)
        {
            item.parentCategory = other.id
            CoinShopManager.saveCategory(item)
            player.sendMessage(Chat.format("&aSet the parent category of ${item.displayName} &ato &f" + other.displayName))
            player.closeInventory()
            EditCategoryAttributesMenu(player, item).openMenu()
        }
    }
}
package ltd.matrixstudios.alchemist.commands.coins.shop.sub

import ltd.matrixstudios.alchemist.commands.coins.cart.CartHandler
import ltd.matrixstudios.alchemist.commands.coins.category.CoinShopCategory
import ltd.matrixstudios.alchemist.commands.coins.item.CoinShopItem
import ltd.matrixstudios.alchemist.commands.coins.shop.CoinShopMenu
import ltd.matrixstudios.alchemist.util.Chat
import ltd.matrixstudios.alchemist.util.menu.Button
import ltd.matrixstudios.alchemist.util.menu.type.BorderedPaginatedMenu
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType

/**
 * Class created on 8/23/2023

 * @author 98ping
 * @project Alchemist
 * @website https://solo.to/redis
 */
class CoinShopDisplayProductsMenu(val category: CoinShopCategory, val player: Player) : BorderedPaginatedMenu(player)
{
    override fun getPagesButtons(player: Player): MutableMap<Int, Button>
    {
        val buttons = mutableMapOf<Int, Button>()
        var i = 0

        for (item in category.getAllProducts().sortedByDescending { it.findOrder() })
        {
            buttons[i++] = CoinShopButton(item)
        }

        return buttons
    }

    override fun getTitle(player: Player): String
    {
        return Chat.format("&7All Items for ${category.displayName}")
    }

    class CoinShopButton(val item: CoinShopItem) : Button()
    {
        override fun getMaterial(player: Player): Material
        {
            return Material.getMaterial(item.displayMaterial) ?: Material.PAPER
        }

        override fun getDescription(player: Player): MutableList<String>
        {
            val desc = item.lore.toMutableList()

            desc.add(" ")
            desc.add(Chat.format("&aClick to purchase this item"))
            desc.add(" ")


            return desc.map { Chat.format(it) }.toMutableList()
        }

        override fun getDisplayName(player: Player): String
        {
            return Chat.format(item.displayName)
        }

        override fun getData(player: Player): Short
        {
            return item.data
        }

        override fun onClick(player: Player, slot: Int, type: ClickType)
        {
            CartHandler.addToCart(player.uniqueId, item)
            player.sendMessage(Chat.format("&aYou have added ${item.displayName} &ato your cart!"))
            CoinShopMenu(player).openMenu()
        }

    }
}
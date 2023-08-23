package ltd.matrixstudios.alchemist.commands.coins.cart

import ltd.matrixstudios.alchemist.commands.coins.item.CoinShopItem
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
class MyCartMenu(val player: Player) : BorderedPaginatedMenu(player) {
    override fun getPagesButtons(player: Player): MutableMap<Int, Button> {
        val currentCart = CartHandler.carts[player.uniqueId]
        val buttons = mutableMapOf<Int, Button>()
        var i = 0

        if (currentCart != null)
        {
            for (item in currentCart.items)
            {
                buttons[i++] = CartItem(item)
            }
        }

        return buttons
    }

    override fun getTitle(player: Player): String {
        return "Your Cart"
    }

    class CartItem(val item: CoinShopItem) : Button() {
        override fun getMaterial(player: Player): Material {
            return Material.getMaterial(item.displayMaterial) ?: Material.PAPER
        }

        override fun getDescription(player: Player): MutableList<String>? {
            return item.lore.map { Chat.format(it) }.toMutableList()
        }

        override fun getDisplayName(player: Player): String? {
            return Chat.format(item.displayName)
        }

        override fun getData(player: Player): Short {
            return item.data
        }

        override fun onClick(player: Player, slot: Int, type: ClickType) {}

    }
}
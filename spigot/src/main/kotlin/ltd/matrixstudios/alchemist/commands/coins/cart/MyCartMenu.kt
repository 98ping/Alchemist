package ltd.matrixstudios.alchemist.commands.coins.cart

import ltd.matrixstudios.alchemist.commands.coins.CoinShopManager
import ltd.matrixstudios.alchemist.commands.coins.cart.model.Cart
import ltd.matrixstudios.alchemist.commands.coins.editor.items.CoinShopItemEditor
import ltd.matrixstudios.alchemist.commands.coins.item.CoinShopItem
import ltd.matrixstudios.alchemist.service.server.UniqueServerService
import ltd.matrixstudios.alchemist.util.Chat
import ltd.matrixstudios.alchemist.util.InputPrompt
import ltd.matrixstudios.alchemist.util.menu.Button
import ltd.matrixstudios.alchemist.util.menu.buttons.SimpleActionButton
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

    override fun getHeaderItems(player: Player): MutableMap<Int, Button> {
        return mutableMapOf(
            1 to Button.placeholder(),
            2 to Button.placeholder(),
            3 to Button.placeholder(),
            4 to SimpleActionButton(Material.EMERALD, getCheckoutDesc(CartHandler.carts[player.uniqueId]!!), Chat.format("&eCheck Out"), 0).setBody { player, i, clickType -> },
            5 to Button.placeholder(),
            6 to Button.placeholder(),
            7 to Button.placeholder(),
            9 to Button.placeholder(),
            17 to Button.placeholder(),
            18 to Button.placeholder(),
            26 to Button.placeholder(),
            27 to Button.placeholder(),
            35 to Button.placeholder(),
            36 to Button.placeholder(),
            37 to Button.placeholder(),
            38 to Button.placeholder(),
            39 to Button.placeholder(),
            40 to Button.placeholder(),
            41 to Button.placeholder(),
            42 to Button.placeholder(),
            43 to Button.placeholder(),
            44 to Button.placeholder(),
        )
    }

    fun getCheckoutDesc(cart: Cart) : MutableList<String> {
        val desc = mutableListOf<String>()
        desc.add(" ")
        for (item in cart.items)
        {
            desc.add(Chat.format("&7- &r${item.displayName} " +
                    "&7(" + (if (item.discount != 0.0) "&c&m$${Math.round(item.price)}&r &7-> &a$${Math.round(item.price.minus(item.discount))}" else "&a${Math.round(item.price)}") + "&7)"))
        }
        desc.add(" ")
        desc.add(Chat.format("&7Total: &f$" + cart.getCombinedPrice()))
        desc.add(Chat.format("&aClick to check out!"))

        return desc
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
package ltd.matrixstudios.alchemist.commands.coins.editor.items

import ltd.matrixstudios.alchemist.commands.coins.CoinShopManager
import ltd.matrixstudios.alchemist.commands.coins.editor.items.specific.SelectCategoryMenu
import ltd.matrixstudios.alchemist.commands.coins.editor.items.specific.SelectRankMenu
import ltd.matrixstudios.alchemist.commands.coins.editor.items.specific.SelectServersMenu
import ltd.matrixstudios.alchemist.commands.coins.item.CoinShopItem
import ltd.matrixstudios.alchemist.util.Chat
import ltd.matrixstudios.alchemist.util.InputPrompt
import ltd.matrixstudios.alchemist.util.menu.Button
import ltd.matrixstudios.alchemist.util.menu.Menu
import ltd.matrixstudios.alchemist.util.menu.buttons.SimpleActionButton
import org.bukkit.Material
import org.bukkit.entity.Player

class CoinShopItemAttributeEditor(val player: Player, val item: CoinShopItem) : Menu(player) {

    init {
        placeholder = true
        staticSize = 27
    }

    override fun getButtons(player: Player): MutableMap<Int, Button> {
        val buttons = mutableMapOf<Int, Button>()

        buttons[10] = SimpleActionButton(
            Material.EMERALD,
            mutableListOf(
                " ",
                Chat.format("&7Change the price of this item"),
                Chat.format("&7that players will pay when"),
                Chat.format("&7checking out of the store."),
                " ",
                Chat.format("&eCurrently: &f${item.price}"),
                " "
            ),
            "&eChange Price", 0
        ).setBody { player, slot, clicktype ->
            InputPrompt()
                .withText(Chat.format("&aType in the new price for this item!"))
                .acceptInput {
                    var newPrice = 0

                    try {
                        newPrice = Integer.parseInt(it)
                    } catch (e: java.lang.NumberFormatException)
                    {
                        player.sendMessage(Chat.format("&cThis is not a number!"))
                        return@acceptInput
                    }

                    item.price = newPrice.toDouble()
                    CoinShopManager.saveItem(item)
                    CoinShopItemAttributeEditor(player, item).openMenu()
                    player.sendMessage(Chat.format("&aUpdated ${item.displayName}'s &aprice to &f$${newPrice}"))
                }.start(player)
        }

        buttons[11] = SimpleActionButton(
            Material.BEACON,
            mutableListOf(
                " ",
                Chat.format("&7Change the material of this"),
                Chat.format("&7item that players will see"),
                Chat.format("&7when checking out of the store."),
                " ",
                Chat.format("&eCurrently: &f${item.displayMaterial}"),
                " "
            ),
            "&eChange Material", 0
        ).setBody { player, slot, clicktype ->
            InputPrompt()
                .withText(Chat.format("&aType in the new material for this item!"))
                .acceptInput {
                    item.displayMaterial = it.toUpperCase()
                    CoinShopManager.saveItem(item)
                    CoinShopItemAttributeEditor(player, item).openMenu()
                    player.sendMessage(Chat.format("&aUpdated ${item.displayName}'s &amenu material to &f${it.toUpperCase()}"))
                }.start(player)
        }

        buttons[12] = SimpleActionButton(
            Material.GOLD_NUGGET,
            mutableListOf(
                " ",
                Chat.format("&7Change the discount price"),
                Chat.format("&7of this item. This will make"),
                Chat.format("&7the final price be less than the"),
                Chat.format("&7original price"),
                " ",
                Chat.format("&eCurrently: &f${item.discount}"),
                " "
            ),
            "&eChange Discount", 0
        ).setBody { player, slot, clicktype ->
            InputPrompt()
                .withText(Chat.format("&aType in the new discount price for this item!"))
                .acceptInput {
                    var newPrice = 0

                    try {
                        newPrice = Integer.parseInt(it)
                    } catch (e: java.lang.NumberFormatException)
                    {
                        player.sendMessage(Chat.format("&cThis is not a number!"))
                        return@acceptInput
                    }

                    item.discount = newPrice.toDouble()
                    CoinShopManager.saveItem(item)
                    CoinShopItemAttributeEditor(player, item).openMenu()
                    player.sendMessage(Chat.format("&aUpdated ${item.displayName}'s &adiscount price to &f$${newPrice}"))
                }.start(player)
        }

        buttons[13] = SimpleActionButton(
            Material.COMPASS,
            mutableListOf(
                " ",
                Chat.format("&7Change the required rank that"),
                Chat.format("&7the purchaser of this package"),
                Chat.format("&7will need to have in order to"),
                Chat.format("&7check it out of their cart"),
                " ",
                Chat.format("&eCurrently: &f${item.requiredRank}"),
                " "
            ),
            "&eChange Required Rank", 0
        ).setBody { player, slot, clicktype ->
            SelectRankMenu(player, item).updateMenu()
        }

        buttons[14] = SimpleActionButton(
            Material.CHEST,
            mutableListOf(
                " ",
                Chat.format("&7Change the category that this"),
                Chat.format("&7package will fall under in the"),
                Chat.format("&7coin shop menu."),
                " ",
                Chat.format("&eCurrently: &f${item.category}"),
                " "
            ),
            "&eChange Category", 0
        ).setBody { player, slot, clicktype ->
            SelectCategoryMenu(player, item).updateMenu()
        }

        buttons[15] = SimpleActionButton(
            Material.PAPER,
            mutableListOf(
                " ",
                Chat.format("&7Change the display name of this"),
                Chat.format("&7item that players will see"),
                Chat.format("&7when checking out of the store."),
                " ",
                Chat.format("&eCurrently: &f${item.displayName}"),
                " "
            ),
            "&eChange Display Name", 0
        ).setBody { player, slot, clicktype ->
            InputPrompt()
                .withText(Chat.format("&aType in the new display name for this item!"))
                .acceptInput {
                    item.displayName = it
                    CoinShopManager.saveItem(item)
                    CoinShopItemAttributeEditor(player, item).openMenu()
                    player.sendMessage(Chat.format("&aUpdated ${item.displayName}'s &amenu name to &f${it}"))
                }.start(player)
        }

        buttons[16] = SimpleActionButton(
            Material.ARROW,
            mutableListOf(
                " ",
                Chat.format("&7Change the servers that this"),
                Chat.format("&7item will be visible on"),
                " "
            ),
            "&eChange Active Servers", 0
        ).setBody { player, slot, clicktype ->
            SelectServersMenu(player, item).updateMenu()
        }


        return buttons
    }

    override fun getTitle(player: Player): String {
        return Chat.format("&7[Editor] ${item.displayName}")
    }
}
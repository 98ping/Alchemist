package ltd.matrixstudios.alchemist.commands.coins.category.editor

/**
 * Class created on 8/22/2023

 * @author 98ping
 * @project Alchemist
 * @website https://solo.to/redis
 */

import ltd.matrixstudios.alchemist.commands.coins.CoinShopManager
import ltd.matrixstudios.alchemist.commands.coins.category.CoinShopCategory
import ltd.matrixstudios.alchemist.commands.coins.category.editor.specific.EditParentCategoryMenu
import ltd.matrixstudios.alchemist.commands.coins.category.editor.specific.SelectCategoryServers
import ltd.matrixstudios.alchemist.commands.coins.editor.items.CoinShopItemEditor
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

class EditCategoryAttributesMenu(val player: Player, val item: CoinShopCategory) : Menu(player) {

    init {
        placeholder = true
        staticSize = 27
    }

    override fun getButtons(player: Player): MutableMap<Int, Button> {
        val buttons = mutableMapOf<Int, Button>()

        buttons[10] = SimpleActionButton(
            Material.PAPER,
            mutableListOf(
                " ",
                Chat.format("&7Change the menu slot of this"),
                Chat.format("&7category. This will allow you"),
                Chat.format("&7to custom the &e/coinshop &7layout."),
                " ",
                Chat.format("&eCurrently: &f${item.menuSlot}"),
                " "
            ),
            "&eChange Slot", 0
        ).setBody { player, slot, clicktype ->
            InputPrompt()
                .withText(Chat.format("&aType in the new slot for this category!"))
                .acceptInput {
                    var newPrice = 0

                    try {
                        newPrice = Integer.parseInt(it)
                    } catch (e: java.lang.NumberFormatException)
                    {
                        player.sendMessage(Chat.format("&cThis is not a number!"))
                        return@acceptInput
                    }

                    item.menuSlot = newPrice
                    CoinShopManager.saveCategory(item)
                    EditCategoryAttributesMenu(player, item).openMenu()
                    player.sendMessage(Chat.format("&aUpdated ${item.displayName}'s &amenu slot to &f${newPrice}"))
                }.start(player)
        }

        buttons[11] = SimpleActionButton(
            Material.BEACON,
            mutableListOf(
                " ",
                Chat.format("&7Change the material of this"),
                Chat.format("&7category that players will see"),
                Chat.format("&7when looking at the store"),
                " ",
                Chat.format("&eCurrently: &f${item.displayItem}"),
                " "
            ),
            "&eChange Material", 0
        ).setBody { player, slot, clicktype ->
            InputPrompt()
                .withText(Chat.format("&aType in the new material for this item!"))
                .acceptInput {
                    item.displayItem = it.toUpperCase()
                    CoinShopManager.saveCategory(item)
                    EditCategoryAttributesMenu(player, item).openMenu()
                    player.sendMessage(Chat.format("&aUpdated ${item.displayName}'s &amenu material to &f${it.toUpperCase()}"))
                }.start(player)
        }

        buttons[12] = SimpleActionButton(
            Material.GOLD_NUGGET,
            mutableListOf(
                " ",
                Chat.format("&7Change the parent category"),
                Chat.format("&7of this category. This will"),
                Chat.format("&7make it so it opens an extra"),
                Chat.format("&7menu when clicked."),
                " ",
                Chat.format("&eCurrently: &f${item.parentCategory}"),
                " "
            ),
            "&eChange Parent Category", 0
        ).setBody { player, slot, clicktype ->
            EditParentCategoryMenu(player, item).updateMenu()
        }

        buttons[13] = SimpleActionButton(
            Material.ARROW,
            mutableListOf(
                " ",
                Chat.format("&7Change the servers that this"),
                Chat.format("&7category will appear on"),
                Chat.format("&7in the coinshop"),
                " "
            ),
            "&eChange Active Servers", 0
        ).setBody { player, slot, clicktype ->
            SelectCategoryServers(player, item).updateMenu()
        }

        buttons[14] = SimpleActionButton(
            Material.NAME_TAG,
            mutableListOf(
                " ",
                Chat.format("&7Change the display name"),
                Chat.format("&7category that players will see"),
                Chat.format("&7when looking at the store"),
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
                    CoinShopManager.saveCategory(item)
                    EditCategoryAttributesMenu(player, item).openMenu()
                    player.sendMessage(Chat.format("&aUpdated ${item.displayName}'s &adisplay name to &f${it}"))
                }.start(player)
        }

        buttons[16] = SimpleActionButton(
            Material.REDSTONE_BLOCK,
            mutableListOf(
                " ",
                Chat.format("&7This will completely"),
                Chat.format("&7delete this category from the"),
                Chat.format("&7database"),
                " "
            ),
            "&cDelete Category", 0
        ).setBody { player, slot, clicktype ->
            CoinShopManager.deleteCategory(item)
            player.sendMessage(Chat.format("&aDeleted this category from the database"))
            CoinShopCategoryEditor(player).updateMenu()
        }



        return buttons
    }

    override fun getTitle(player: Player): String {
        return Chat.format("&7[Editor] ${item.displayName}")
    }
}
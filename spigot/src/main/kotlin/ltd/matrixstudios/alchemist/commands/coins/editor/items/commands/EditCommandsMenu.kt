package ltd.matrixstudios.alchemist.commands.coins.editor.items.commands

import ltd.matrixstudios.alchemist.commands.coins.CoinShopManager
import ltd.matrixstudios.alchemist.commands.coins.item.CoinShopItem
import ltd.matrixstudios.alchemist.util.Chat
import ltd.matrixstudios.alchemist.util.InputPrompt
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
class EditCommandsMenu(val player: Player, val item: CoinShopItem) : PaginatedMenu(18, player)
{
    override fun getPagesButtons(player: Player): MutableMap<Int, Button>
    {
        val buttons = mutableMapOf<Int, Button>()
        var i = 0

        for (cmd in item.commands)
        {
            buttons[i++] = CommandButton(cmd, item)
        }

        return buttons
    }

    override fun getHeaderItems(player: Player): MutableMap<Int, Button>
    {
        return mutableMapOf(
            4 to SimpleActionButton(
                Material.NETHER_STAR,
                mutableListOf(),
                Chat.format("&aAdd New Command"),
                0
            ).setBody { player, i, clickType ->
                InputPrompt()
                    .withText(Chat.format("&aType in a new command to add. Use <target> as the target placeholder"))
                    .acceptInput {
                        item.commands.add(it)
                        CoinShopManager.saveItem(item)
                        player.sendMessage(Chat.format("&aAdded &f${it} &ato the item &r${item.displayName}"))
                    }.start(player)
            }
        )
    }

    override fun getTitle(player: Player): String
    {
        return "Edit Commands"
    }

    class CommandButton(val cmd: String, val item: CoinShopItem) : Button()
    {
        override fun getMaterial(player: Player): Material
        {
            return Material.SIGN
        }

        override fun getDescription(player: Player): MutableList<String>
        {
            return mutableListOf(
                " ",
                Chat.format("&cClick to delete this command from the item!"),
                " "
            )
        }

        override fun getDisplayName(player: Player): String
        {
            return Chat.format("&e${cmd}")
        }

        override fun getData(player: Player): Short
        {
            return 0
        }

        override fun onClick(player: Player, slot: Int, type: ClickType)
        {
            item.commands.remove(cmd)
            CoinShopManager.saveItem(item)
            player.sendMessage(Chat.format("&aRemoved &f${cmd} &afrom &r${item.displayName}"))
            EditCommandsMenu(player, item).updateMenu()
        }
    }
}
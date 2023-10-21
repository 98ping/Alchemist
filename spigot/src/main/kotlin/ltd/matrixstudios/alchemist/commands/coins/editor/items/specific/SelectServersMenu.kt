package ltd.matrixstudios.alchemist.commands.coins.editor.items.specific

import ltd.matrixstudios.alchemist.commands.coins.CoinShopManager
import ltd.matrixstudios.alchemist.commands.coins.editor.items.CoinShopItemAttributeEditor
import ltd.matrixstudios.alchemist.commands.coins.item.CoinShopItem
import ltd.matrixstudios.alchemist.models.server.UniqueServer
import ltd.matrixstudios.alchemist.service.server.UniqueServerService
import ltd.matrixstudios.alchemist.util.Chat
import ltd.matrixstudios.alchemist.util.menu.Button
import ltd.matrixstudios.alchemist.util.menu.buttons.SimpleActionButton
import ltd.matrixstudios.alchemist.util.menu.pagination.PaginatedMenu
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType

class SelectServersMenu(val player: Player, val coinShopItem: CoinShopItem) : PaginatedMenu(18, player)
{

    override fun getHeaderItems(player: Player): MutableMap<Int, Button>
    {
        return mutableMapOf(
            4 to SimpleActionButton(Material.PAPER, mutableListOf(), "&cGo Back", 0).setBody { player, i, clickType ->
                CoinShopItemAttributeEditor(player, coinShopItem).openMenu()
            }
        )
    }

    override fun getPagesButtons(player: Player): MutableMap<Int, Button>
    {
        val buttons = mutableMapOf<Int, Button>()
        var i = 0

        for (server in UniqueServerService.servers.values)
        {
            buttons[i++] = ServerButton(server, coinShopItem)
        }

        return buttons
    }

    override fun getTitle(player: Player): String
    {
        return "Select a Server"
    }


    class ServerButton(val server: UniqueServer, val item: CoinShopItem) : Button()
    {
        override fun getMaterial(player: Player): Material
        {
            return Material.BOOK
        }

        override fun getDescription(player: Player): MutableList<String>
        {
            return mutableListOf()
        }

        override fun getDisplayName(player: Player): String
        {
            return Chat.format((if (item.servers.contains(server.id)) "&a&l" else "&c&l") + server.displayName)
        }

        override fun getData(player: Player): Short
        {
            return 0
        }

        override fun onClick(player: Player, slot: Int, type: ClickType)
        {
            if (item.servers.contains(server.id))
            {
                item.servers.remove(server.id)
            } else
            {
                item.servers.add(server.id)
            }

            CoinShopManager.saveItem(item)
            player.sendMessage(Chat.format("&eUpdated active servers of ${item.displayName}"))
            SelectServersMenu(player, item).updateMenu()
        }

    }
}
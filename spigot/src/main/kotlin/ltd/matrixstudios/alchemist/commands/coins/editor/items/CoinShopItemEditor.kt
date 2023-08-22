package ltd.matrixstudios.alchemist.commands.coins.editor.items

import ltd.matrixstudios.alchemist.commands.coins.CoinShopManager
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

class CoinShopItemEditor(val player: Player) : BorderedPaginatedMenu(player) {
    override fun getPagesButtons(player: Player): MutableMap<Int, Button> {
        val buttons = mutableMapOf<Int, Button>()
        var i = 0

        for (item in CoinShopManager.itemMap.values)
        {
            buttons[i++] = CoinShopItemButton(item)
        }

        return buttons
    }

    override fun getTitle(player: Player): String {
        return Chat.format("&7[Editor] &eCoin Shop Items")
    }

    override fun getHeaderItems(player: Player): MutableMap<Int, Button> {
        return mutableMapOf(
            1 to Button.placeholder(),
            2 to Button.placeholder(),
            3 to Button.placeholder(),
            4 to SimpleActionButton(Material.NETHER_STAR, mutableListOf(), Chat.format("&aCreate New Item"), 0).setBody { player, i, clickType ->
                InputPrompt()
                    .withText(Chat.format("&aEnter the id of the item you want to create"))
                    .acceptInput { string ->
                        val item = CoinShopItem(
                            string.toLowerCase(),
                            string,
                            mutableListOf(),
                            UniqueServerService.servers.values.map { it.id }.toList(),
                            "None",
                            "DIAMOND",
                            "None",
                            0, mutableListOf(), 1000.0
                        )

                        CoinShopManager.saveItem(item).whenComplete { v, t ->
                            player.sendMessage(Chat.format("&aYou have created a new coin shop item with the id &f${v.id}"))
                            CoinShopItemEditor(player).updateMenu()
                        }
                    }.start(player)
            },
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

    class CoinShopItemButton(val item: CoinShopItem) : Button()
    {
        override fun getMaterial(player: Player): Material {
            return Material.getMaterial(item.displayMaterial) ?: return Material.PAPER
        }

        override fun getDescription(player: Player): MutableList<String>? {
            val desc = mutableListOf<String>()
            desc.add(" ")
            desc.add(Chat.format("&6&l｜ &ePrice: &f$${item.price}"))
            desc.add(Chat.format("&6&l｜ &eCategory: &f${item.category}"))
            desc.add(Chat.format("&6&l｜ &eDisplay Name: &f${item.displayMaterial}"))
            desc.add(Chat.format("&6&l｜ &eActive Discount: &f$${item.discount} off"))
            desc.add(Chat.format("&6&l｜ &eRequired Rank: &f${item.requiredRank}"))
            desc.add(Chat.format(" "))
            desc.add(Chat.format("&6&l｜ &eActive On: &f" + item.servers + " servers"))
            desc.add(Chat.format("&6&l｜ &eCommands:"))
            for (server in item.commands) {
                desc.add(Chat.format("&e- &f${server}"))
            }
            desc.add(" ")
            desc.add(Chat.format("&aClick To Edit Item"))
            return desc
        }

        override fun getDisplayName(player: Player): String? {
            return Chat.format(item.displayName)
        }

        override fun getData(player: Player): Short {
            return item.data
        }

        override fun onClick(player: Player, slot: Int, type: ClickType) {

        }

    }
}
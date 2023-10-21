package ltd.matrixstudios.alchemist.commands.coins.category.editor

import ltd.matrixstudios.alchemist.commands.coins.CoinShopManager
import ltd.matrixstudios.alchemist.commands.coins.category.CoinShopCategory
import ltd.matrixstudios.alchemist.util.Chat
import ltd.matrixstudios.alchemist.util.InputPrompt
import ltd.matrixstudios.alchemist.util.menu.Button
import ltd.matrixstudios.alchemist.util.menu.buttons.SimpleActionButton
import ltd.matrixstudios.alchemist.util.menu.type.BorderedPaginatedMenu
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import java.util.*

class CoinShopCategoryEditor(val player: Player) : BorderedPaginatedMenu(player)
{
    override fun getPagesButtons(player: Player): MutableMap<Int, Button>
    {
        val buttons = mutableMapOf<Int, Button>()
        var i = 0

        for (item in CoinShopManager.categoryMap.values)
        {
            buttons[i++] = CoinShopCategoryButton(item)
        }

        return buttons
    }

    override fun getTitle(player: Player): String
    {
        return Chat.format("&7[Editor] &eCoin Shop Categories")
    }

    override fun getHeaderItems(player: Player): MutableMap<Int, Button>
    {
        return mutableMapOf(
            1 to Button.placeholder(),
            2 to Button.placeholder(),
            3 to Button.placeholder(),
            4 to SimpleActionButton(
                Material.NETHER_STAR,
                mutableListOf(),
                Chat.format("&aCreate New Category"),
                0
            ).setBody { player, i, clickType ->
                InputPrompt()
                    .withText(Chat.format("&aEnter the id of the item you want to create"))
                    .acceptInput { string ->
                        val item = CoinShopCategory(
                            string.lowercase(Locale.getDefault()),
                            string,
                            mutableListOf(),
                            "DIAMOND",
                            mutableListOf(),
                            0,
                            0,
                            null
                        )

                        CoinShopManager.saveCategory(item).whenComplete { v, t ->
                            player.sendMessage(Chat.format("&aYou have created a new coin shop category with the id &f${v.id}"))
                            CoinShopCategoryEditor(player).updateMenu()
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

    class CoinShopCategoryButton(val item: CoinShopCategory) : Button()
    {
        override fun getMaterial(player: Player): Material
        {
            return Material.getMaterial(item.displayItem) ?: return Material.PAPER
        }

        override fun getDescription(player: Player): MutableList<String>
        {
            val desc = mutableListOf<String>()
            desc.add(" ")
            desc.add(Chat.format("&6&l｜ &eItem: &f${item.displayItem}"))
            desc.add(Chat.format("&6&l｜ &eName: &f${item.displayName}"))
            desc.add(Chat.format("&6&l｜ &eSlot: &f${item.menuSlot}"))
            desc.add(Chat.format("&6&l｜ &eActive On: &f${item.activeOn.size} servers"))
            desc.add(Chat.format("&6&l｜ &eHas Parent Category: ${if (item.parentCategory == null) "&cNo" else "&aYes"}"))
            desc.add(
                Chat.format(
                    "&6&l｜ &eParent Category: ${
                        if (item.parentCategory == null) "&cNone" else CoinShopManager.findCategory(
                            item.parentCategory!!
                        )!!.displayName
                    }"
                )
            )
            desc.add(" ")
            desc.add(Chat.format("&aClick To Edit Item"))
            return desc
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
            EditCategoryAttributesMenu(player, item).openMenu()
        }

    }
}
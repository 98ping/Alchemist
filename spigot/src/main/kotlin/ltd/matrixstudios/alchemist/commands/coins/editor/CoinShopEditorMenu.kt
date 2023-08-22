package ltd.matrixstudios.alchemist.commands.coins.editor

import ltd.matrixstudios.alchemist.commands.coins.editor.items.CoinShopItemEditor
import ltd.matrixstudios.alchemist.util.Chat
import ltd.matrixstudios.alchemist.util.menu.Button
import ltd.matrixstudios.alchemist.util.menu.Menu
import ltd.matrixstudios.alchemist.util.menu.buttons.SimpleActionButton
import org.bukkit.Material
import org.bukkit.entity.Player

class CoinShopEditorMenu(val player: Player) : Menu(player) {

    init {
        staticSize = 27
        placeholder = true
    }

    override fun getButtons(player: Player): MutableMap<Int, Button> {
        val buttons = mutableMapOf<Int, Button>()

        buttons[12] = SimpleActionButton(Material.CHEST, mutableListOf(
            Chat.format(" "),
            Chat.format("&7Click here to view all possible"),
            Chat.format("&7categories that an item could be"),
            Chat.format("&7placed into"),
            Chat.format(" "),
            Chat.format("&aClick here to explore categories!")
        ), "&b&lCategories", 0)

        buttons[14] = SimpleActionButton(Material.BEACON, mutableListOf(
            Chat.format(" "),
            Chat.format("&7Click here to view all purchasable items"),
            Chat.format("&7that are available on the coin shop"),
            Chat.format(" "),
            Chat.format("&aClick here to explore items!")
        ), "&6&lItems", 0).setBody { player, i, clickType ->
            CoinShopItemEditor(player).updateMenu()
        }

        return buttons
    }

    override fun getTitle(player: Player): String {
        return Chat.format("&7[Editor] &eCoin Shop")
    }
}
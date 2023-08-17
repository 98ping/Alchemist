package ltd.matrixstudios.alchemist.commands.admin.panel.menu.editors

import ltd.matrixstudios.alchemist.themes.commands.menu.ThemeSelectMenu
import ltd.matrixstudios.alchemist.util.Chat
import ltd.matrixstudios.alchemist.util.menu.Button
import ltd.matrixstudios.alchemist.util.menu.Menu
import ltd.matrixstudios.alchemist.util.menu.buttons.SimpleActionButton
import org.bukkit.Material
import org.bukkit.entity.Player

class AdminPanelEditorMenu(val player: Player) : Menu(player) {

    init {
        staticSize = 27
        placeholder = true
    }

    override fun getButtons(player: Player): MutableMap<Int, Button> {
        val buttons = mutableMapOf<Int, Button>()

        buttons[13] = SimpleActionButton(Material.PAINTING, mutableListOf(
            Chat.format(" "),
            Chat.format("&7Select from a list of possible"),
            Chat.format("&7themes that you may want your server"),
            Chat.format("&7to look like. We currently support:"),
            Chat.format(" "),
            Chat.format("&b&l｜ &fHydrogen"),
            Chat.format("&a&l｜ &fmCore"),
            Chat.format("&6&l｜ &fXeNitrogen"),
            Chat.format("&e&l｜ &fNeutron"),
            Chat.format(" "),
            Chat.format("&a&lClick To View All Themes")
        ), "&e&lThemes", 0).setBody { player, i, clickType ->
            ThemeSelectMenu(player).openMenu()
        }

        buttons[14] = SimpleActionButton(Material.BREWING_STAND, mutableListOf(
            Chat.format(" "),
            Chat.format("&7Show which Alchemist modules you have"),
            Chat.format("&cdisabled &7and which ones you have"),
            Chat.format("&aenabled"),
            Chat.format(" "),
            Chat.format("&a&lClick To View All Modules")
        ), "&c&lModules", 0).setBody { player, i, clickType ->
            ModuleEditorMenu(player).openMenu()
        }

        return buttons
    }

    override fun getTitle(player: Player): String {
        return Chat.format("&aEditor Menu")
    }
}
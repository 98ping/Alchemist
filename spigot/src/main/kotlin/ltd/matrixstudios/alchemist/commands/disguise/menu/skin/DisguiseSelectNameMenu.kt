package ltd.matrixstudios.alchemist.commands.disguise.menu.skin

import ltd.matrixstudios.alchemist.util.Chat
import ltd.matrixstudios.alchemist.util.menu.Button
import ltd.matrixstudios.alchemist.util.menu.Menu
import ltd.matrixstudios.alchemist.util.menu.buttons.SimpleActionButton
import org.bukkit.Material
import org.bukkit.entity.Player

class DisguiseSelectNameMenu(val player: Player) : Menu(player)
{
    init
    {
        staticSize = 9
        placeholder = true
    }

    override fun getButtons(player: Player): MutableMap<Int, Button>
    {
        val buttons = mutableMapOf<Int, Button>()

        buttons[2] = SimpleActionButton(
            Material.NAME_TAG,
            mutableListOf(
                " ",
                Chat.format("&7Click to be randomly"),
                Chat.format("&7given a username from a list"),
                Chat.format("&7of uncommon names."),
                " ",
                Chat.format("&aClick to use a random name!")
            ),
            Chat.format("&cUse Random Name"),
            0
        ).setBody { player, i, clickType ->

        }

        buttons[4] = SimpleActionButton(
            Material.SIGN,
            mutableListOf(
                " ",
                Chat.format("&7Click to type in a custom"),
                Chat.format("&7username that you will use for your"),
                Chat.format("&7disguise."),
                " ",
                Chat.format("&aClick to use a custom name!")
            ),
            Chat.format("&9Use Custom Name"),
            0
        ).setBody { player, i, clickType ->

        }

        buttons[6] = SimpleActionButton(
            Material.PAINTING,
            mutableListOf(
                " ",
                Chat.format("&7Click to be randomly"),
                Chat.format("&7given a popular username."),
                " ",
                Chat.format("&aClick to use a random name!")
            ),
            Chat.format("&aUse Popular Name"),
            0
        ).setBody { player, i, clickType ->

        }

        return buttons
    }

    override fun getTitle(player: Player): String
    {
        return "Select a Name!"
    }
}
package ltd.matrixstudios.alchemist.essentials.messages.menu

import ltd.matrixstudios.alchemist.essentials.messages.MessageHandler
import ltd.matrixstudios.alchemist.essentials.messages.menu.sub.IgnoredPlayersMenu
import ltd.matrixstudios.alchemist.util.Chat
import ltd.matrixstudios.alchemist.util.menu.Button
import ltd.matrixstudios.alchemist.util.menu.Menu
import ltd.matrixstudios.alchemist.util.menu.buttons.SimpleActionButton
import org.bukkit.Material
import org.bukkit.entity.Player

class MessageSettingsMenu(val player: Player) : Menu(player) {

    init {
        staticSize = 27
        placeholder = true
    }

    override fun getButtons(player: Player): MutableMap<Int, Button> {
        val buttons = mutableMapOf<Int, Button>()

        buttons[13] = SimpleActionButton(Material.REDSTONE_BLOCK, mutableListOf(
            " ",
            Chat.format("&7Click here to view every player"),
            Chat.format("&7that is currently on your"),
            Chat.format("&cignore list&7."),
            " ",
            Chat.format("&a&lLeft-Click &ato view ignored list"),
            " "
        ), Chat.format("&cIgnored Players"), 0).setBody { player, i, clickType ->
            IgnoredPlayersMenu(player).updateMenu()
        }

        buttons[14] = SimpleActionButton(Material.ANVIL, mutableListOf(
            " ",
            Chat.format("&7Click here to configure your"),
            Chat.format("&7message sounds to your liking."),
            Chat.format("&7You currently have them " + if (MessageHandler.hasSoundsOn(player.uniqueId)) "&aon" else "&coff"),
            " ",
            Chat.format("&a&lLeft-Click &ato edit sounds"),
            " "
        ), Chat.format("&eSound Options"), 0).setBody { player, i, clickType ->
            val bool = MessageHandler.hasSoundsOn(player.uniqueId)
            val opp = !bool

            MessageHandler.toggleSounds(opp, player.uniqueId)
            player.sendMessage(Chat.format("&eYou have toggled your message sounds " + if (opp) "&aon" else "&coff"))
            MessageSettingsMenu(player).openMenu()
        }

        buttons[12] = SimpleActionButton(Material.BEACON, mutableListOf(
            " ",
            Chat.format("&7Click here to completely disable"),
            Chat.format("&7your messages. This means that"),
            Chat.format("&7nobody is able to message you at all."),
            Chat.format("&7You currently have them " + if (MessageHandler.hasMessagesOn(player.uniqueId)) "&aon" else "&coff"),
            " ",
            Chat.format("&a&lLeft-Click &ato toggle messages"),
            " "
        ), Chat.format("&aToggle Messages"), 0).setBody { player, i, clickType ->
            val bool = MessageHandler.hasMessagesOn(player.uniqueId)
            val opp = !bool

            MessageHandler.toggleMessages(opp, player.uniqueId)
            player.sendMessage(Chat.format("&eYou have toggled your messages " + if (opp) "&aon" else "&coff"))
            MessageSettingsMenu(player).openMenu()
        }

        return buttons
    }

    override fun getTitle(player: Player): String {
        return "Editing your Settings"
    }
}
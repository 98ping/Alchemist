package ltd.matrixstudios.alchemist.commands.admin.panel.menu.editors

import ltd.matrixstudios.alchemist.AlchemistSpigotPlugin
import ltd.matrixstudios.alchemist.util.Chat
import ltd.matrixstudios.alchemist.util.menu.Button
import ltd.matrixstudios.alchemist.util.menu.Menu
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType

class ModuleEditorMenu(val player: Player) : Menu(player) {

    init {
        staticSize = 36
        placeholder = true
    }

    override fun getButtons(player: Player): MutableMap<Int, Button> {
        val buttons = mutableMapOf<Int, Button>()

        buttons[10] = ModuleButton("modules.ranks", "&d&lRanks", Material.PAINTING, mutableListOf(
            " ",
            Chat.format("&7This module houses every rank and permission"),
            Chat.format("&7a player may have on your server. Ranks are"),
            Chat.format("&7configurable through commands &e/rank &7and &e/rank editor")
        ),0)

        buttons[11] = ModuleButton("modules.punishments", "&6&lPunishments", Material.REDSTONE_ORE, mutableListOf(
            " ",
            Chat.format("&7This module controls punishments on the server."),
            Chat.format("&7This also means it houses punishment history, and"),
            Chat.format("&7join disabling for banned users")
        ),0)

        buttons[12] = ModuleButton("modules.parties", "&b&lParties", Material.ARROW, mutableListOf(
            " ",
            Chat.format("&7This module controls the party function on"),
            Chat.format("&7the server. These parties function similar to larger"),
            Chat.format("&7servers in the way that you can invite to, kick from, and disband"),
            Chat.format("&7your party.")
        ),0)

        buttons[13] = ModuleButton("modules.prefixes", "&e&lPrefixes", Material.NAME_TAG, mutableListOf(
            " ",
            Chat.format("&7This module controls chat tags that"),
            Chat.format("&7appear in front of your name. Players can"),
            Chat.format("&7be granted these tags in order to spice up"),
            Chat.format("&7their in-game profile!")
        ),0)

        buttons[14] = ModuleButton("modules.coins", "&2&lCoins", Material.EMERALD, mutableListOf(
            " ",
            Chat.format("&7This module controls the addition of"),
            Chat.format("&7a new economy called &2coins&7."),
            Chat.format("&7This economy is a new way around the"),
            Chat.format("&aMinecraft &7EULA which is why so many"),
            Chat.format("&7servers switch to this currency.")
        ),0)


        buttons[15] = ModuleButton("modules.filters", "&9&lFilters", Material.HOPPER, mutableListOf(
            " ",
            Chat.format("&7This module controls in-game filters"),
            Chat.format("&7which do not allow for some words to be"),
            Chat.format("&7shown in the public chat."),
            Chat.format("&7This module hooks into the &6&lPunishments"),
            Chat.format("&7module as well.")
        ),0)

        buttons[16] = ModuleButton("modules.queue", "&a&lQueue", Material.BLAZE_POWDER, mutableListOf(
            " ",
            Chat.format("&7This module controls the queue"),
            Chat.format("&7system which slowly filters players"),
            Chat.format("&7into the server based on their rank"),
            Chat.format("&7priority."),
        ),0)

        buttons[19] = ModuleButton("modules.chatcolors", "&3&lChatColors", Material.INK_SACK, mutableListOf(
            " ",
            Chat.format("&7This module controls custom chat colors"),
            Chat.format("&7that players can use to make their"),
            Chat.format("&7messages stand out in the sea of"),
            Chat.format("&7humanity."),
        ),6)

        buttons[20] = ModuleButton("modules.notes", "&5&lNotes", Material.BOOK, mutableListOf(
            " ",
            Chat.format("&7This module controls notes that can"),
            Chat.format("&7be applied to users which inform"),
            Chat.format("&7other staff of infractions that this user"),
            Chat.format("&7may have faced or just general information."),
        ),0)

        buttons[21] = ModuleButton("modules.staffmode", "&c&lStaffMode", Material.COMPASS, mutableListOf(
            " ",
            Chat.format("&7This module controls the staff mode"),
            Chat.format("&7portion of the rank core. Some staff"),
            Chat.format("&7mode features such as the staff welcome"),
            Chat.format("&7message may still be used even if this is off."),
        ),0)

        buttons[22] = ModuleButton("modules.chat", "&4&lChat Moderation", Material.ANVIL, mutableListOf(
            " ",
            Chat.format("&7This module controls chat moderation"),
            Chat.format("&7setting such as &e/slowchat and &e/mutechat."),
        ),0)

        buttons[23] = ModuleButton("modules.vouchers", "&7&lVouchers", Material.DIAMOND_CHESTPLATE, mutableListOf(
            " ",
            Chat.format("&7This module controls vouchers that users"),
            Chat.format("&7can redeem to claim rewards that can be"),
            Chat.format("&7given through a command.")
        ),0)

        buttons[24] = ModuleButton("modules.essentials", "&b&lEssentials", Material.NETHER_STAR, mutableListOf(
            " ",
            Chat.format("&7This module controls essential commands"),
            Chat.format("&7that are very useful for players and staff"),
            Chat.format("&7when interacting with a server environment")
        ),0)

        buttons[25] = ModuleButton("modules.themeCommands", "&c&lThemes", Material.PAINTING, mutableListOf(
            " ",
            Chat.format("&7This module controls if the themes command"),
            Chat.format("&7should be enabled on the server or not"),
        ),0)

        return buttons
    }

    override fun getTitle(player: Player): String {
        return Chat.format("&cModules")
    }

    class ModuleButton(val module: String, val displayName: String, val item: Material, val desc: MutableList<String>, val data: Int) : Button() {

        val enabled = AlchemistSpigotPlugin.instance.config.getBoolean(module)
        override fun getMaterial(player: Player): Material {
            return item
        }

        override fun getDescription(player: Player): MutableList<String>? {
            val originalDesc = desc

            originalDesc.add(" ")
            originalDesc.add(Chat.format("&eCurrently " + if (enabled) "&aturned on" else "&cturned off"))

            return originalDesc
        }

        override fun getDisplayName(player: Player): String? {
            return Chat.format(displayName)
        }

        override fun getData(player: Player): Short {
            return 0
        }

        override fun onClick(player: Player, slot: Int, type: ClickType) {

        }
    }
}
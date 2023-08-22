package ltd.matrixstudios.alchemist.chatcolors.menu

import ltd.matrixstudios.alchemist.api.AlchemistAPI
import ltd.matrixstudios.alchemist.chatcolors.ChatColorLoader
import ltd.matrixstudios.alchemist.models.chatcolor.ChatColor
import ltd.matrixstudios.alchemist.profiles.getProfile
import ltd.matrixstudios.alchemist.service.profiles.ProfileGameService
import ltd.matrixstudios.alchemist.util.Chat
import ltd.matrixstudios.alchemist.util.menu.Button
import ltd.matrixstudios.alchemist.util.menu.buttons.SimpleActionButton
import ltd.matrixstudios.alchemist.util.menu.pagination.PaginatedMenu
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType

class ChatColorMenu(val player: Player) : PaginatedMenu(18, player) {

    override fun getHeaderItems(player: Player): MutableMap<Int, Button> {
        val buttons = mutableMapOf<Int, Button>()

        val profile = player.getProfile() ?: return buttons

        buttons[4] = SimpleActionButton(Material.PAPER, mutableListOf(
            " ",
            Chat.format("&7Click here to reset your current"),
            Chat.format("&7chat color."),
            " ",
            Chat.format("&eYou currently have " + (if (profile.activeColor != null) profile.activeColor!!.chatColor + ChatColorLoader.proper(profile.activeColor!!) else "&fNone") + " &eequipped"),
            " "
        ), Chat.format("&cReset ChatColor"), 0).setBody { player, i, clickType ->
            profile.activeColor = null

            ProfileGameService.save(profile)
            player.sendMessage(Chat.format("&aYou have reset your chat color to normal"))
        }

        return buttons
    }

    override fun getPagesButtons(player: Player): MutableMap<Int, Button> {
        val buttons = mutableMapOf<Int, Button>()
        var index = 0

        for (color in ChatColorLoader.colors.values)
        {
            buttons[index++] = ChatColorButton(color, player)
        }

        return buttons
    }


    override fun getTitle(player: Player): String {
        return "Select a Color"
    }

    class ChatColorButton(val chatColor: ChatColor, val player: Player) : Button()
    {
        override fun getMaterial(player: Player): Material {
            return Material.WOOL
        }

        override fun getDescription(player: Player): MutableList<String>? {
            val desc = mutableListOf<String>()
            desc.add(Chat.format("&6&m------------------"))
            desc.add(Chat.format("&eColor:"))
            desc.add(Chat.format("&e│ &r" + chatColor.chatColor + ChatColorLoader.proper(chatColor)))
            desc.add(" ")
            desc.add(Chat.format("&eExample:"))
            desc.add(Chat.format("&e│ &r" + AlchemistAPI.getRankWithPrefix(player.uniqueId) + "&7: &f" + chatColor.chatColor + "Hi!"))
            desc.add(" ")
            if (player.hasPermission(chatColor.permission))
            {
                desc.add(Chat.format("&aClick to select this color"))
            } else {
                desc.add(Chat.format("&cYou do not own this color"))
            }
            desc.add(Chat.format("&6&m------------------"))

            return desc
        }

        override fun getDisplayName(player: Player): String? {
            return Chat.format(chatColor.chatColor + ChatColorLoader.proper(chatColor))
        }

        override fun getData(player: Player): Short {
            return Chat.getDyeColor(chatColor.chatColor).woolData.toShort()
        }

        override fun onClick(player: Player, slot: Int, type: ClickType) {
            val profile = ProfileGameService.byId(player.uniqueId)

            if (profile == null)
            {
                player.sendMessage(Chat.format("&cNull Profile. Contact an admin"))
                return
            }

            if (player.hasPermission(chatColor.permission))
            {
                profile.activeColor = chatColor

                ProfileGameService.save(profile)
                player.sendMessage(Chat.format("&aUpdated your chat color!"))
            } else {
                player.sendMessage(Chat.format("&cYou do not have permission to use this!"))
            }
        }

    }
}
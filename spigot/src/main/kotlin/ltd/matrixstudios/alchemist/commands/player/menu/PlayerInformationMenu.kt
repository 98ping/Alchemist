package ltd.matrixstudios.alchemist.commands.player.menu

import ltd.matrixstudios.alchemist.api.AlchemistAPI
import ltd.matrixstudios.alchemist.commands.alts.menu.AltsMenu
import ltd.matrixstudios.alchemist.commands.grants.menu.grants.GrantsMenu
import ltd.matrixstudios.alchemist.commands.grants.menu.grants.filter.GrantFilter
import ltd.matrixstudios.alchemist.commands.notes.menu.PlayerNotesMenu
import ltd.matrixstudios.alchemist.commands.punishments.menu.HistoryMenu
import ltd.matrixstudios.alchemist.commands.punishments.menu.executed.ExecutedPunishmentHistoryMenu
import ltd.matrixstudios.alchemist.commands.tags.grants.menu.grants.TagGrantsMenu
import ltd.matrixstudios.alchemist.friends.filter.FriendFilter
import ltd.matrixstudios.alchemist.friends.menus.FriendsMenu
import ltd.matrixstudios.alchemist.models.profile.GameProfile
import ltd.matrixstudios.alchemist.serialize.Serializers
import ltd.matrixstudios.alchemist.service.expirable.RankGrantService
import ltd.matrixstudios.alchemist.util.Chat
import ltd.matrixstudios.alchemist.util.menu.Button
import ltd.matrixstudios.alchemist.util.menu.Menu
import ltd.matrixstudios.alchemist.util.menu.buttons.PlaceholderButton
import ltd.matrixstudios.alchemist.util.menu.buttons.SimpleActionButton
import ltd.matrixstudios.alchemist.util.menu.buttons.SkullButton
import org.bukkit.Material
import org.bukkit.entity.Player

/**
 * Class created on 6/4/2023

 * @author 98ping
 * @project Alchemist
 * @website https://solo.to/redis
 */
class PlayerInformationMenu(val player: Player, val target: GameProfile) : Menu(player) {

    override fun size(buttons: Map<Int, Button>): Int {
        return 54
    }
    override fun getButtons(player: Player): MutableMap<Int, Button> {
        val buttons = mutableMapOf<Int, Button>()
        for (int in 0 until 54)
        {
            buttons[int] = PlaceholderButton(Material.STAINED_GLASS_PANE, mutableListOf(), "", 7)
        }

        buttons[4] = PlaceholderButton(Material.SKULL_ITEM, mutableListOf(), Chat.format(AlchemistAPI.getRankDisplay(target.uuid) + "'s &7Profile View"), 0)

        buttons[19] = SimpleActionButton(Material.BEACON, mutableListOf(
            " ",
            Chat.format("&7Click this button to view"),
            Chat.format("&7the staff history of this player."),
            Chat.format("&7If they are not staff then nothing"),
            Chat.format("&7will appear here!"),
            " "
            ), "&6Staff History", 0).setBody {
                player, i, clickType ->  ExecutedPunishmentHistoryMenu(player, target).openMenu()
        }

        buttons[21] = SimpleActionButton(Material.REDSTONE, mutableListOf(
            " ",
            Chat.format("&7Click this button to view"),
            Chat.format("&7the punishment history of"),
            Chat.format("&7this user!"),
            " "
        ), "&6Punishment History", 0).setBody {
                player, i, clickType ->  HistoryMenu(target, player).openMenu()
        }

        buttons[23] = SimpleActionButton(Material.ANVIL, mutableListOf(
            " ",
            Chat.format("&7Click this button to view"),
            Chat.format("&7every alternate account of"),
            Chat.format("&7this user!"),
            " "
        ), "&6Alternate Accounts", 0).setBody {
                player, i, clickType ->  AltsMenu(player, target, target.getAltAccounts()).updateMenu()
        }

        buttons[25] = SimpleActionButton(Material.WOOL, mutableListOf(
            " ",
            Chat.format("&7Click this button to view"),
            Chat.format("&7the rank grants of this"),
            Chat.format("&7user!"),
            " "
        ), "&6Rank Grants", 13).setBody {
                player, i, clickType ->  GrantsMenu(player, target, RankGrantService.getFromCache(target.uuid).toMutableList(), GrantFilter.ALL).updateMenu()
        }

        buttons[37] = SimpleActionButton(Material.PAPER, mutableListOf(
            " ",
            Chat.format("&7Click this button to view"),
            Chat.format("&7the notes of this user"),
            " "
        ), "&6Notes", 0).setBody {
                player, i, clickType ->  PlayerNotesMenu(player, target).updateMenu()
        }

        buttons[39] = SimpleActionButton(Material.REDSTONE_COMPARATOR, mutableListOf(
            " ",
            Chat.format("&7Click this button to view"),
            Chat.format("&7the friends of this user"),
            " "
        ), "&6Friends", 0).setBody {
                player, i, clickType ->  FriendsMenu(player, target, FriendFilter.ALL).updateMenu()
        }

        buttons[41] = SimpleActionButton(Material.NAME_TAG, mutableListOf(
            " ",
            Chat.format("&7Click this button to view"),
            Chat.format("&7the tag grants of this"),
            Chat.format("&7user"),
            " "
        ), "&6Tag Grants", 0).setBody {
                player, i, clickType ->  TagGrantsMenu(player, target).updateMenu()
        }

        buttons[43] = SimpleActionButton(Material.CAULDRON_ITEM, mutableListOf(
            " ",
            Chat.format("&7Click this button to view"),
            Chat.format("&7the raw JSON dump of this"),
            Chat.format("&7user's profile"),
            " "
        ), "&6JSON Dump", 0).setBody { player, i, clickType ->
            val gson = Serializers.GSON.toJson(target)

            player.closeInventory()
            player.sendMessage(gson)
        }


        return buttons
    }

    override fun getTitle(player: Player): String {
        return Chat.format("&7Viewing: &r" + AlchemistAPI.getRankDisplay(target.uuid))
    }
}
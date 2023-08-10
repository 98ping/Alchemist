package ltd.matrixstudios.alchemist.friends.menus


import ltd.matrixstudios.alchemist.friends.filter.FriendFilter
import ltd.matrixstudios.alchemist.models.profile.GameProfile
import ltd.matrixstudios.alchemist.service.profiles.ProfileGameService
import ltd.matrixstudios.alchemist.util.Chat
import ltd.matrixstudios.alchemist.util.InputPrompt
import ltd.matrixstudios.alchemist.util.TimeUtil
import ltd.matrixstudios.alchemist.util.menu.Button
import ltd.matrixstudios.alchemist.util.menu.Menu
import ltd.matrixstudios.alchemist.util.menu.buttons.PlaceholderButton
import ltd.matrixstudios.alchemist.util.menu.buttons.SimpleActionButton
import org.bukkit.Material
import org.bukkit.entity.Player
import java.util.concurrent.CompletableFuture

class FriendsMenu(val player: Player, val profile: GameProfile) : Menu(player) {

    init {
        staticSize = 27
        placeholder = true
    }
    override fun getButtons(player: Player): MutableMap<Int, Button> {
        val buttons = mutableMapOf<Int, Button>()

        buttons[12] = SimpleActionButton(Material.BOOK, mutableListOf(
            Chat.format("&7Send out a friend request"),
            Chat.format("&7to a player on the network")
        ), Chat.format("&bSend Friend Request"), 0).setBody { player, i, clickType ->
            
        }

        buttons[13] = PlaceholderButton(Material.EMERALD, mutableListOf(
            Chat.format("&7Rank: ${profile.getCurrentRank().color + profile.getCurrentRank().displayName}"),
            Chat.format("&7Total Friends: &f" + profile.friends.size),
            Chat.format("&7Friend Requests: &f" + profile.friendInvites.size),
        ), Chat.format("&aYour Profile"), 0)

        buttons[14] = SimpleActionButton(Material.BEACON, mutableListOf(
            Chat.format("&7View every friend that you"),
            Chat.format("&7have added!")
        ), Chat.format("&6View Friends"), 0).setBody { player, i, clickType ->
            FriendsListMenu(player, profile, FriendFilter.ALL).updateMenu()
        }

        return buttons
    }

    override fun getTitle(player: Player): String {
        return "Configure Friends"
    }
}
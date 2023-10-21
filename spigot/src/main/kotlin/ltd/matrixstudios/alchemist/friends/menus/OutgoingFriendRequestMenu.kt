package ltd.matrixstudios.alchemist.friends.menus

import ltd.matrixstudios.alchemist.models.profile.GameProfile
import ltd.matrixstudios.alchemist.profiles.getProfile
import ltd.matrixstudios.alchemist.service.profiles.ProfileGameService
import ltd.matrixstudios.alchemist.util.Chat
import ltd.matrixstudios.alchemist.util.TimeUtil
import ltd.matrixstudios.alchemist.util.items.ItemBuilder
import ltd.matrixstudios.alchemist.util.menu.Button
import ltd.matrixstudios.alchemist.util.menu.type.BorderedPaginatedMenu
import ltd.matrixstudios.alchemist.util.skull.SkullUtil
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import org.bukkit.inventory.ItemStack
import java.util.*

class OutgoingFriendRequestMenu(val player: Player, val profile: GameProfile, val toShow: MutableList<GameProfile>) :
    BorderedPaginatedMenu(player)
{
    override fun getPagesButtons(player: Player): MutableMap<Int, Button>
    {
        val buttons = mutableMapOf<Int, Button>()
        var i = 0

        for (profile in toShow)
        {
            buttons[i++] = OutFriendRequestButton(profile.uuid)
        }

        return buttons
    }

    override fun getTitle(player: Player): String
    {
        return "All Outgoing Requests"
    }

    class OutFriendRequestButton(val uuid: UUID) : Button()
    {
        val profile = ProfileGameService.byId(uuid)

        override fun getButtonItem(player: Player): ItemStack
        {
            if (profile != null)
            {
                return ItemBuilder.copyOf(
                    SkullUtil.generate(
                        profile.username,
                        Chat.format(profile.getCurrentRank().prefix + profile.getRankDisplay())
                    )
                ).setLore(getDescription(player).toList()).build()
            }

            return ItemBuilder.of(Material.REDSTONE_BLOCK).name(Chat.format("&cProfile Not Found"))
                .setLore(getDescription(player).toList()).build()
        }

        override fun getMaterial(player: Player): Material
        {
            return Material.DIRT
        }

        override fun getDescription(player: Player): MutableList<String>
        {
            val desc = mutableListOf<String>()
            if (profile != null)
            {
                desc.add(
                    Chat.format(
                        "&7Last Seen: &f" + if (profile.isOnline()) "&aOnline" else TimeUtil.formatDuration(
                            System.currentTimeMillis().minus(profile.lastSeenAt)
                        ) + " ago"
                    )
                )
                desc.add(Chat.format("&7Rank: &f" + profile.getCurrentRank().color + profile.getCurrentRank().displayName))
            } else
            {
                desc.add(Chat.format("&cProfile not found!"))
            }

            return desc
        }

        override fun getDisplayName(player: Player): String
        {
            return "a"
        }

        override fun getData(player: Player): Short
        {
            return 0
        }

        override fun onClick(player: Player, slot: Int, type: ClickType)
        {
            val gameProfile = player.getProfile()

            if (gameProfile == null)
            {
                player.sendMessage(Chat.format("&cYour profile doesn't exist"))
                return
            }

            if (profile == null)
            {
                player.sendMessage(Chat.format("&cOther profile doesn't exist!"))
                return
            }

            if (!profile.friendInvites.contains(gameProfile.uuid))
            {
                player.sendMessage(Chat.format("&cApparently you have never sent a friend request to this player"))
                return
            }

            profile.friendInvites.remove(gameProfile.uuid)
            ProfileGameService.save(profile)

            player.closeInventory()
            player.sendMessage(Chat.format("&aYou revoked " + profile.getRankDisplay() + "&a's outgoing friend request!"))

            ProfileGameService.getAllOutgoingFriendRequests(profile).thenApply {
                OutgoingFriendRequestMenu(player, profile, it).updateMenu()
            }
        }

    }
}
package ltd.matrixstudios.alchemist.commands.rank.menu.scan

import ltd.matrixstudios.alchemist.api.AlchemistAPI
import ltd.matrixstudios.alchemist.models.profile.GameProfile
import ltd.matrixstudios.alchemist.models.ranks.Rank
import ltd.matrixstudios.alchemist.util.Chat
import ltd.matrixstudios.alchemist.util.items.ItemBuilder
import ltd.matrixstudios.alchemist.util.menu.Button
import ltd.matrixstudios.alchemist.util.menu.type.BorderedPaginatedMenu
import ltd.matrixstudios.alchemist.util.skull.SkullUtil
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import org.bukkit.inventory.ItemStack
import java.util.*

class RankScanUserMenu(val player: Player, val rankScan: Collection<GameProfile>, val rank: Rank) :
    BorderedPaginatedMenu(player)
{
    override fun getPagesButtons(player: Player): MutableMap<Int, Button>
    {
        val buttons = mutableMapOf<Int, Button>()
        var i = 0

        for (profile in rankScan)
        {
            buttons[i++] = ProfileButton(profile)
        }

        return buttons
    }

    override fun getTitle(player: Player): String
    {
        return "Viewing all players"
    }

    class ProfileButton(val profile: GameProfile) : Button()
    {
        override fun getMaterial(player: Player): Material
        {
            return Material.DIRT
        }

        override fun getDescription(player: Player): MutableList<String>
        {
            val desc = mutableListOf<String>()
            desc.add(Chat.format("&7&m-------------------"))
            desc.add(Chat.format("&eUsername: &f${profile.username}"))
            desc.add(Chat.format("&eHighest Grant: &f${profile.getCurrentRank().color + profile.getCurrentRank().displayName}"))
            desc.add(Chat.format("&eLast Seen: &f${Date(profile.lastSeenAt)}"))
            desc.add(Chat.format("&7&m-------------------"))
            return desc
        }

        override fun getDisplayName(player: Player): String
        {
            return ""
        }

        override fun getButtonItem(player: Player): ItemStack
        {
            val skull = SkullUtil.generate(profile.username, "")

            return ItemBuilder.copyOf(skull).setLore(getDescription(player).toList())
                .name(Chat.format(AlchemistAPI.getRankDisplay(profile.uuid))).build()
        }

        override fun getData(player: Player): Short
        {
            return 0
        }

        override fun onClick(player: Player, slot: Int, type: ClickType)
        {
        }
    }
}
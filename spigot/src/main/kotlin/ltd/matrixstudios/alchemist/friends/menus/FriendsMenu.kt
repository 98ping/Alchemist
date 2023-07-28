package ltd.matrixstudios.alchemist.friends.menus

import ltd.matrixstudios.alchemist.api.AlchemistAPI
import ltd.matrixstudios.alchemist.friends.filter.FriendFilter
import ltd.matrixstudios.alchemist.friends.filter.button.FilterButton
import ltd.matrixstudios.alchemist.models.profile.GameProfile
import ltd.matrixstudios.alchemist.util.Chat
import ltd.matrixstudios.alchemist.util.TimeUtil
import ltd.matrixstudios.alchemist.util.items.ItemBuilder
import ltd.matrixstudios.alchemist.util.menu.Button
import ltd.matrixstudios.alchemist.util.menu.pagination.PaginatedMenu
import ltd.matrixstudios.alchemist.util.skull.SkullUtil
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import org.bukkit.inventory.ItemStack
import java.util.concurrent.TimeUnit

class FriendsMenu(val player: Player, val profile: GameProfile, val filter: FriendFilter) : PaginatedMenu(18, player) {

    override fun getHeaderItems(player: Player): MutableMap<Int, Button> {
        val buttons = mutableMapOf<Int, Button>()

        buttons[4] = FilterButton(filter, profile)
        return buttons
    }

    override fun getPagesButtons(player: Player): MutableMap<Int, Button> {
        val buttons = mutableMapOf<Int, Button>()
        var index = 0

        val filtered = get(profile, filter)

        for (item in filtered)
        {
            buttons[index++] = FriendButton(item)
        }

        return buttons

    }

    override fun getTitle(player: Player): String {
        return "Your Friends!"
    }

    class FriendButton(val profile: GameProfile) : Button()
    {
        override fun getMaterial(player: Player): Material {
            return Material.DIRT
        }

        override fun getDescription(player: Player): MutableList<String>? {
            val desc = mutableListOf<String>()
            val rank = AlchemistAPI.findRank(profile.uuid)

            desc.add(Chat.format("&7&m-------------------"))
            desc.add(Chat.format("&eRank: &f" + rank.color + rank.displayName))
            desc.add(Chat.format("&eTotal Friends: &f" + profile.friends.size))
            if (profile.isOnline()) {
                desc.add(Chat.format("&ePlaying: &f" + profile.metadata.get("server").asString))
            }
            desc.add(" ")
            if (profile.isOnline()) {
                desc.add(Chat.format("&aCurrently Online"))
            } else {
                desc.add(Chat.format("&cCurrently Offline"))
                desc.add(Chat.format("&7Offline For " + TimeUtil.formatDuration(System.currentTimeMillis().minus(profile.lastSeenAt))))
            }
            desc.add(Chat.format("&7&m-------------------"))

            return desc
        }

        override fun getDisplayName(player: Player): String? {
            return "bing"
        }

        override fun getButtonItem(player: Player): ItemStack? {
            val name = Chat.format(AlchemistAPI.getRankDisplay(profile.uuid))
            val desc = getDescription(player)!!
            val skullItem = SkullUtil.generate(profile.username, name)

            return ItemBuilder.copyOf(skullItem).setLore(desc.toList()).name(name).build()
        }

        override fun getData(player: Player): Short {
            return 0
        }

        override fun onClick(player: Player, slot: Int, type: ClickType) {

        }

    }

    fun get(profile: GameProfile, filter: FriendFilter) : List<GameProfile>
    {
        if (filter == FriendFilter.ALL) return profile.supplyFriendsAsProfiles().get()

        val baseList = profile.supplyFriendsAsProfiles().get()

        //statuses
        if (filter == FriendFilter.ONLINE)
        {
            return baseList.filter { it.isOnline() }
        } else if (filter == FriendFilter.OFFLINE)
        {
            return baseList.filter { !it.isOnline() }
        }

        //attributes
        if (filter == FriendFilter.YOUR_SERVER)
        {
            return baseList.filter { it.metadata.get("server").asString == profile.metadata.get("server").asString }
        } else if (filter == FriendFilter.RECENTLY_JOINED)
        {
            return baseList.filter { System.currentTimeMillis().minus(it.lastSeenAt) <= TimeUnit.MINUTES.toMillis(30L) }
        }

        return baseList

    }
}
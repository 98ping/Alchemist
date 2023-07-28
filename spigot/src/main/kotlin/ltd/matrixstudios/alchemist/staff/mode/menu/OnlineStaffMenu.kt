package ltd.matrixstudios.alchemist.staff.mode.menu

import ltd.matrixstudios.alchemist.api.AlchemistAPI
import ltd.matrixstudios.alchemist.service.profiles.ProfileGameService
import ltd.matrixstudios.alchemist.util.Chat
import ltd.matrixstudios.alchemist.util.TimeUtil
import ltd.matrixstudios.alchemist.util.items.ItemBuilder
import ltd.matrixstudios.alchemist.util.menu.Button
import ltd.matrixstudios.alchemist.util.menu.pagination.PaginatedMenu
import ltd.matrixstudios.alchemist.util.skull.SkullUtil
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import org.bukkit.inventory.ItemStack

class OnlineStaffMenu(val player: Player) : PaginatedMenu(18, player) {
    override fun getPagesButtons(player: Player): MutableMap<Int, Button> {
        val buttons = hashMapOf<Int, Button>()
        var index = 0

        for (p in Bukkit.getOnlinePlayers())
        {
            if (p.hasPermission("alchemist.staff"))
            {
                buttons[index++] = StaffDetailButton(p)
            }
        }

        return buttons
    }

    override fun getTitle(player: Player): String {
        return "All Online Staff"
    }


    class StaffDetailButton(val player: Player) : Button()
    {
        override fun getMaterial(player: Player): Material {
            return Material.DIRT
        }

        override fun getDescription(player: Player): MutableList<String> {
            val desc = mutableListOf<String>()
            val profile = ProfileGameService.byId(this.player.uniqueId)
            desc.add(Chat.format("&7&m-------------------"))
            desc.add(Chat.format("&eModMode: &aYes"))
            desc.add(Chat.format("&eVanished: &f" + if (this.player.hasMetadata("vanish")) "&aYes" else "&cNo"))
            desc.add(" ")
            desc.add(Chat.format("&eOnline For: &f" + TimeUtil.formatDuration(System.currentTimeMillis().minus(profile!!.lastSeenAt))))
            desc.add(" ")
            desc.add(Chat.format("&7Click to teleport!"))
            desc.add(Chat.format("&7&m-------------------"))

            return desc
        }

        override fun getDisplayName(player: Player): String? {
            return ""
        }

        override fun getButtonItem(player: Player): ItemStack? {
            val skull = SkullUtil.generate(this.player.name, "")

            return ItemBuilder.copyOf(skull).setLore(getDescription(player).toList()).name(Chat.format(AlchemistAPI.getRankDisplay(this.player.uniqueId))).build()
        }

        override fun getData(player: Player): Short {
            return 0
        }

        override fun onClick(player: Player, slot: Int, type: ClickType) {
            if (this.player.isOnline)
            {
                player.teleport(this.player)
            }
        }


    }
}
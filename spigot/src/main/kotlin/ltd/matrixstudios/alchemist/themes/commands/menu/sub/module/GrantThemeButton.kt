package ltd.matrixstudios.alchemist.themes.commands.menu.sub.module

import ltd.matrixstudios.alchemist.service.profiles.ProfileGameService
import ltd.matrixstudios.alchemist.service.ranks.RankService
import ltd.matrixstudios.alchemist.themes.Theme
import ltd.matrixstudios.alchemist.util.menu.Button
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType

class GrantThemeButton(val theme: Theme, val player: Player) : Button() {

    val rank = RankService.findFirstAvailableDefaultRank()!!

    override fun getMaterial(player: Player): Material {
        return Material.WOOL
    }

    override fun getDescription(player: Player): MutableList<String>? {
        return theme.getGrantLore(player, ProfileGameService.byId(player.uniqueId)!!, rank)
    }

    override fun getDisplayName(player: Player): String? {
        return theme.getGrantDisplayName(player, rank)
    }

    override fun getData(player: Player): Short {
        return theme.getGrantData(player, rank)
    }

    override fun onClick(player: Player, slot: Int, type: ClickType) {
        return
    }
}
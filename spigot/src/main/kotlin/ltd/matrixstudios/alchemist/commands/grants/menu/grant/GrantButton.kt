package ltd.matrixstudios.alchemist.commands.grants.menu.grant

import ltd.matrixstudios.alchemist.models.profile.GameProfile
import ltd.matrixstudios.alchemist.models.ranks.Rank
import ltd.matrixstudios.alchemist.profiles.getProfile
import ltd.matrixstudios.alchemist.themes.ThemeLoader
import ltd.matrixstudios.alchemist.util.Chat
import ltd.matrixstudios.alchemist.util.menu.Button
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType


class GrantButton(var rank: Rank, var gameProfile: GameProfile) : Button() {


    override fun getMaterial(player: Player): Material {
        val curr = player.getProfile()?.getCurrentRank()

        if (rank.weight > (curr?.weight ?: 0)) {
            return Material.OBSIDIAN
        }

        return Material.WOOL
    }

    override fun getDescription(player: Player): MutableList<String>? {
        return ThemeLoader.defaultTheme.getGrantLore(player, gameProfile, rank)
    }

    override fun getDisplayName(player: Player): String? {
        return ThemeLoader.defaultTheme.getGrantDisplayName(player, rank)
    }

    override fun getData(player: Player): Short {
        val curr = player.getProfile()?.getCurrentRank()

        if (rank.weight > (curr?.weight ?: 0)) {
            return 0
        }

        return ThemeLoader.defaultTheme.getGrantData(player, rank)
    }

    override fun onClick(player: Player, slot: Int, type: ClickType) {
        val curr = player.getProfile()?.getCurrentRank()

        if (rank.weight > (curr?.weight ?: 0)) {
            player.sendMessage(Chat.format("&cThis rank is too high up on the priority list for you to grant!"))
            return
        }

        DurationMenu(player, rank, gameProfile).openMenu()
    }
}
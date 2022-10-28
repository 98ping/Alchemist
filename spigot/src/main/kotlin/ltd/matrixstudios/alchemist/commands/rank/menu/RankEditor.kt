package ltd.matrixstudios.alchemist.commands.rank.menu

import ltd.matrixstudios.alchemist.models.ranks.Rank
import ltd.matrixstudios.alchemist.service.ranks.RankService
import ltd.matrixstudios.alchemist.util.Chat
import ltd.matrixstudios.alchemist.util.menu.Button
import ltd.matrixstudios.alchemist.util.menu.pagination.PaginatedMenu
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType

class RankEditor(val player: Player) : PaginatedMenu(36, player) {

    override fun getPagesButtons(player: Player): MutableMap<Int, Button> {
        val buttons = mutableMapOf<Int, Button>()
        val ranks = RankService.getRanksInOrder()
        var index = 0

        for (rank in ranks)
        {
            buttons[index++] = RankButton(player, rank)
        }
        return buttons
    }

    override fun getTitle(player: Player): String {
        return "Editing Ranks"
    }

    class RankButton(val player: Player, val rank: Rank) : Button()
    {
        override fun getMaterial(player: Player): Material {
            return Material.INK_SACK
        }

        override fun getDescription(player: Player): MutableList<String>? {
            val desc = mutableListOf<String>()
            desc.add(Chat.format("&6&m---------------------"))
            desc.add(Chat.format("&6Metadata:"))
            desc.add(Chat.format("&e- Prefix: &f" + rank.prefix))
            desc.add(Chat.format("&e- Permissions: &f" + rank.permissions.size))
            desc.add(Chat.format("&e- Weight: &f" + rank.weight))
            desc.add(" ")
            desc.add(Chat.format("&6Looks:"))
            desc.add(Chat.format("&e- Player List: &f" + rank.color + player.name))
            desc.add(Chat.format("&e- Chat Format: &f" + rank.prefix + player.name))
            desc.add(Chat.format("&6&m---------------------"))

            return desc
        }

        override fun getDisplayName(player: Player): String? {
            return Chat.format(rank.color + rank.displayName)
        }

        override fun getData(player: Player): Short {
            return Chat.getDyeColor(rank.color).dyeData.toShort()
        }

        override fun onClick(player: Player, slot: Int, type: ClickType) {
           return
        }


    }
}
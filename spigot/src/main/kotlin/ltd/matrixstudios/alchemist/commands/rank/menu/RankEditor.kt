package ltd.matrixstudios.alchemist.commands.rank.menu

import ltd.matrixstudios.alchemist.api.AlchemistAPI
import ltd.matrixstudios.alchemist.commands.rank.menu.sub.RankEditPropertiesMenu
import ltd.matrixstudios.alchemist.models.ranks.Rank
import ltd.matrixstudios.alchemist.service.ranks.RankService
import ltd.matrixstudios.alchemist.util.Chat
import ltd.matrixstudios.alchemist.util.menu.Button
import ltd.matrixstudios.alchemist.util.menu.pagination.PaginatedMenu
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType

class RankEditor(val player: Player) : PaginatedMenu(36, player)
{

    override fun getPagesButtons(player: Player): MutableMap<Int, Button>
    {
        val buttons = mutableMapOf<Int, Button>()
        val ranks = RankService.getAllRanksInOrder()
        var index = 0

        for (rank in ranks)
        {
            buttons[index++] = RankButton(player, rank)
        }
        return buttons
    }

    override fun getTitle(player: Player): String
    {
        return Chat.format("&7[Editor] &eRanks")
    }

    class RankButton(val player: Player, val rank: Rank) : Button()
    {
        override fun getMaterial(player: Player): Material
        {
            return Material.INK_SACK
        }

        override fun getDescription(player: Player): MutableList<String>
        {
            val desc = mutableListOf<String>()
            desc.add(Chat.format("&7&m---------------------"))
            desc.add(Chat.format("&6Metadata:"))
            desc.add(Chat.format("&6&l｜ &fPrefix: &e" + rank.prefix))
            desc.add(Chat.format("&6&l｜ &fWeight: &e" + rank.weight))
            desc.add(Chat.format("&6&l｜ &fColor: &r${rank.color}This"))
            desc.add(Chat.format("&6&l｜ &fWool Color: &e${if (rank.woolColor == null) "&cNone" else "${rank.woolColor}This"}"))
            desc.add(" ")
            desc.add(Chat.format("&6Attachments:"))
            desc.add(Chat.format("&6&l｜ &fPermissions: &e" + rank.permissions.size))
            desc.add(Chat.format("&6&l｜ &fParents: &e" + rank.parents.size))
            desc.add(" ")
            desc.add(Chat.format("&6Looks:"))
            desc.add(Chat.format("&6&l｜ &fPlayer List: &f" + rank.color + player.name))
            desc.add(Chat.format("&6&l｜ &fChat Format: &f" + rank.prefix + rank.color + player.name))
            desc.add(" ")
            desc.add(Chat.format("&a&lLeft-Click to edit this rank!"))
            desc.add(Chat.format("&7&m---------------------"))

            return desc
        }

        override fun getDisplayName(player: Player): String
        {
            return Chat.format(rank.color + rank.displayName)
        }

        override fun getData(player: Player): Short
        {
            if (rank.woolColor != null)
            {
                return AlchemistAPI.getWoolColor(rank.woolColor!!).dyeData.toShort()
            }
            return AlchemistAPI.getWoolColor(rank.color).dyeData.toShort()
        }

        override fun onClick(player: Player, slot: Int, type: ClickType)
        {
            RankEditPropertiesMenu(player, rank).openMenu()
        }


    }
}
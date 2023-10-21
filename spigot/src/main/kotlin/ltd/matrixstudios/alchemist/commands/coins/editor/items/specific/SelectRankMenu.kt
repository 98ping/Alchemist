package ltd.matrixstudios.alchemist.commands.coins.editor.items.specific

import ltd.matrixstudios.alchemist.api.AlchemistAPI
import ltd.matrixstudios.alchemist.commands.coins.CoinShopManager
import ltd.matrixstudios.alchemist.commands.coins.editor.items.CoinShopItemAttributeEditor
import ltd.matrixstudios.alchemist.commands.coins.item.CoinShopItem
import ltd.matrixstudios.alchemist.models.ranks.Rank
import ltd.matrixstudios.alchemist.service.ranks.RankService
import ltd.matrixstudios.alchemist.util.Chat
import ltd.matrixstudios.alchemist.util.menu.Button
import ltd.matrixstudios.alchemist.util.menu.pagination.PaginatedMenu
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType

class SelectRankMenu(val player: Player, val item: CoinShopItem) : PaginatedMenu(18, player)
{
    override fun getPagesButtons(player: Player): MutableMap<Int, Button>
    {
        val buttons = mutableMapOf<Int, Button>()
        var i = 0

        for (rank in RankService.getAllRanksInOrder())
        {
            buttons[i++] = RankButton(item, rank)
        }

        return buttons
    }

    override fun getTitle(player: Player): String
    {
        return "Select a Rank"
    }

    class RankButton(val item: CoinShopItem, val rank: Rank) : Button()
    {
        override fun getMaterial(player: Player): Material
        {
            return Material.WOOL
        }

        override fun getDescription(player: Player): MutableList<String>
        {
            return mutableListOf()
        }

        override fun getDisplayName(player: Player): String
        {
            return Chat.format(rank.color + rank.displayName)
        }

        override fun getData(player: Player): Short
        {
            return AlchemistAPI.getWoolColor(rank.color).woolData.toShort()
        }

        override fun onClick(player: Player, slot: Int, type: ClickType)
        {
            item.requiredRank = rank.id
            CoinShopManager.saveItem(item)
            player.sendMessage(Chat.format("&aSet the required rank of ${item.displayName} &ato &f" + rank.color + rank.displayName))
            player.closeInventory()
            CoinShopItemAttributeEditor(player, item).openMenu()
        }
    }
}
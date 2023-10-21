package ltd.matrixstudios.alchemist.commands.rank.menu

import ltd.matrixstudios.alchemist.commands.rank.menu.scan.RankScanUserMenu
import ltd.matrixstudios.alchemist.models.ranks.Rank
import ltd.matrixstudios.alchemist.service.ranks.RankService
import ltd.matrixstudios.alchemist.util.Chat
import ltd.matrixstudios.alchemist.util.items.ItemBuilder
import ltd.matrixstudios.alchemist.util.menu.Button
import ltd.matrixstudios.alchemist.util.menu.pagination.PaginatedMenu
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import org.bukkit.inventory.ItemStack

class RankScanMenu(val player: Player) : PaginatedMenu(27, player)
{
    override fun getPagesButtons(player: Player): MutableMap<Int, Button>
    {
        val buttons = mutableMapOf<Int, Button>()
        var i = 0

        for (rank in RankService.getAllRanksInOrder())
        {
            buttons[i++] = RankScanButton(rank)
        }

        return buttons
    }

    override fun getTitle(player: Player): String
    {
        return "Choose a Rank to Scan"
    }

    override fun getButtonPositions(): List<Int>
    {
        return listOf(
            1, 3, 5, 7,
            10, 12, 14, 16,
            19, 21, 23, 25,
            28, 30, 32, 34
        )
    }

    override fun getHeaderItems(player: Player): MutableMap<Int, Button>
    {
        return mutableMapOf(
            2 to Button.placeholder(),
            4 to Button.placeholder(),
            6 to Button.placeholder(),
            9 to Button.placeholder(),
            11 to Button.placeholder(),
            13 to Button.placeholder(),
            15 to Button.placeholder(),
            17 to Button.placeholder(),
            18 to Button.placeholder(),
            20 to Button.placeholder(),
            22 to Button.placeholder(),
            24 to Button.placeholder(),
            26 to Button.placeholder(),
            27 to Button.placeholder(),
            29 to Button.placeholder(),
            31 to Button.placeholder(),
            33 to Button.placeholder(),
            35 to Button.placeholder(),
        )
    }

    override fun getButtonsPerPage(): Int
    {
        return 16
    }

    class RankScanButton(val rank: Rank) : Button()
    {

        override fun getButtonItem(player: Player): ItemStack
        {
            return ItemBuilder.of(getMaterial(player))
                .setLore(getDescription(player))
                .name(getDisplayName(player))
                .color(Chat.getLeatherMetaColor(if (rank.woolColor != null) rank.woolColor!! else rank.color))
                .build()
        }

        override fun getMaterial(player: Player): Material
        {
            return Material.LEATHER_CHESTPLATE
        }

        override fun getDescription(player: Player): MutableList<String>
        {
            val desc = mutableListOf<String>()
            desc.add(" ")
            desc.add("&7Weight: &f${rank.weight}")
            desc.add("&7Staff: &f" + if (rank.staff) "&aYes" else "&cNo")
            desc.add("&7Display Name: &f${rank.displayName}")
            desc.add(" ")

            return desc.map { Chat.format(it) }.toMutableList()
        }

        override fun getDisplayName(player: Player): String
        {
            return Chat.format(rank.color + rank.displayName)
        }

        override fun getData(player: Player): Short
        {
            return 0
        }

        override fun onClick(player: Player, slot: Int, type: ClickType)
        {
            player.closeInventory()
            player.sendMessage(Chat.format("&eLoading the rank scan for ${rank.color + rank.displayName}&e..."))

            RankService.scanRank(rank).thenAccept {
                RankScanUserMenu(player, it, rank).updateMenu()
            }
        }

    }
}
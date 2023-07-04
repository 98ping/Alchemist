package ltd.matrixstudios.alchemist.commands.rank.menu

import ltd.matrixstudios.alchemist.api.AlchemistAPI
import ltd.matrixstudios.alchemist.commands.punishments.menu.impl.proof.sub.ProofInputLinkMenu
import ltd.matrixstudios.alchemist.commands.rank.menu.filter.RankListFilter
import ltd.matrixstudios.alchemist.friends.menus.FriendsMenu
import ltd.matrixstudios.alchemist.models.grant.types.proof.ProofEntry
import ltd.matrixstudios.alchemist.models.ranks.Rank
import ltd.matrixstudios.alchemist.service.ranks.RankService
import ltd.matrixstudios.alchemist.util.Chat
import ltd.matrixstudios.alchemist.util.InputPrompt
import ltd.matrixstudios.alchemist.util.menu.Button
import ltd.matrixstudios.alchemist.util.menu.buttons.SkullButton
import ltd.matrixstudios.alchemist.util.menu.pagination.PaginatedMenu
import ltd.matrixstudios.alchemist.util.skull.SkullUtil
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import org.bukkit.inventory.ItemStack

/**
 * Class created on 6/27/2023

 * @author 98ping
 * @project Alchemist
 * @website https://solo.to/redis
 */
class RankListMenu(val player: Player, val ranks: MutableCollection<Rank>, val rankListFilter: RankListFilter) : PaginatedMenu(36, player) {

    override fun getHeaderItems(player: Player): MutableMap<Int, Button> {
        return mutableMapOf(
            1 to Button.placeholder(),
            2 to Button.placeholder(),
            4 to Button.placeholder(),
            6 to Button.placeholder(),
            7 to Button.placeholder(),
            9 to Button.placeholder(),
            17 to Button.placeholder(),
            18 to Button.placeholder(),
            26 to Button.placeholder(),
            27 to Button.placeholder(),
            35 to Button.placeholder(),
            36 to Button.placeholder(),
            37 to Button.placeholder(),
            38 to Button.placeholder(),
            39 to Button.placeholder(),
            40 to Button.placeholder(),
            41 to Button.placeholder(),
            42 to Button.placeholder(),
            43 to Button.placeholder(),
            44 to Button.placeholder(),
            3 to SkullButton(
                "ewogICJ0aW1lc3RhbXAiIDogMTY4NzkyNjU3ODkzMCwKICAicHJvZmlsZUlkIiA6ICIzOTg5OGFiODFmMjU0NmQxOGIyY2ExMTE1MDRkZGU1MCIsCiAgInByb2ZpbGVOYW1lIiA6ICI4YjJjYTExMTUwNGRkZTUwIiwKICAic2lnbmF0dXJlUmVxdWlyZWQiIDogdHJ1ZSwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlL2M4OTMwY2M1OTQ2OWU2M2Y4MzhjMTJmMjY4ZjMxMjJiMTllZjQxMTcwNmVkMzE5Mzc1YTc4MzI1OTE1OGVlNmYiCiAgICB9CiAgfQp9",
                generateRankListFilterLore(),
                "&eClick to switch &6Rank Filter").setBody { player, i, clickType ->
                val values = RankListFilter.values()
                val index = values.indexOf(rankListFilter)
                val next = (index + 1)
                val limit = values.size - 1

                if (next > limit)
                {
                    RankListMenu(player, getRanksBasedOnFilter(values[0]), values[0]).updateMenu()

                    return@setBody
                }

                RankListMenu(player, getRanksBasedOnFilter(values[next]), values[next]).updateMenu()
            },

            5 to SkullButton(
                "eyJ0aW1lc3RhbXAiOjE1NzEzMTYzMzY1MjgsInByb2ZpbGVJZCI6IjVkZTZlMTg0YWY4ZDQ5OGFiYmRlMDU1ZTUwNjUzMzE2IiwicHJvZmlsZU5hbWUiOiJBc3Nhc2luSmlhbmVyMjUiLCJzaWduYXR1cmVSZXF1aXJlZCI6dHJ1ZSwidGV4dHVyZXMiOnsiU0tJTiI6eyJ1cmwiOiJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlLzlhNmUwYzE2ZGYwMTMzNDE4OGVhNjliNzVjN2M4Y2IxOGVmODZmMjZhMTVjYTk2YTJkYTI1MWVhZGQ5NDU1NTkifX19",
                mutableListOf(" ",
                    "&eClick to query every &6rank",
                    " "),
                "&eQuery &6Ranks").setBody { player, i, clickType ->
                    InputPrompt()
                        .withText(Chat.format("&eType any search query to be shown a list of ranks that match the search"))
                        .acceptInput { s: String ->
                            RankListMenu(player, ranks.filter { it.id.contains(s) }.toMutableList(), rankListFilter).updateMenu()
                        }.start(player)
            }
        )
    }

    fun getRanksBasedOnFilter(filter: RankListFilter) : MutableCollection<Rank> {
        val allRanks = RankService.ranks.values

        return when (filter) {
            RankListFilter.ALL -> allRanks
            RankListFilter.DEFAULT -> allRanks.filter { it.default }.toMutableList()
            RankListFilter.STAFF -> allRanks.filter { it.staff }.toMutableList()
            RankListFilter.HAS_PARENTS -> allRanks.filter { it.parents.isNotEmpty() }.toMutableList()
        }
    }

    override fun getPagesButtons(player: Player): MutableMap<Int, Button> {
        val buttons = mutableMapOf<Int, Button>()
        var i = 0

        for (rank in ranks) {
            buttons[i++] = SkullButton(
                Chat.mapChatColorToSkullTexture(if (rank.woolColor != null) rank.woolColor!! else rank.color),
                generateRankButtonDesc(rank),
                "&r${rank.color}${rank.displayName}"
            ).setBody { player, i, clickType ->
                RankEditor(player).updateMenu()
            }
        }

        return buttons
    }

    fun generateRankListFilterLore() : MutableList<String> {
        val desc = mutableListOf<String>()
        desc.add(" ")
        for (filter in RankListFilter.values())
        {
            if (rankListFilter == filter)
            {
                desc.add(Chat.format("&7- &a" + filter.displayName))
            } else {
                desc.add(Chat.format("&7- &7" + filter.displayName))
            }
        }
        desc.add(" ")
        desc.add(Chat.format("&7Click to move to next filter!"))
        desc.add(" ")

        return desc
    }

    fun generateRankButtonDesc(rank: Rank) : MutableList<String> {
        val desc = mutableListOf<String>()
        desc.add(Chat.format("&6&m-----------------------------"))
        desc.add(Chat.format("&ePriority: &6${rank.weight}"))
        desc.add(Chat.format("&ePrefix:  ${rank.prefix}"))
        desc.add(Chat.format("&eColor: ${rank.color}This"))
        desc.add(Chat.format("&eDefault: &6${rank.default}"))
        desc.add(Chat.format("&eStaff Rank: &6${rank.staff}"))
        desc.add(Chat.format("&eMongo Id: &6" + rank.id))
        desc.add(Chat.format("&ePermissions: &6" + rank.permissions.size))
        desc.add(Chat.format("&eParents: &6" + rank.parents.size))
        if (rank.woolColor != null) {
            desc.add(Chat.format("&eWool Color: &r" + rank.woolColor!! + "This"))
        }
        desc.add(Chat.format("&6&m-----------------------------"))

        return desc
    }

    override fun getTitle(player: Player): String {
        return "Viewing Ranks"
    }

    override fun getButtonPositions(): List<Int> {
        return listOf(
            10, 11, 12, 13, 14, 15, 16,
            19, 20, 21, 22, 23, 24, 25,
            28, 29, 30, 31, 32, 33, 34
        )
    }

    override fun getButtonsPerPage(): Int {
        return 21
    }
}
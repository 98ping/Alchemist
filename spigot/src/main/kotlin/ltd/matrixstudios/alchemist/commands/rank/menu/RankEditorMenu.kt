package ltd.matrixstudios.alchemist.commands.rank.menu

import ltd.matrixstudios.alchemist.api.AlchemistAPI
import ltd.matrixstudios.alchemist.commands.rank.menu.filter.RankListFilter
import ltd.matrixstudios.alchemist.commands.rank.menu.sub.RankEditPropertiesMenu
import ltd.matrixstudios.alchemist.models.ranks.Rank
import ltd.matrixstudios.alchemist.service.ranks.RankService
import ltd.matrixstudios.alchemist.util.Chat
import ltd.matrixstudios.alchemist.util.InputPrompt
import ltd.matrixstudios.alchemist.util.menu.Button
import ltd.matrixstudios.alchemist.util.menu.buttons.SkullButton
import ltd.matrixstudios.alchemist.util.menu.pagination.PaginatedMenu
import ltd.matrixstudios.alchemist.util.menu.type.BorderedPaginatedMenu
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType

class RankEditorMenu(
    player: Player,
    private val ranks: List<Rank>,
    private val rankListFilter: RankListFilter
) : BorderedPaginatedMenu(player)
{

    override fun getHeaderItems(player: Player): MutableMap<Int, Button>
    {
        return super.getHeaderItems(player).apply {
            putAll(mutableMapOf(
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
                    "&eClick to switch &6Rank Filter"
                ).setBody { player, i, clickType ->
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
                    mutableListOf(
                        " ",
                        "&eClick to query every &6rank",
                        " "
                    ),
                    "&eQuery &6Ranks"
                ).setBody { player, i, clickType ->
                    InputPrompt()
                        .withText(Chat.format("&eType any search query to be shown a list of ranks that match the search"))
                        .acceptInput { s: String ->
                            RankEditorMenu(
                                player,
                                ranks.filter { it.id.contains(s, ignoreCase = true) }.toMutableList(),
                                rankListFilter
                            ).updateMenu()
                        }.start(player)
                }
            ))
        }
    }

    override fun getPagesButtons(player: Player): MutableMap<Int, Button>
    {
        val buttons = mutableMapOf<Int, Button>()
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
            desc.add(Chat.format("&6Display:"))
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

    private fun getRanksBasedOnFilter(filter: RankListFilter): MutableCollection<Rank>
    {
        return when (filter)
        {
            RankListFilter.ALL -> ranks
            RankListFilter.DEFAULT -> ranks.filter { it.default }
            RankListFilter.STAFF -> ranks.filter { it.staff }
            RankListFilter.HAS_PARENTS -> ranks.filter { it.parents.isNotEmpty() }
        }.toMutableList()
    }

    private fun generateRankListFilterLore(): MutableList<String>
    {
        val desc = mutableListOf<String>()
        desc.add(" ")
        for (filter in RankListFilter.values())
        {
            if (rankListFilter == filter)
            {
                desc.add(Chat.format("&7- &a" + filter.displayName))
            } else
            {
                desc.add(Chat.format("&7- &7" + filter.displayName))
            }
        }
        desc.add(" ")
        desc.add(Chat.format("&7Click to move to next filter!"))
        desc.add(" ")

        return desc
    }

}
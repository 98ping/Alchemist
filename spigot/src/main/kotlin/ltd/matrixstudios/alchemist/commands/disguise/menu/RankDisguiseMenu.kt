package ltd.matrixstudios.alchemist.commands.disguise.menu

import ltd.matrixstudios.alchemist.api.AlchemistAPI
import ltd.matrixstudios.alchemist.models.profile.disguise.RankDisguiseAttribute
import ltd.matrixstudios.alchemist.models.ranks.Rank
import ltd.matrixstudios.alchemist.service.profiles.ProfileGameService
import ltd.matrixstudios.alchemist.service.ranks.RankService
import ltd.matrixstudios.alchemist.util.Chat
import ltd.matrixstudios.alchemist.util.menu.Button
import ltd.matrixstudios.alchemist.util.menu.buttons.SkullButton
import ltd.matrixstudios.alchemist.util.menu.pagination.PaginatedMenu
import org.bukkit.entity.Player

/**
 * Class created on 6/27/2023

 * @author 98ping
 * @project Alchemist
 * @website https://solo.to/redis
 */
class RankDisguiseMenu(val player: Player) : PaginatedMenu(36, player) {

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
            3 to Button.placeholder(),
            5 to Button.placeholder()
        )
    }


    override fun getPagesButtons(player: Player): MutableMap<Int, Button> {
        val buttons = mutableMapOf<Int, Button>()
        var i = 0

        for (rank in RankService.getRanksInOrder().filter { AlchemistAPI.getRankWeight(player.uniqueId) >= it.weight }) {
            buttons[i++] = SkullButton(
                Chat.mapChatColorToSkullTexture(if (rank.woolColor != null) rank.woolColor!! else rank.color),
                generateRankButtonDesc(rank),
                "&r${rank.color}${rank.displayName}"
            ).setBody { player, i, clickType ->
                player.closeInventory()
                val profile = AlchemistAPI.syncFindProfile(player.uniqueId)!!
                profile.rankDisguiseAttribute = RankDisguiseAttribute(rank.id, true)
                ProfileGameService.save(profile)

                player.sendMessage(Chat.format("&eSuccess! &aYou now look like you have the rank " + rank.color + rank.displayName))
            }
        }

        return buttons
    }

    fun generateRankButtonDesc(rank: Rank) : MutableList<String> {
        val desc = mutableListOf<String>()
        desc.add(Chat.format("&6&m---------------------------"))
        desc.add(Chat.format("&eName: &f" + rank.color + rank.displayName))
        desc.add(Chat.format("&ePrefix: &f" + rank.prefix))
        desc.add(Chat.format("&eShows As: &f" + rank.prefix + rank.color + player.name))
        desc.add(Chat.format("&6&m---------------------------"))
        desc.add(Chat.format("&a&lClick to disguise your rank"))
        desc.add(Chat.format("&6&m---------------------------"))

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
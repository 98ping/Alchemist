package ltd.matrixstudios.alchemist.grants.menu.grant

import ltd.matrixstudios.alchemist.models.profile.GameProfile
import ltd.matrixstudios.alchemist.service.ranks.RankService
import ltd.matrixstudios.alchemist.util.Chat
import ltd.matrixstudios.alchemist.util.menu.Button
import ltd.matrixstudios.alchemist.util.menu.pagination.PaginatedMenu
import ltd.matrixstudios.alchemist.util.menu.type.BorderedPaginatedMenu
import org.bukkit.entity.Player

class GrantMenu(val player: Player, val gameProfile: GameProfile) : BorderedPaginatedMenu(player) {

    override fun getPagesButtons(player: Player): MutableMap<Int, Button> {
        val buttons = hashMapOf<Int, Button>()

        var index = 0
        for (rank in RankService.getAllRanksInOrder()) {
            buttons[index++] = GrantButton(rank, gameProfile)
        }

        return buttons
    }

    override fun getTitle(player: Player): String {
        return "Granting for " + Chat.format(gameProfile.getCurrentRank().color) + gameProfile.username
    }
}
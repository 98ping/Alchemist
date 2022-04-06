package ltd.matrixstudios.alchemist.commands.grants.menu.grant

import ltd.matrixstudios.alchemist.models.profile.GameProfile
import ltd.matrixstudios.alchemist.service.ranks.RankService
import ltd.matrixstudios.alchemist.util.menu.Button
import ltd.matrixstudios.alchemist.util.menu.pagination.PaginatedMenu
import org.bukkit.entity.Player

class GrantMenu(val player: Player, val gameProfile: GameProfile) : PaginatedMenu(27, player) {

    override fun getPagesButtons(player: Player): MutableMap<Int, Button> {
        val buttons = hashMapOf<Int, Button>()

        var index = 0
        for (rank in RankService.getValues()) {
            buttons[index++] = GrantButton(rank, gameProfile)
        }

        return buttons
    }

    override fun getTitle(player: Player): String {
        return "Granting of " + gameProfile.username
    }
}
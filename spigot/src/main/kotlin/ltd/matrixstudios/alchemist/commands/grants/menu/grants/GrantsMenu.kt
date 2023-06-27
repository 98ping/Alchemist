package ltd.matrixstudios.alchemist.commands.grants.menu.grants

import com.google.common.base.Stopwatch
import ltd.matrixstudios.alchemist.models.profile.GameProfile
import ltd.matrixstudios.alchemist.service.expirable.RankGrantService
import ltd.matrixstudios.alchemist.util.menu.Button
import ltd.matrixstudios.alchemist.util.menu.pagination.PaginatedMenu
import org.bukkit.entity.Player
import java.util.concurrent.TimeUnit

class GrantsMenu(val player: Player, val gameProfile: GameProfile) : PaginatedMenu(36, player) {

    override fun getPagesButtons(player: Player): MutableMap<Int, Button> {
        val buttons = hashMapOf<Int, Button>()

        val time = System.currentTimeMillis()

        var index = 0
        for (grant in RankGrantService.getFromCache(gameProfile.uuid)) {
            buttons[index++] = GrantsButton(grant)
        }

        return buttons
    }

    override fun getButtonPositions(): List<Int> {
        return listOf(10, 11, 12, 13, 14, 15, 16,
                      19, 20, 21, 22, 23, 24, 25,
                      28, 29, 30, 31, 32, 33, 34)
    }

    override fun getHeaderItems(player: Player): MutableMap<Int, Button> {
        return mutableMapOf(
            1 to Button.placeholder(),
            2 to Button.placeholder(),
            3 to Button.placeholder(),
            4 to Button.placeholder(),
            5 to Button.placeholder(),
            6 to Button.placeholder(),
            7 to Button.placeholder(),
            17 to Button.placeholder(),
            18 to Button.placeholder(),
            26 to Button.placeholder(),
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
            9 to Button.placeholder(),
            27 to Button.placeholder()
        )
    }

    override fun getButtonsPerPage(): Int {
        return 21
    }

    override fun getTitle(player: Player): String {
        return "Granting of " + gameProfile.username
    }
}
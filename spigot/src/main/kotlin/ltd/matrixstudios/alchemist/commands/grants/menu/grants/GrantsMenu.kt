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

        val stopwatch = Stopwatch.createStarted()

        var index = 0
        for (grant in RankGrantService.findByTarget(gameProfile.uuid)) {
            buttons[index++] = GrantsButton(grant)
        }

        stopwatch.stop()
        println("Menu " + this.javaClass.simpleName + " took " + stopwatch.elapsed(TimeUnit.MILLISECONDS) + "ms to open")

        return buttons
    }

    override fun getTitle(player: Player): String {
        return "Granting of " + gameProfile.username
    }
}
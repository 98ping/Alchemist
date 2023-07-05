package ltd.matrixstudios.alchemist.commands.metrics.menu

import ltd.matrixstudios.alchemist.commands.metrics.menu.buttons.*
import ltd.matrixstudios.alchemist.util.menu.Button
import ltd.matrixstudios.alchemist.util.menu.Menu
import org.bukkit.entity.Player

class MetricsMenu(val player: Player) : Menu(player) {

    init {
        staticSize = 27
        placeholder = true
    }

    override fun getButtons(player: Player): MutableMap<Int, Button> {
        val buttons = mutableMapOf<Int, Button>()

        buttons[4] = HeartbeatButton()
        buttons[10] = ProfileMetricButton()
        buttons[11] = GrantsMetricButton()
        buttons[12] = PunishmentMetricButton()
        buttons[13] = PermissionMetricButton()

        return buttons
    }

    override fun getTitle(player: Player): String {
        return "Listing Metrics"
    }
}
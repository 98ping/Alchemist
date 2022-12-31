package ltd.matrixstudios.alchemist.commands.metrics.menu

import ltd.matrixstudios.alchemist.commands.metrics.menu.buttons.GrantsMetricButton
import ltd.matrixstudios.alchemist.commands.metrics.menu.buttons.ProfileMetricButton
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
        buttons[10] = ProfileMetricButton()
        buttons[11] = GrantsMetricButton()

        return buttons
    }

    override fun getTitle(player: Player): String {
        return "Listing Metrics"
    }
}
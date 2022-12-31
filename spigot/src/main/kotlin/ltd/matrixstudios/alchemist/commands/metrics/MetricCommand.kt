package ltd.matrixstudios.alchemist.commands.metrics

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import ltd.matrixstudios.alchemist.commands.metrics.menu.MetricsMenu
import org.bukkit.entity.Player

class MetricCommand : BaseCommand() {

    @CommandAlias("metrics|mylaggyserver")
    fun metrics(player: Player)
    {
        MetricsMenu(player).openMenu()
    }
}
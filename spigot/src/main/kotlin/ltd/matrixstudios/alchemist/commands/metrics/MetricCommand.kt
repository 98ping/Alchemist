package ltd.matrixstudios.alchemist.commands.metrics

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import ltd.matrixstudios.alchemist.api.AlchemistAPI
import ltd.matrixstudios.alchemist.commands.metrics.menu.MetricsMenu
import ltd.matrixstudios.alchemist.metric.Metric
import ltd.matrixstudios.alchemist.metric.MetricService
import org.bukkit.entity.Player

class MetricCommand : BaseCommand() {

    @CommandAlias("metrics|mylaggyserver")
    fun metrics(player: Player)
    {
        val startMs = System.currentTimeMillis()
        //sends a decoy mongo request to know its working. If this takes long asf its my fault
        //for not being able to code :shrug:
        AlchemistAPI.syncFindProfile(player.uniqueId)
        MetricService.addMetric("Heartbeat", Metric("Heartbeat", System.currentTimeMillis().minus(startMs), System.currentTimeMillis()))

        MetricsMenu(player).openMenu()
    }
}
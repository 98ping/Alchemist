package ltd.matrixstudios.alchemist.commands.metrics

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandPermission
import co.aikar.commands.annotation.HelpCommand
import co.aikar.commands.annotation.Subcommand
import ltd.matrixstudios.alchemist.metric.MetricManager
import ltd.matrixstudios.alchemist.util.Chat
import org.bukkit.command.CommandSender

@CommandAlias("metrics|metric|performance")
@CommandPermission("alchemist.metrics")
class MetricsCommand : BaseCommand() {

    @HelpCommand
    fun help(sender: CommandSender) {
        sender.sendMessage(Chat.format("&7&m-------------------------"))
        sender.sendMessage(Chat.format("&6&lMetric Help"))
        sender.sendMessage(" ")
        sender.sendMessage(Chat.format("&e/metrics viewAll"))
        sender.sendMessage(Chat.format("&e/metrics viewGroup &f<group>"))
        sender.sendMessage(Chat.format("&7&m-------------------------"))
    }

    @Subcommand("viewall")
    fun viewall(sender: CommandSender)
    {
        sender.sendMessage(Chat.format("&e&lMetrics &7(All)"))
        sender.sendMessage(" ")
        sender.sendMessage(Chat.format("&eProfiles: " + MetricManager.get("profile")))
        sender.sendMessage(Chat.format("&eGrants: " + MetricManager.get("grants")))
        sender.sendMessage(" ")
    }


}
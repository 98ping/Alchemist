package ltd.matrixstudios.alchemist.statistics

import com.velocitypowered.api.command.SimpleCommand
import ltd.matrixstudios.alchemist.AlchemistVelocity
import java.util.concurrent.CompletableFuture

class ConnectionStatsCommand : SimpleCommand {

    override fun execute(invocation: SimpleCommand.Invocation) {
        val source = invocation.source()
        val args = invocation.arguments()

        if (!source.hasPermission("alchemist.staff")) {
            source.sendMessage(AlchemistVelocity.color("&cYou do not have permission to use this command."))
            return
        }

        if (args.isNotEmpty() && args[0].equals("reset", ignoreCase = true)) {
            if (!source.hasPermission("alchemist.admin")) {
                source.sendMessage(AlchemistVelocity.color("&cYou do not have permission to reset connection statistics."))
                return
            }

            CompletableFuture.runAsync {
                ConnectionStatisticsService.reset()
                source.sendMessage(AlchemistVelocity.color("&aConnection statistics have been reset."))
            }
            return
        }

        CompletableFuture.runAsync {
            val stats = ConnectionStatisticsService.fetchStatistics()
            val successRate = if (stats.attempts > 0L) {
                stats.successful.toDouble() / stats.attempts.toDouble() * 100.0
            } else 0.0

            val message = buildString {
                appendLine("&8&m----------------------------------------")
                appendLine(" &b&lConnection Statistics")
                appendLine("")
                appendLine(" &7Server list pings: &f${"%,d".format(stats.pings)}")
                appendLine(" &7Connection attempts: &f${"%,d".format(stats.attempts)}")
                appendLine(" &7Successful logins: &f${"%,d".format(stats.successful)}")
                appendLine(" &7Success rate: &a${"%.1f".format(successRate)}%")
                append("&8&m----------------------------------------")
            }

            source.sendMessage(AlchemistVelocity.color(message))
        }
    }

    override fun suggest(invocation: SimpleCommand.Invocation): List<String> {
        val args = invocation.arguments()
        if (args.size <= 1 && invocation.source().hasPermission("alchemist.admin")) {
            return listOf("reset")
        }
        return emptyList()
    }
}

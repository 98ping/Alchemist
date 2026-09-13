package ltd.matrixstudios.alchemist.lockdown

import com.velocitypowered.api.command.SimpleCommand
import com.velocitypowered.api.proxy.Player
import ltd.matrixstudios.alchemist.AlchemistVelocity
import ltd.matrixstudios.alchemist.packets.StaffMessagePacket
import ltd.matrixstudios.alchemist.service.profiles.ProfileGameService
import java.util.concurrent.CompletableFuture

class LockdownCommand : SimpleCommand {

    override fun execute(invocation: SimpleCommand.Invocation) {
        val source = invocation.source()
        val args = invocation.arguments()

        if (!source.hasPermission("alchemist.admin")) {
            source.sendMessage(AlchemistVelocity.color("&cYou do not have permission to use this command."))
            return
        }

        val active = when (args.firstOrNull()?.lowercase()) {
            "up" -> true
            "down" -> false
            else -> {
                CompletableFuture.runAsync {
                    val status = if (LockdownManager.serverIsOnLockdown()) "&cup" else "&adown"
                    source.sendMessage(AlchemistVelocity.color("&7Lockdown is currently $status&7.\n&cUsage: /lockdown <up|down>"))
                }
                return
            }
        }

        CompletableFuture.runAsync {
            if (LockdownManager.serverIsOnLockdown() == active) {
                source.sendMessage(AlchemistVelocity.color("&cLockdown is already ${if (active) "up" else "down"}."))
                return@runAsync
            }

            LockdownManager.setLockdown(active)

            source.sendMessage(AlchemistVelocity.color(if (active) "&aThe network is now on lockdown." else "&aThe network is no longer on lockdown."))

            val name = if (source is Player) {
                (ProfileGameService.byId(source.uniqueId)?.getHighestGlobalRank()?.color ?: "&f") + source.username
            } else {
                "&4Console"
            }
            val action = if (active) "&3put the network on &clockdown" else "&3lifted the network &alockdown"

            StaffMessagePacket("&b[S] &r$name $action").action()
        }
    }

    override fun suggest(invocation: SimpleCommand.Invocation): List<String> {
        val args = invocation.arguments()
        if (args.size <= 1 && invocation.source().hasPermission("alchemist.admin")) {
            return listOf("up", "down").filter { it.startsWith(args.firstOrNull().orEmpty(), ignoreCase = true) }
        }
        return emptyList()
    }
}

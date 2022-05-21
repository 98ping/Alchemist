package ltd.matrixstudios.alchemist.commands.player

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import ltd.matrixstudios.alchemist.api.AlchemistAPI
import ltd.matrixstudios.alchemist.service.ranks.RankService
import ltd.matrixstudios.alchemist.util.Chat
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import java.util.*
import java.util.concurrent.ForkJoinPool

class ListCommand : BaseCommand() {


    @CommandAlias("list|players|online")
    fun list(sender: CommandSender) {
        ForkJoinPool.commonPool().execute {
            sender.sendMessage(Chat.format(" "))
            sender.sendMessage(Chat.format("&e&lCurrently Online: &f" + Bukkit.getOnlinePlayers().size))
            sender.sendMessage(Chat.format("&e&lRanks: " + RankService.getRanksInOrder().joinToString(", ") { it.color + it.displayName }))

            AlchemistAPI.supplyColoredNames().thenAccept {
                sender.sendMessage(Chat.format("&e&lPlayers: $it"))
                sender.sendMessage(Chat.format(" "))
            }

        }
    }
}
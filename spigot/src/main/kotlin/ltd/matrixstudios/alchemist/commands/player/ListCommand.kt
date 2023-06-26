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
            sender.sendMessage(Chat.format(RankService.getRanksInOrder().joinToString("&f, ") { it.color + it.displayName }))

            AlchemistAPI.supplyColoredNames().thenAccept { players ->
                if (players.size >= 350) {
                    sender.sendMessage(Chat.format("&f(" + Bukkit.getOnlinePlayers().size + "/${Bukkit.getMaxPlayers()}&f) ${
                        players.take(
                            350
                        ).joinToString("&f, ") { it.displayName }
                    }"))
                    sender.sendMessage(Chat.format("&c(Only showing first 350 entries...)"))
                    sender.sendMessage(" ")
                } else {
                    sender.sendMessage(Chat.format("&f(" + Bukkit.getOnlinePlayers().size + "/${Bukkit.getMaxPlayers()}&f) ${players.joinToString("&f, ") { it.displayName }}"))
                    sender.sendMessage(" ")
                }
            }
        }
    }
}
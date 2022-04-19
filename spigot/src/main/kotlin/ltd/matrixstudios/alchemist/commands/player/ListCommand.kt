package ltd.matrixstudios.alchemist.commands.player

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import ltd.matrixstudios.alchemist.api.AlchemistAPI
import ltd.matrixstudios.alchemist.service.ranks.RankService
import ltd.matrixstudios.alchemist.util.Chat
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender

class ListCommand : BaseCommand() {


    @CommandAlias("list|players|online")
    fun list(sender: CommandSender) {
        sender.sendMessage(Chat.format(" "))
        sender.sendMessage(Chat.format("&e&lCurrently Online: &f" + Bukkit.getOnlinePlayers().size))
        sender.sendMessage(Chat.format("&e&lRanks: " + RankService.getRanksInOrder().joinToString(", ") { it.color + it.displayName }))
        sender.sendMessage(Chat.format(AlchemistAPI.supplyColoredNames().get()))
        sender.sendMessage(Chat.format(" "))
    }
}
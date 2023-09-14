package ltd.matrixstudios.alchemist.client.commands

import co.aikar.commands.BaseCommand
import co.aikar.commands.CommandHelp
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandCompletion
import co.aikar.commands.annotation.CommandPermission
import co.aikar.commands.annotation.HelpCommand
import co.aikar.commands.annotation.Name
import co.aikar.commands.annotation.Subcommand
import co.aikar.commands.bukkit.contexts.OnlinePlayer
import com.lunarclient.bukkitapi.LunarClientAPI
import ltd.matrixstudios.alchemist.Alchemist
import ltd.matrixstudios.alchemist.api.AlchemistAPI
import ltd.matrixstudios.alchemist.client.LunarClientExtension
import ltd.matrixstudios.alchemist.util.Chat
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

/**
 * Class created on 9/13/2023

 * @author 98ping
 * @project Alchemist
 * @website https://solo.to/redis
 */
@CommandAlias("lunarclient|lc")
@CommandPermission("alchemist.clients.lunar")
object LunarClientCommands : BaseCommand()
{

    @HelpCommand
    fun help(help: CommandHelp)
    {
        help.showHelp()
    }

    @Subcommand("players")
    fun players(player: CommandSender)
    {
        val start = System.currentTimeMillis()
        val count = Bukkit.getOnlinePlayers().count {
            LunarClientAPI.getInstance().isRunningLunarClient(it)
        }

        player.sendMessage(Chat.format("&eThis server has a total of &a${count} &eplayers that run &bLunar Client&e."))
        player.sendMessage(Chat.format("&7To check a specific user's status, execute /lc check <player>"))
        player.sendMessage(Chat.format("&8(This lookup took ${System.currentTimeMillis().minus(start)} milliseconds)"))
    }

    @Subcommand("check")
    @CommandCompletion("@players")
    fun check(player: CommandSender, @Name("target") target: OnlinePlayer)
    {
        val isUsing = LunarClientAPI.getInstance().isRunningLunarClient(target.player)

        player.sendMessage(Chat.format("&r${AlchemistAPI.getRankDisplay(target.player.uniqueId)} &eis ${if (isUsing) "&acurrently" else "&cnot currently"} &eusing &bLunar Client&e."))
    }
}
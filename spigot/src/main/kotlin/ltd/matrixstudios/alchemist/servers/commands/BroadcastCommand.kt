package ltd.matrixstudios.alchemist.servers.commands

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandPermission
import co.aikar.commands.annotation.Name
import co.aikar.commands.annotation.Syntax
import ltd.matrixstudios.alchemist.Alchemist
import ltd.matrixstudios.alchemist.util.Chat
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender

/**
 * Class created on 8/3/2023

 * @author 98ping
 * @project Alchemist
 * @website https://solo.to/redis
 */
object BroadcastCommand : BaseCommand()
{

    @CommandAlias("bc|broadcast|raw")
    @CommandPermission("alchemist.broadcast")
    @Syntax("[-p❘-s] <message...>")
    fun bc(sender: CommandSender, @Name("message...") msg: String)
    {
        val newMessage = StringBuilder()

        if (msg.startsWith("-p"))
        {
            newMessage.append("&8[&4Alert&8] ")
        }

        if (msg.startsWith("-s"))
        {
            newMessage.append("&7(${Alchemist.globalServer.displayName}) ")
        }

        newMessage.append(msg.removePrefix("-s").removePrefix("-p"))

        Bukkit.broadcastMessage(Chat.format(newMessage.toString()))
    }
}
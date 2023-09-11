package ltd.matrixstudios.alchemist.servers.commands

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import ltd.matrixstudios.alchemist.Alchemist
import ltd.matrixstudios.alchemist.util.Chat
import org.bukkit.entity.Player

object WhereAmICommand : BaseCommand()
{
    @CommandAlias("whereami|whatamion")
    fun whereAmI(sender: Player)
    {
        val server = Alchemist.globalServer
        sender.sendMessage(Chat.format("&eYou are currently on the server &a${server.displayName}&e. &8(${server.players.size} Connected)"))
    }
}
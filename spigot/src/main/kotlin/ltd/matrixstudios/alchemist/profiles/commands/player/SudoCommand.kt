package ltd.matrixstudios.alchemist.profiles.commands.player

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandPermission
import co.aikar.commands.annotation.Name
import ltd.matrixstudios.alchemist.util.Chat
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import java.util.*

class SudoCommand : BaseCommand()
{

    @CommandAlias("sudoall")
    @CommandPermission("alchemist.sudoall")
    fun sudoAll(sender: CommandSender, @Name("message") message: String)
    {
        Bukkit.getOnlinePlayers().forEach {
            it.chat(message)
        }

        sender.sendMessage(Chat.format("&aSudo'ed every person online to say &f$message"))
    }

}
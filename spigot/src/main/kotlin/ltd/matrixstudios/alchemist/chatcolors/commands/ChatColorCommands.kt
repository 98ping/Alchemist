package ltd.matrixstudios.alchemist.chatcolors.commands

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import ltd.matrixstudios.alchemist.chatcolors.menu.ChatColorMenu
import org.bukkit.entity.Player

class ChatColorCommands : BaseCommand()
{

    @CommandAlias("chatcolors|cc|colors")
    fun chatColors(player: Player)
    {
        ChatColorMenu(player).updateMenu()
    }
}
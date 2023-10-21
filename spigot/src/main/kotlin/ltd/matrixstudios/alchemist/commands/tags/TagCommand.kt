package ltd.matrixstudios.alchemist.commands.tags

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import ltd.matrixstudios.alchemist.commands.tags.menu.GeneralTagMenu
import org.bukkit.entity.Player

class TagCommand : BaseCommand()
{

    @CommandAlias("prefix|tags|tag|prefixes")
    fun tags(player: Player)
    {
        GeneralTagMenu(player).updateMenu()
    }
}
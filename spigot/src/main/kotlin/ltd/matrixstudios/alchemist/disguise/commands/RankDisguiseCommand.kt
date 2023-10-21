package ltd.matrixstudios.alchemist.disguise.commands

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandPermission
import ltd.matrixstudios.alchemist.disguise.commands.menu.rank.RankDisguiseMenu
import org.bukkit.entity.Player
import java.util.*


class RankDisguiseCommand : BaseCommand()
{

    @CommandAlias("rankdisguise")
    @CommandPermission("alchemist.disguise.rank")
    fun rankDisguise(player: Player)
    {
        RankDisguiseMenu(player).updateMenu()
    }
}
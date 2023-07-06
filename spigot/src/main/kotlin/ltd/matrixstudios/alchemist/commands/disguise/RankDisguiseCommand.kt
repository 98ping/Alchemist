package ltd.matrixstudios.alchemist.commands.disguise

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandPermission
import co.aikar.commands.annotation.Name
import ltd.matrixstudios.alchemist.commands.disguise.menu.RankDisguiseMenu
import ltd.matrixstudios.alchemist.models.ranks.Rank
import org.bukkit.entity.Player

class RankDisguiseCommand : BaseCommand() {

    @CommandAlias("rankdisguise")
    @CommandPermission("alchemist.disguise.rank")
    fun rankDisguise(player: Player) {
        RankDisguiseMenu(player).updateMenu()
    }
}
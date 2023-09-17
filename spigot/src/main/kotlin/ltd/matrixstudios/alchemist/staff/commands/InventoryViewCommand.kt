package ltd.matrixstudios.alchemist.staff.commands

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandPermission
import co.aikar.commands.annotation.Name
import co.aikar.commands.bukkit.contexts.OnlinePlayer
import org.bukkit.entity.Player

class InventoryViewCommand : BaseCommand() {

    @CommandAlias("invsee|inv|viewinventory|viewinv")
    @CommandPermission("alchemist.invsee")
    fun invsee(player: Player, @Name("target") onlineTarget: OnlinePlayer) {
        val target = onlineTarget.player
        player.openInventory(target.inventory)
    }

}
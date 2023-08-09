package ltd.matrixstudios.alchemist.essentials.commands

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandPermission
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryType

class CraftCommand : BaseCommand() {

    @CommandAlias("craft")
    @CommandPermission("alchemist.essentials.craft")
    fun craft(player: Player)
    {
        player.openWorkbench(player.location, true)
    }
}
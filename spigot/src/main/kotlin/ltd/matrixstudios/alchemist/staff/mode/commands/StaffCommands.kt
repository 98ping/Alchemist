package ltd.matrixstudios.alchemist.staff.mode.commands

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandPermission
import co.aikar.commands.annotation.Name
import co.aikar.commands.annotation.Optional
import ltd.matrixstudios.alchemist.staff.mode.StaffSuiteManager
import ltd.matrixstudios.alchemist.util.Chat
import org.bukkit.Bukkit
import org.bukkit.entity.Player

class StaffCommands : BaseCommand() {

    @CommandAlias("staff|h|mod|hacker|staffmode|modmode")
    @CommandPermission("alchemist.staffmode")
    fun staff(player: Player, @Name("other") @Optional target: String?)
    {
        if (target == null)
        {
            val isIn = StaffSuiteManager.isModMode(player)

            if (isIn)
            {
                StaffSuiteManager.removeStaffMode(player)
                player.sendMessage(Chat.format("&aYou have went into Staff Mode!"))
            } else {
                StaffSuiteManager.setStaffMode(player)
                player.sendMessage(Chat.format("&cYou have left Staff Mode!"))
            }
        } else {
            val targetPlayer = Bukkit.getPlayer(target)

            if (targetPlayer == null)
            {
                player.sendMessage(Chat.format("&cInvalid target!"))
                return
            }

            val isIn = StaffSuiteManager.isModMode(targetPlayer)

            if (isIn)
            {
                StaffSuiteManager.removeStaffMode(targetPlayer)
                targetPlayer.sendMessage(Chat.format("&aYou have went into Staff Mode!"))
            } else {
                StaffSuiteManager.setStaffMode(targetPlayer)
                targetPlayer.sendMessage(Chat.format("&cYou have left Staff Mode!"))
            }

            player.sendMessage(Chat.format("&aUpdated the Staff Mode status of &f$target"))
        }
    }
}
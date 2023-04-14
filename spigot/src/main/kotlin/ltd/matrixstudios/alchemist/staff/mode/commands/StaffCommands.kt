package ltd.matrixstudios.alchemist.staff.mode.commands

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.*
import ltd.matrixstudios.alchemist.models.profile.GameProfile
import ltd.matrixstudios.alchemist.staff.mode.StaffItems
import ltd.matrixstudios.alchemist.staff.mode.StaffSuiteManager
import ltd.matrixstudios.alchemist.util.Chat
import org.bukkit.Bukkit
import org.bukkit.entity.Player

class StaffCommands : BaseCommand() {

    @CommandAlias("givestaffitems")
    @CommandPermission("alchemist.admin")
    @CommandCompletion("@gameprofile")
    fun staffitems(player: Player, @Name("other") profile: GameProfile)
    {
        val bukkitPlayer = Bukkit.getPlayer(profile.uuid)

        if (bukkitPlayer == null || !bukkitPlayer.isOnline)
        {
            player.sendMessage(Chat.format("&cThis player is not online!"))
            return
        }

        bukkitPlayer.inventory.setItem(0, StaffItems.COMPASS)
        bukkitPlayer.inventory.setItem(1, StaffItems.INVENTORY_INSPECT)
        bukkitPlayer.inventory.setItem(2, StaffItems.RANDOMTP)
        bukkitPlayer.inventory.setItem(3, StaffItems.BETTER_VIEW)

        if (bukkitPlayer.hasPermission("alchemist.staffmode.worldedit"))
        {
            bukkitPlayer.inventory.setItem(4, StaffItems.WORLDEDIT_AXE)
        }

        bukkitPlayer.inventory.setItem(6, StaffItems.ONLINE_STAFF)
        bukkitPlayer.inventory.setItem(7, StaffItems.VANISH)
        bukkitPlayer.inventory.setItem(8, StaffItems.FREEZE)

        bukkitPlayer.updateInventory()
        player.sendMessage(Chat.format("&aGiven this player the default mod mode loadout!"))

    }

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
                player.sendMessage(Chat.format("&cYou have left Staff Mode!"))
            } else {
                StaffSuiteManager.setStaffMode(player)
                player.sendMessage(Chat.format("&aYou have went into Staff Mode!"))
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
                targetPlayer.sendMessage(Chat.format("&cYou have left Staff Mode!"))
            } else {
                StaffSuiteManager.setStaffMode(targetPlayer)
                targetPlayer.sendMessage(Chat.format("&aYou have went into Staff Mode!"))
            }

            player.sendMessage(Chat.format("&aUpdated the Staff Mode status of &f$target"))
        }
    }
}
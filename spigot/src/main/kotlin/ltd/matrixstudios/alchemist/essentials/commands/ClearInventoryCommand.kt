package ltd.matrixstudios.alchemist.essentials.commands

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandPermission
import co.aikar.commands.annotation.Name
import co.aikar.commands.annotation.Optional
import co.aikar.commands.bukkit.contexts.OnlinePlayer
import ltd.matrixstudios.alchemist.util.Chat
import org.bukkit.GameMode
import org.bukkit.entity.Player

class ClearInventoryCommand : BaseCommand() {

    @CommandAlias("clear|ci|clearinv")
    @CommandPermission("alchemist.essentials.clear")
    fun clear(player: Player, @Name("target") @Optional target: OnlinePlayer?)
    {
        if (target == null) {
            player.inventory.clear()
            player.inventory.armorContents = arrayOfNulls(4)
            player.sendMessage(Chat.format("&6You have cleared your &finventory&6."))
        } else {
            if (!player.hasPermission("alchemist.essentials.clear.other"))
            {
                player.sendMessage(Chat.format("&cYou do not have permission to clear other people's inventories!"))
                return
            }

            player.sendMessage(Chat.format(target.player.displayName + " &6has gotten their &finventory &6cleared."))
            target.player.inventory.clear()
            target.player.inventory.armorContents = arrayOfNulls(4)
            target.player.sendMessage(Chat.format("&6Your &finventory &6has been cleared."))
        }
    }
}
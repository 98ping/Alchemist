package ltd.matrixstudios.alchemist.essentials.commands

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandPermission
import co.aikar.commands.annotation.Name
import ltd.matrixstudios.alchemist.util.Chat
import org.bukkit.entity.Player
import org.bukkit.potion.PotionEffect
import java.util.function.Consumer

class InventoryCopyingCommands : BaseCommand() {

    @CommandAlias("cpinv|cpfrom")
    @CommandPermission("alchemist.essentials.cpinv")
    fun cpInv(sender: Player, @Name("target") player: Player)
    {
        sender.inventory.contents = player.inventory.contents
        sender.inventory.armorContents = player.inventory.armorContents
        sender.health = player.health
        sender.foodLevel = player.foodLevel
        player.activePotionEffects.forEach(sender::addPotionEffect)

        sender.sendMessage(Chat.format(player.displayName + "&6's inventory has been applied to you."))
    }

    @CommandAlias("cpto")
    @CommandPermission("alchemist.essentials.cpto")
    fun cpTo(sender: Player, @Name("target") player: Player)
    {
        player.inventory.contents = sender.inventory.contents
        player.inventory.armorContents = sender.inventory.armorContents
        player.health = sender.health
        player.foodLevel = sender.foodLevel
        sender.activePotionEffects.forEach(player::addPotionEffect)

        sender.sendMessage(Chat.format("&6Your inventory has been applied to &f" + player.displayName + "&6."))
    }
}
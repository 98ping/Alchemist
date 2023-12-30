package ltd.matrixstudios.alchemist.essentials.commands

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandPermission
import co.aikar.commands.annotation.Name
import co.aikar.commands.bukkit.contexts.OnlinePlayer
import ltd.matrixstudios.alchemist.Alchemist
import ltd.matrixstudios.alchemist.redis.AsynchronousRedisSender
import ltd.matrixstudios.alchemist.staff.alerts.StaffActionAlertPacket
import ltd.matrixstudios.alchemist.util.Chat
import org.bukkit.entity.Player

class InventoryCopyingCommands : BaseCommand()
{

    @CommandAlias("cpinv|cpfrom")
    @CommandPermission("alchemist.essentials.cpinv")
    fun cpInv(sender: Player, @Name("target") online: OnlinePlayer)
    {
        val player = online.player

        sender.inventory.contents = player.inventory.contents
        sender.inventory.armorContents = player.inventory.armorContents
        sender.health = player.health
        sender.foodLevel = player.foodLevel
        player.activePotionEffects.forEach(sender::addPotionEffect)

        sender.sendMessage(Chat.format(player.displayName + "&6's inventory has been applied to you."))

        AsynchronousRedisSender.send(StaffActionAlertPacket("has copied ${online.player.name}'s current inventory", player.name, Alchemist.globalServer.id))
    }

    @CommandAlias("cpto")
    @CommandPermission("alchemist.essentials.cpto")
    fun cpTo(sender: Player, @Name("target") online: OnlinePlayer)
    {
        val player = online.player

        player.inventory.contents = sender.inventory.contents
        player.inventory.armorContents = sender.inventory.armorContents
        player.health = sender.health
        player.foodLevel = sender.foodLevel
        sender.activePotionEffects.forEach(player::addPotionEffect)

        AsynchronousRedisSender.send(StaffActionAlertPacket("has given their inventory to ${online.player.name}", player.name, Alchemist.globalServer.id))

        sender.sendMessage(Chat.format("&6Your inventory has been applied to &f" + player.displayName + "&6."))
    }
}
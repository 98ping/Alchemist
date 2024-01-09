package ltd.matrixstudios.alchemist.essentials.commands

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandPermission
import co.aikar.commands.annotation.Name
import co.aikar.commands.annotation.Optional
import co.aikar.commands.bukkit.contexts.OnlinePlayer
import ltd.matrixstudios.alchemist.Alchemist
import ltd.matrixstudios.alchemist.redis.AsynchronousRedisSender
import ltd.matrixstudios.alchemist.staff.alerts.StaffActionAlertPacket
import ltd.matrixstudios.alchemist.util.Chat
import org.bukkit.entity.Player

class RegenerativeCommands : BaseCommand()
{

    @CommandAlias("heal")
    @CommandPermission("alchemist.essentials.heal")
    fun heal(sender: Player, @Name("target") @Optional target: OnlinePlayer?)
    {
        if (target == null)
        {
            sender.health = 20.0
            sender.sendMessage(Chat.format("&6You have been healed!"))
            AsynchronousRedisSender.send(StaffActionAlertPacket("has healed themselves", sender.name, Alchemist.globalServer.id))
        } else
        {
            target.player.health = 20.0
            target.player.sendMessage(Chat.format("&6You have been healed!"))
            sender.sendMessage(Chat.format("&6You have healed ${target.player.displayName}"))
            AsynchronousRedisSender.send(StaffActionAlertPacket("has healed ${target.player.name}", sender.name, Alchemist.globalServer.id))
        }
    }

    @CommandAlias("feed")
    @CommandPermission("alchemist.essentials.feed")
    fun feed(sender: Player, @Name("target") @Optional target: OnlinePlayer?)
    {
        if (target == null)
        {
            sender.foodLevel = 10
            sender.sendMessage(Chat.format("&6You have been fed!"))
            AsynchronousRedisSender.send(StaffActionAlertPacket("has fed themselves", sender.name, Alchemist.globalServer.id))
        } else
        {
            target.player.foodLevel = 10
            target.player.sendMessage(Chat.format("&6You have been fed!"))
            sender.sendMessage(Chat.format("&6You have fed ${target.player.displayName}"))
            AsynchronousRedisSender.send(StaffActionAlertPacket("has fed ${target.player.name}", sender.name, Alchemist.globalServer.id))
        }
    }
}
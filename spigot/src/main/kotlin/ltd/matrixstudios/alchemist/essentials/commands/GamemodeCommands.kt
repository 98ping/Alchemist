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
import org.bukkit.GameMode
import org.bukkit.entity.Player

class GamemodeCommands : BaseCommand()
{

    @CommandAlias("gmc")
    @CommandPermission("alchemist.essentials.gamemode")
    fun gmc(player: Player, @Name("target") @Optional target: OnlinePlayer?)
    {
        if (target == null)
        {
            player.gameMode = GameMode.CREATIVE
            player.sendMessage(Chat.format("&6You are now in &fCREATIVE &6mode."))

            AsynchronousRedisSender.send(StaffActionAlertPacket("has gone into CREATIVE mode", player.name, Alchemist.globalServer.id))
        } else
        {
            player.sendMessage(Chat.format(target.player.displayName + " &6is now in &fCREATIVE &6mode."))
            target.player.gameMode = GameMode.CREATIVE
            target.player.sendMessage(Chat.format("&6You are now in &fCREATIVE &6mode."))

            AsynchronousRedisSender.send(StaffActionAlertPacket("has put ${target.player.name} into CREATIVE mode", player.name, Alchemist.globalServer.id))
        }
    }

    @CommandAlias("gms")
    @CommandPermission("alchemist.essentials.gamemode")
    fun gms(player: Player, @Name("target") @Optional target: OnlinePlayer?)
    {
        if (target == null)
        {
            player.gameMode = GameMode.SURVIVAL
            player.sendMessage(Chat.format("&6You are now in &fSURVIVAL &6mode."))

            AsynchronousRedisSender.send(StaffActionAlertPacket("has gone into SURVIVAL mode", player.name, Alchemist.globalServer.id))
        } else
        {
            player.sendMessage(Chat.format(target.player.displayName + " &6is now in &fSURVIVAL &6mode."))
            target.player.gameMode = GameMode.SURVIVAL
            target.player.sendMessage(Chat.format("&6You are now in &fSURVIVAL &6mode."))

            AsynchronousRedisSender.send(StaffActionAlertPacket("has put ${target.player.name} into SURVIVAL mode", player.name, Alchemist.globalServer.id))
        }
    }
}
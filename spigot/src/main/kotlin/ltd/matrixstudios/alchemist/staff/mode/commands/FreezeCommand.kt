package ltd.matrixstudios.alchemist.staff.mode.commands

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandPermission
import co.aikar.commands.annotation.Name
import ltd.matrixstudios.alchemist.Alchemist
import ltd.matrixstudios.alchemist.AlchemistSpigotPlugin
import ltd.matrixstudios.alchemist.api.AlchemistAPI
import ltd.matrixstudios.alchemist.packets.StaffGeneralMessagePacket
import ltd.matrixstudios.alchemist.redis.AsynchronousRedisSender
import ltd.matrixstudios.alchemist.util.Chat
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.metadata.FixedMetadataValue

class FreezeCommand : BaseCommand()
{

    @CommandAlias("freeze|ss")
    @CommandPermission("alchemist.staffmode")
    fun freeze(player: Player, @Name("target") targetString: String)
    {
        val target = Bukkit.getPlayer(targetString)

        if (target == null)
        {
            player.sendMessage(Chat.format("&cInvalid target"))
            return
        }

        val frozen = target.hasMetadata("frozen")
        val server = Alchemist.globalServer.displayName
        val displayExec = AlchemistAPI.getRankDisplay(player.uniqueId)
        val displayTarget = AlchemistAPI.getRankDisplay(target.uniqueId)

        if (frozen)
        {
            target.removeMetadata("frozen", AlchemistSpigotPlugin.instance)
            target.sendMessage(Chat.format("&aYou have been unfrozen"))
            AsynchronousRedisSender.send(StaffGeneralMessagePacket("&b[S] &3[$server] $displayExec &3has unfrozen $displayTarget"))
        } else
        {
            target.setMetadata("frozen", FixedMetadataValue(AlchemistSpigotPlugin.instance, true))
            target.sendMessage(Chat.format("&cYou have been frozen"))
            AsynchronousRedisSender.send(StaffGeneralMessagePacket("&b[S] &3[$server] $displayExec &3has frozen $displayTarget"))
        }

    }

}
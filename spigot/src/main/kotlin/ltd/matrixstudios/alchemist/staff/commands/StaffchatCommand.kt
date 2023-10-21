package ltd.matrixstudios.alchemist.staff.commands

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandPermission
import co.aikar.commands.annotation.Name
import ltd.matrixstudios.alchemist.Alchemist
import ltd.matrixstudios.alchemist.packets.StaffMessagePacket
import ltd.matrixstudios.alchemist.redis.AsynchronousRedisSender
import org.bukkit.entity.Player

class StaffchatCommand : BaseCommand()
{

    @CommandAlias("sc|staffchat")
    @CommandPermission("alchemist.staffchat")
    fun staffchat(player: Player, @Name("message") message: String)
    {
        AsynchronousRedisSender.send(StaffMessagePacket(message, Alchemist.globalServer.displayName, player.uniqueId))
    }
}
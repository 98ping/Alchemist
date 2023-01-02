package ltd.matrixstudios.alchemist.commands.admin

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandPermission
import co.aikar.commands.annotation.Name
import ltd.matrixstudios.alchemist.AlchemistSpigotPlugin
import ltd.matrixstudios.alchemist.redis.AsynchronousRedisSender
import ltd.matrixstudios.alchemist.packets.AdminChatPacket
import org.bukkit.entity.Player

class AdminChatCommand : BaseCommand() {

    @CommandAlias("ac|adminchat")
    @CommandPermission("alchemist.adminchat")
    fun adminChat(player: Player, @Name("message")message: String)
    {
        AsynchronousRedisSender.send(AdminChatPacket(message, AlchemistSpigotPlugin.instance.globalServer.displayName, player.uniqueId))
    }
}
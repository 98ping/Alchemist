package ltd.matrixstudios.alchemist.staff.requests.commands

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.Name
import ltd.matrixstudios.alchemist.AlchemistSpigotPlugin
import ltd.matrixstudios.alchemist.api.AlchemistAPI
import ltd.matrixstudios.alchemist.packets.StaffGeneralMessagePacket
import ltd.matrixstudios.alchemist.redis.AsynchronousRedisSender
import ltd.matrixstudios.alchemist.staff.requests.handlers.RequestHandler
import ltd.matrixstudios.alchemist.staff.requests.packets.RequestPacket
import ltd.matrixstudios.alchemist.util.Chat
import org.bukkit.entity.Player

class RequestCommand : BaseCommand() {

    @CommandAlias("request|helpop")
    fun request(player: Player, @Name("reason")rzn: String)
    {
        if (RequestHandler.isOnRequestCooldown(player))
        {
            player.sendMessage(Chat.format("&cPlease wait before trying this again!"))
            return
        }

        val currentServer = AlchemistSpigotPlugin.instance.globalServer.displayName
        val display = AlchemistAPI.getRankDisplay(player.uniqueId)

        AsynchronousRedisSender.send(RequestPacket("&9[Request] &7[$currentServer] &b$display &7requested assistance\n     &9Reason: &7$rzn"))
        RequestHandler.requestCooldowns[player.uniqueId] = System.currentTimeMillis()
        player.sendMessage(Chat.format("&aYour request has been sent to every online staff member!"))

    }
}
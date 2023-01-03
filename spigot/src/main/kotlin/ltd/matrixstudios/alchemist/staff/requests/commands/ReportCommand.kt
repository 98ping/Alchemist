package ltd.matrixstudios.alchemist.staff.requests.commands

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.Name
import ltd.matrixstudios.alchemist.AlchemistSpigotPlugin
import ltd.matrixstudios.alchemist.api.AlchemistAPI
import ltd.matrixstudios.alchemist.packets.StaffGeneralMessagePacket
import ltd.matrixstudios.alchemist.redis.AsynchronousRedisSender
import ltd.matrixstudios.alchemist.staff.requests.handlers.RequestHandler
import ltd.matrixstudios.alchemist.util.Chat
import org.bukkit.Bukkit
import org.bukkit.entity.Player

class ReportCommand : BaseCommand() {

    @CommandAlias("report")
    fun request(player: Player, @Name("player")other:String, @Name("reason")rzn: String)
    {
        if (RequestHandler.isOnReportCooldown(player))
        {
            player.sendMessage(Chat.format("&cPlease wait before trying this again!"))
            return
        }

        val target = Bukkit.getPlayer(other)

        if (target == null)
        {
            player.sendMessage(Chat.format("&cInvalid target"))
            return
        }

        if (player.name == target.name)
        {
            player.sendMessage(Chat.format("&cYou cannot report yourself!"))
            return
        }


        val currentServer = AlchemistSpigotPlugin.instance.globalServer.displayName
        val display = AlchemistAPI.getRankDisplay(player.uniqueId)
        val otherDisplay = AlchemistAPI.getRankDisplay(target.uniqueId)

        AsynchronousRedisSender.send(StaffGeneralMessagePacket("&9[Report] &7[$currentServer] &b$display &7has reported &f$otherDisplay\n     &9Reason: &7$rzn"))
        RequestHandler.reportCooldowns[player.uniqueId] = System.currentTimeMillis()
        player.sendMessage(Chat.format("&aYour report has been sent to every online staff member!"))

    }
}
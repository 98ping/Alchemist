package ltd.matrixstudios.alchemist.staff.commands

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandPermission
import co.aikar.commands.annotation.Name
import ltd.matrixstudios.alchemist.Alchemist
import ltd.matrixstudios.alchemist.api.AlchemistAPI
import ltd.matrixstudios.alchemist.models.profile.GameProfile
import ltd.matrixstudios.alchemist.packets.StaffGeneralMessagePacket
import ltd.matrixstudios.alchemist.redis.AsynchronousRedisSender
import ltd.matrixstudios.alchemist.service.server.UniqueServerService
import ltd.matrixstudios.alchemist.util.Chat
import ltd.matrixstudios.alchemist.util.NetworkUtil
import ltd.matrixstudios.alchemist.util.TimeUtil
import org.bukkit.entity.Player
import java.util.*

/**
 * Class created on 5/14/2023

 * @author 98ping
 * @project Alchemist
 * @website https://solo.to/redis
 */
class JumpToPlayerCommand : BaseCommand()
{

    @CommandAlias("jumptoplayer|jtp|jumpto")
    @CommandPermission("alchemist.jtp")
    fun jumpTo(player: Player, @Name("target") target: GameProfile)
    {
        val globalServer = Alchemist.globalServer
        val onlineServer = target.metadata.get("server").asString
        val uniqueServer = UniqueServerService.byId(onlineServer.lowercase(Locale.getDefault()))

        if (uniqueServer == null || onlineServer.lowercase(Locale.getDefault()).equals("None", ignoreCase = true))
        {
            player.sendMessage(Chat.format("&6&lServer Jump Request"))
            player.sendMessage(Chat.format("&eTarget: &f" + AlchemistAPI.getRankDisplay(target.uuid)))
            player.sendMessage(Chat.format("&eDestination: &cNone"))
            player.sendMessage(Chat.format("&eLast Seen: &f${TimeUtil.formatIntoCalendarString(Date(target.lastSeenAt))} ago"))
            player.sendMessage(Chat.format("&cUnable to handle. Proxy issue?"))
            return
        }

        val rank = AlchemistAPI.findRank(target.uuid)

        if (rank.weight < target.getCurrentRank().weight)
        {
            player.sendMessage(Chat.format("&cThis player has a higher rank weight than yours!"))
            return
        }

        player.sendMessage(Chat.format("&6&lServer Jump Request"))
        player.sendMessage(Chat.format("&eTarget: &f" + AlchemistAPI.getRankDisplay(target.uuid)))
        player.sendMessage(Chat.format("&eDestination: &f" + uniqueServer.displayName))
        player.sendMessage(Chat.format("&eProxy Name: &f" + uniqueServer.bungeeName))
        player.sendMessage(Chat.format("&aCurrently sending..."))
        NetworkUtil.send(player, uniqueServer.id)
        AsynchronousRedisSender.send(
            StaffGeneralMessagePacket(
                Chat.format(
                    "&b[S] &3[${globalServer.displayName}] &r${
                        AlchemistAPI.getRankDisplay(
                            player.uniqueId
                        )
                    } &3has jumped to &r${target.getRankDisplay()}&3."
                )
            )
        )
    }
}
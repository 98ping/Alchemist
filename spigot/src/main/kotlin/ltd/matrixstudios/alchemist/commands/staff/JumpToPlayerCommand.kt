package ltd.matrixstudios.alchemist.commands.staff

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandPermission
import co.aikar.commands.annotation.Name
import ltd.matrixstudios.alchemist.api.AlchemistAPI
import ltd.matrixstudios.alchemist.models.profile.GameProfile
import ltd.matrixstudios.alchemist.service.ranks.RankService
import ltd.matrixstudios.alchemist.service.server.UniqueServerService
import ltd.matrixstudios.alchemist.util.Chat
import ltd.matrixstudios.alchemist.util.NetworkUtil
import org.bukkit.entity.Player

/**
 * Class created on 5/14/2023

 * @author 98ping
 * @project Alchemist
 * @website https://solo.to/redis
 */
class JumpToPlayerCommand : BaseCommand() {

    @CommandAlias("jumptoplayer|jtp|jumpto")
    @CommandPermission("alchemist.jtp")
    fun jumpTo(player: Player, @Name("target") target: GameProfile)
    {
        val onlineServer = target.metadata.get("server").asString
        val uniqueServer = UniqueServerService.byId(onlineServer.lowercase())

        if (uniqueServer == null)
        {
            player.sendMessage(Chat.format("&cPlayer is not online or server is unable to handle your request"))
            return
        }

        val rank = AlchemistAPI.findRank(target.uuid)

        if (rank.weight < target.getCurrentRank()!!.weight)
        {
            player.sendMessage(Chat.format("&cThis player has a higher rank weight than yours!"))
            return
        }

        player.sendMessage(Chat.format("&aPlayer Found! Currently on &f" + uniqueServer.displayName + " &7(Network abides by " + uniqueServer.bungeeName + ")"))
        NetworkUtil.send(player, uniqueServer.id)
    }
}
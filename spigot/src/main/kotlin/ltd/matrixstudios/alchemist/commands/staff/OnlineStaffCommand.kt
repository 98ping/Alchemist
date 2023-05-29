package ltd.matrixstudios.alchemist.commands.staff

import ltd.matrixstudios.alchemist.Alchemist
import ltd.matrixstudios.alchemist.AlchemistSpigotPlugin
import ltd.matrixstudios.alchemist.servers.*
import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.*
import ltd.matrixstudios.alchemist.api.AlchemistAPI
import ltd.matrixstudios.alchemist.models.profile.GameProfile
import ltd.matrixstudios.alchemist.models.profile.notes.ProfileNote
import ltd.matrixstudios.alchemist.models.server.UniqueServer
import ltd.matrixstudios.alchemist.service.profiles.ProfileGameService
import ltd.matrixstudios.alchemist.service.ranks.RankService
import ltd.matrixstudios.alchemist.service.server.UniqueServerService
import ltd.matrixstudios.alchemist.util.Chat
import org.apache.commons.lang.StringUtils
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.util.StringUtil
import java.util.*
import kotlin.collections.ArrayList

/**
 * Class created on 5/14/2023

 * @author 98ping
 * @project Alchemist
 * @website https://solo.to/redis
 */
class OnlineStaffCommand : BaseCommand() {

    @CommandAlias("onlinestaff|globalstaff|sl|stafflist")
    @CommandPermission("alchemist.staff.list")
    fun onlineStaff(player: Player) {
        val allPlayers = mutableListOf<UUID>()
        val servers = UniqueServerService.getValues()
        val msgs = mutableListOf<String>()

        for (server in servers)
        {
            for (player1 in server.players)
            {
                if (!allPlayers.contains(player1)) {
                    allPlayers.add(player1)
                }
            }
        }

        for (player2 in allPlayers) {
            val profile = AlchemistAPI.syncFindProfile(player2) ?: continue
            val serverName = UniqueServerService.byId(profile.metadata.get("server").asString.lowercase())?.displayName ?: "&cUnknown"

            msgs.add(Chat.format("&7- " + AlchemistAPI.getRankDisplay(profile.uuid) + " &eis currently &aonline &eat &f" + serverName))

        }
        player.sendMessage(Chat.format("&e&lOnline Staff Members&7:"))
        for (msg in msgs) {
            player.sendMessage(Chat.format(msg))
        }
    }

    fun getStaffMembers(server: UniqueServer): List<UUID> {
        val pList = server.players
        val sList = mutableListOf<UUID>()

        for (uuid in pList) {
            if (AlchemistAPI.findRank(uuid).staff) {
                sList.add(uuid)
            }
        }

        return sList
    }


}
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
    fun onlineStaff(player: Player){
        val msgs = mutableListOf<String>()

        val connectedServers = UniqueServerService.getValues()
        val visibleServers = mutableListOf<UniqueServer>()

        for (server in connectedServers){
            val lockedRank = RankService.byId(server.lockRank)
            if (lockedRank != null) {
                if(AlchemistAPI.findRank(player.uniqueId).weight > lockedRank.weight || player.hasPermission("alchemist.owner")){
                    visibleServers.add(server)
                }
            }
        }

        for(server in visibleServers){
            val uncoloredStaffList = getStaffMembers(server)
            val coloredStaffList = ArrayList<String>()

            for(UUID in uncoloredStaffList){
                coloredStaffList.add(AlchemistAPI.getRankDisplay(UUID))
            }

            val coloredStaffString = coloredStaffList.joinToString(
                "&7, ",
            )

            msgs.add(Chat.format("&e&l" + server.displayName + "&7 (ID: " + server.id + "&7) " + coloredStaffString))
        }


        for(msg in msgs){
            player.sendMessage(Chat.format(msg))
        }
    }

    fun getStaffMembers(server: UniqueServer): List<UUID>{
        val pList = server.players
        val sList = mutableListOf<UUID>()

        for (uuid in pList){
            if(AlchemistAPI.findRank(uuid).staff){
                sList.add(uuid)
            }
        }

        return sList
    }



}
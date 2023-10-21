package ltd.matrixstudios.alchemist.staff.commands

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandPermission
import ltd.matrixstudios.alchemist.models.profile.GameProfile
import ltd.matrixstudios.alchemist.punishments.PunishmentType
import ltd.matrixstudios.alchemist.service.expirable.PunishmentService
import ltd.matrixstudios.alchemist.service.profiles.ProfileGameService
import ltd.matrixstudios.alchemist.staff.commands.menu.StaffLeaderboardMenu
import ltd.matrixstudios.alchemist.util.Chat
import ltd.matrixstudios.alchemist.util.menu.Button
import org.bukkit.entity.Player
import java.util.concurrent.CompletableFuture

/**
 * Class created on 9/16/2023

 * @author 98ping
 * @project Alchemist
 * @website https://solo.to/redis
 */
object StaffLeaderboardCommand : BaseCommand()
{
    @CommandAlias("staffleaderboard")
    @CommandPermission("alchemist.staff.leaderboards")
    fun staffLeaderboard(player: Player): CompletableFuture<Void>
    {
        player.sendMessage(Chat.format("&eOpening up the &astaff leaderboard&e! &ePlease wait, this may take a moment."))

        return ProfileGameService.handler.retrieveAllAsync().thenAcceptAsync {
            val mutableList = it.toMutableList()
            val types = listOf(
                PunishmentType.WARN,
                PunishmentType.MUTE,
                PunishmentType.GHOST_MUTE,
                PunishmentType.BAN,
                PunishmentType.BLACKLIST
            )

            exclusivelyLoadEntries(types, mutableList).whenComplete { t, v ->
                StaffLeaderboardMenu(player, t).openMenu()
            }
        }
    }

    fun exclusivelyLoadEntries(
        types: List<PunishmentType>,
        users: MutableList<GameProfile>
    ): CompletableFuture<MutableMap<Int, Button>>
    {
        val toLoad = mutableMapOf<Int, Button>()
        var i = 11

        for (type in types)
        {
            toLoad[i++] = StaffLeaderboardMenu.LeaderboardPunishmentButton(type, fetchFirst10Entries(type, users))
        }

        return CompletableFuture.completedFuture(toLoad)
    }


    fun fetchFirst10Entries(
        type: PunishmentType, toPickFrom: MutableList<GameProfile>
    ): MutableList<GameProfile>
    {
        return mutableListOf<GameProfile>().apply {
            val toAdd = toPickFrom
                .sortedByDescending {
                    PunishmentService.findExecutorPunishments(it.uuid)
                        .count { punishment -> punishment.punishmentType == type.name }
                }.take(10)
            this.addAll(toAdd)
        }
    }
}
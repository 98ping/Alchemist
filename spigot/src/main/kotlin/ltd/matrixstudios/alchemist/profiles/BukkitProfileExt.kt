package ltd.matrixstudios.alchemist.profiles

import ltd.matrixstudios.alchemist.api.AlchemistAPI
import ltd.matrixstudios.alchemist.models.profile.GameProfile
import ltd.matrixstudios.alchemist.models.ranks.Rank
import ltd.matrixstudios.alchemist.punishment.BukkitPunishmentFunctions
import ltd.matrixstudios.alchemist.service.ranks.RankService
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import java.util.*

/**
 * Class created on 6/30/2023

 * @author 98ping
 * @project Alchemist
 * @website https://solo.to/redis
 */

fun Player.getProfile(): GameProfile? {
    return AlchemistAPI.syncFindProfile(uniqueId)
}

fun Player.getRankDisplay(): String {
    return AlchemistAPI.getRankDisplay(uniqueId)
}

fun Player.getCurrentRank(): Rank {
    val profile = AlchemistAPI.syncFindProfile(player.uniqueId) ?: return RankService.FALLBACK_RANK

    return profile.getCurrentRank()
}

fun CommandSender.getRankDisplay(): String {
    var finalString = "${AlchemistAPI.CONSOLE_COLOR}Console"

    val profile = AlchemistAPI.syncFindProfile(BukkitPunishmentFunctions.getSenderUUID(this))

    if (profile != null) {
        finalString = profile.getCurrentRank().color + profile.username
    }

    return finalString

}

fun Player.getRankDisplayWithPrefix(): String {
    return AlchemistAPI.getRankWithPrefix(uniqueId)
}
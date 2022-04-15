package ltd.matrixstudios.alchemist.api

import ltd.matrixstudios.alchemist.service.profiles.ProfileGameService
import java.util.*

object AlchemistAPI {

    fun getRankDisplay(uuid: UUID) : String {
        val profile = ProfileGameService.byId(uuid) ?: return "&cNot Found"

        return profile.getCurrentRank()!!.color + profile.username
    }

    fun getWoolColor(str: String): Short {
        if (str.contains("&1") || str.contains("&9")) return 11
        if (str.contains("&2")) return 13
        if (str.contains("&3")) return 9
        if (str.contains("&4") || str.contains("&c")) return 14
        if (str.contains("&5")) return 10
        if (str.contains("&6")) return 1
        if (str.contains("&7")) return 8
        if (str.contains("&8")) return 7
        if (str.contains("&a")) return 5
        if (str.contains("&b")) return 3
        if (str.contains("&d")) return 6
        return if (str.contains("&e")) 4 else 0
    }
}
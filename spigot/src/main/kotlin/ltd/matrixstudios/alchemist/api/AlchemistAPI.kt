package ltd.matrixstudios.alchemist.api

import ltd.matrixstudios.alchemist.models.profile.GameProfile
import ltd.matrixstudios.alchemist.service.profiles.ProfileGameService
import sun.java2d.cmm.Profile
import java.util.*

object AlchemistAPI {

    fun getRankDisplay(uuid: UUID) : String {
        val profile = ProfileGameService.byId(uuid) ?: return "&cNot Found"

        return profile.getCurrentRank()!!.color + profile.username
    }

    fun quickFindProfile(uuid: UUID) : GameProfile? {
        return ProfileGameService.byId(uuid)
    }

    fun getWoolColor(str: String): Short { //for punishments
        if (str.contains("&4") || str.contains("&c")) return 14
        if (str.contains("&6")) return 1
        return if (str.contains("&e")) 4 else 0
    }
}
package ltd.matrixstudios.alchemist.api

import ltd.matrixstudios.alchemist.service.profiles.ProfileGameService
import java.util.*

object AlchemistAPI {

    fun getRankDisplay(uuid: UUID) : String {
        val profile = ProfileGameService.byId(uuid) ?: return "&cNot Found"

        return profile.getCurrentRank()!!.color + profile.username
    }
}
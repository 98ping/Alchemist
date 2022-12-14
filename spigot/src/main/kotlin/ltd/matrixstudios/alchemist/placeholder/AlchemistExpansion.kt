package ltd.matrixstudios.alchemist.placeholder

import ltd.matrixstudios.alchemist.api.AlchemistAPI
import ltd.matrixstudios.alchemist.service.profiles.ProfileGameService
import me.clip.placeholderapi.expansion.PlaceholderExpansion
import org.bukkit.entity.Player

class AlchemistExpansion : PlaceholderExpansion() {
    override fun getIdentifier(): String {
        return "alchemist"
    }

    override fun getAuthor(): String {
        return "Matrix Studios"
    }

    override fun getVersion(): String {
        return "1.0.0"
    }

    override fun onPlaceholderRequest(player: Player?, params: String): String? {
        val rank = AlchemistAPI.findRank(player!!.uniqueId)
        val profile = ProfileGameService.byId(player.uniqueId) ?: return ""

        when (params) {
            "rankDisplay" -> {
                return rank.color + rank.displayName
            }

            "rankPrefix" -> {
                return rank.prefix
            }

            "activeTag" -> {
                return profile.getActivePrefix()?.prefix ?: ""
            }
        }

        return ""
    }
}
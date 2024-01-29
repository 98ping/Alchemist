package ltd.matrixstudios.alchemist.models.ranks

import ltd.matrixstudios.alchemist.models.ranks.scope.RankScope
import ltd.matrixstudios.alchemist.service.ranks.RankService
import java.util.*


class Rank(
    var id: String,
    var name: String,
    var displayName: String,

    var weight: Int,
    var permissions: ArrayList<String>,
    var parents: ArrayList<String>,

    var prefix: String,
    var color: String,
    var staff: Boolean = false,
    var default: Boolean = false,
    var woolColor: String? = null,
    var scope: RankScope? = null,
    var discordRoleId: String? = null
) {

    fun getRankScope() : RankScope {
        if (scope != null) return scope!!

        return RankScope(mutableListOf(), true)
    }

    fun getHexCodeFromColorCode() : String {
        if (color.contains("#")) return color;

        when (color) {
            "&c" -> {
                return "#eb4723"
            }
            "&4" -> {
                return "#f50c18"
            }
            "&e" -> {
                return "#FBE503"
            }
            "&a" -> {
                return "#34FB03"
            }
            "&2" -> {
                return "#21810A"
            }
            "&9" -> {
                return "#0B4ECB"
            }
            "&b" -> {
                return "#0BBFCB"
            }
            "&3" -> {
                return "#54A8AE"
            }
            "&6" -> {
                return "#C2900B"
            }
            "&d" -> {
                return "#EEACE0"
            }
            "&7" -> {
                return "#9D9D9D"
            }
            "&0" -> {
                return "#000000"
            }
            "&5" -> {
                return "#D51CBC"
            }
        }

        return "#FFFFFF"
    }

    fun getAllPermissions() : MutableList<String> {
        val perms = mutableListOf<String>()

        perms.addAll(permissions)

        for (parent in parents)
        {
            val rank = RankService.byId(parent) ?: continue

            for (permission in rank.permissions)
            {
                if (!perms.contains(permission))
                {
                    perms.add(permission)
                }
            }
        }

        return perms
    }
}

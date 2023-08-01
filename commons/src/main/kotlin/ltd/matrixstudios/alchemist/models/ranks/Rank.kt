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
    var scope: RankScope? = null
) {

    fun getRankScope() : RankScope {
        if (scope != null) return scope!!

        return RankScope(mutableListOf(), true)
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

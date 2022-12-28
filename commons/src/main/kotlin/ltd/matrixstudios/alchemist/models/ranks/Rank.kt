package ltd.matrixstudios.alchemist.models.ranks

import ltd.matrixstudios.alchemist.service.ranks.RankService
import sun.security.ec.point.ProjectivePoint.Mutable
import java.util.*
import kotlin.collections.ArrayList


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
    var default: Boolean = false
) {

    fun getAllPermissions() : MutableList<String> {
        val perms = mutableListOf<String>()

        perms.addAll(permissions)

        for (parent in parents)
        {
            val rank = RankService.byId(parent) ?: continue

            for (perm in rank.getAllPermissions())
            {
                if (!perms.contains(perm))
                {
                    perms.add(perm)
                }
            }
        }

        return perms
    }
}

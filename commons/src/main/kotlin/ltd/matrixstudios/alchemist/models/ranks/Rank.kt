package ltd.matrixstudios.alchemist.models.ranks

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
}

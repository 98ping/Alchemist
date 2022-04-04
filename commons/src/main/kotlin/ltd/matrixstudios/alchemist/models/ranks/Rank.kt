package ltd.matrixstudios.alchemist.models.ranks

import java.util.*


class Rank(
    var id: String,
    var name: String,
    var displayName: String,

    var weight: Int,
    var permissions: List<String>,
    var parents: List<String>,

    var prefix: String,
    var color: String,
    var staff: Boolean = false,
    var default: Boolean = false
) {
}

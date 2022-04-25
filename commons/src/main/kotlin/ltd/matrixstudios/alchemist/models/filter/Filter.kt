package ltd.matrixstudios.alchemist.models.filter

import java.util.*

data class Filter(
    var id: UUID,
    var word: String,
    var offense: FilterOffense,
    var command: String
) {

    enum class FilterOffense {
        MUTE, KICK, NOTHING
    }
}
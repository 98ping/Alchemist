package ltd.matrixstudios.alchemist.models.filter

import ltd.matrixstudios.alchemist.punishments.PunishmentType
import java.util.*

data class Filter(
    var id: UUID,
    var word: String,
    var silent: Boolean,
    var punishmentType: PunishmentType,
    var duration: String,
    var staffExempt: Boolean,
    var exemptPermission: String,
    var shouldPunish: Boolean,
) {

}
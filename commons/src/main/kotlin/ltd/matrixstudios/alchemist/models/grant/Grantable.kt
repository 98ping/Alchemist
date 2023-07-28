package ltd.matrixstudios.alchemist.models.grant

import ltd.matrixstudios.alchemist.models.expirables.Expirable
import java.util.*

abstract class Grantable<T>(
    var uuid: UUID,
    var target: UUID,
    var executor: UUID,
    var reason: String,
    var expirable: Expirable,
    var removedBy: UUID?,
    var removedReason: String?,
){

    abstract fun getGrantable(): T?
}
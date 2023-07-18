package ltd.matrixstudios.alchemist.models.expirables

import java.util.*

class Expirable(
    var expired: Boolean,
    var addedAt: Long,
    var duration: Long,
    var removedAt: Long
) {
    fun isActive() : Boolean {
        if (duration != -1L && (this.addedAt + this.duration) - System.currentTimeMillis() <= 0) {
            expired = true
            removedAt = System.currentTimeMillis()
        }

        return !this.expired
    }

    fun getActiveUntil(): Long {
        return if (duration == Long.MAX_VALUE) Long.MAX_VALUE else addedAt + duration
    }
}
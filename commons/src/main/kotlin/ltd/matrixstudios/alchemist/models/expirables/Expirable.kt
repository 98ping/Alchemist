package ltd.matrixstudios.alchemist.models.expirables

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
}
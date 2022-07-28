package ltd.matrixstudios.alchemist.networking

import ltd.matrixstudios.alchemist.api.AlchemistAPI
import java.util.*
import java.util.concurrent.TimeUnit

object NetworkManager {

    fun hasFullyDCed(target: UUID) : Boolean {
        var offline = false
        AlchemistAPI.quickFindProfile(target).thenApply {
            if (!it!!.isOnline() && System.currentTimeMillis().minus(it.lastSeenAt) >= TimeUnit.SECONDS.toMillis(30L)) {
                offline = true
            } else if (it.isOnline())  {
                offline = false
            }
        }

        return offline
    }
}
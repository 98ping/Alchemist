package ltd.matrixstudios.alchemist.networking

import ltd.matrixstudios.alchemist.api.AlchemistAPI
import java.util.*

object NetworkManager {

    fun hasFullyDCed(target: UUID) : Boolean {
        var online = false
        AlchemistAPI.quickFindProfile(target).thenApply {
            if (it!!.isOnline()) {
                online = true
            } else if (!it.isOnline())  {
                online = false
            }
        }

        return online
    }
}
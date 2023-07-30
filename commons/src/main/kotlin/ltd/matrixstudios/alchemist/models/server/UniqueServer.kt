package ltd.matrixstudios.alchemist.models.server

import ltd.matrixstudios.alchemist.models.server.software.ServerSoftware
import java.util.*
import kotlin.collections.ArrayList

data class UniqueServer(
    var id: String,
    var displayName: String,
    var queueName: String,
    var players: ArrayList<UUID>,
    var online: Boolean,
    var ramAllocated: Int,
    var bungeeName: String,
    var setToRelease: Long = -1L,
    var lockedWithRank: Boolean = false,
    var lockRank: String,
    var lastHeartbeat: Long,
    var serverSoftware: ServerSoftware? = null
) {
    fun findServerSoftware() : ServerSoftware {
        if (serverSoftware != null) {
            return serverSoftware!!
        }

        return ServerSoftware("Unknown", mutableListOf())
    }
}

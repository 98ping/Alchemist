package ltd.matrixstudios.alchemist.models.server

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
    var lockedWithRank: Boolean,
    var lockRank: String,
    var lastHeartbeat: Long,
)

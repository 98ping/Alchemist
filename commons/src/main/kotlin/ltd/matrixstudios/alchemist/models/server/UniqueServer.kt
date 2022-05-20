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
    var lockedWithPermission: Boolean,
    var lockPermission: String,
    var lastHeartbeat: Long,
)

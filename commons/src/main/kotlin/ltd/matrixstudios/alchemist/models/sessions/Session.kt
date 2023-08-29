package ltd.matrixstudios.alchemist.models.sessions

import ltd.matrixstudios.alchemist.models.server.UniqueServer
import java.util.*

data class Session(
    var randomId: String,
    var player: UUID,

    var serversJoined: MutableMap<Long, UniqueServer>,
    var loggedInAt: Long,
    var leftAt: Long,
)

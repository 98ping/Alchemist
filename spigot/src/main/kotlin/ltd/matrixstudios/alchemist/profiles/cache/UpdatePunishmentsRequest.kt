package ltd.matrixstudios.alchemist.profiles.cache

import ltd.matrixstudios.alchemist.redis.RedisPacket
import ltd.matrixstudios.alchemist.service.expirable.PunishmentService
import java.util.*

class UpdatePunishmentsRequest(val target: UUID) : RedisPacket("punishment-sync-update") {

    override fun action() {
        PunishmentService.recalculateUUID(target)
    }
}
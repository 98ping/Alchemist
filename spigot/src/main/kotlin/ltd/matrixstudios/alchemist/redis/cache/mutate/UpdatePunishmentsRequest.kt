package ltd.matrixstudios.alchemist.redis.cache.mutate

import ltd.matrixstudios.alchemist.redis.RedisPacket
import ltd.matrixstudios.alchemist.service.expirable.PunishmentService
import java.util.*

class UpdatePunishmentsRequest(val target: UUID) : RedisPacket("punishment-sync-update") {

    override fun action() {
        PunishmentService.recalculateUUID(target)
    }
}
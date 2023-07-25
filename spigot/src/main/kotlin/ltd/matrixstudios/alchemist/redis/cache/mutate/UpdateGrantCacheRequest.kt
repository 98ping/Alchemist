package ltd.matrixstudios.alchemist.redis.cache.mutate

import ltd.matrixstudios.alchemist.redis.RedisPacket
import ltd.matrixstudios.alchemist.service.expirable.RankGrantService
import java.util.*

class UpdateGrantCacheRequest(val target: UUID) : RedisPacket("grant-sync-update") {

    override fun action() {
        RankGrantService.recalculateUUID(target)
    }
}
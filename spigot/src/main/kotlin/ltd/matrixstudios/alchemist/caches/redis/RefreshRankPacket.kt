package ltd.matrixstudios.alchemist.caches.redis

import ltd.matrixstudios.alchemist.redis.RedisPacket
import ltd.matrixstudios.alchemist.service.ranks.RankService

class RefreshRankPacket : RedisPacket("refresh-rank") {

    override fun action() {
        val cache = RankService.ranks

        cache.clear()

        for (rank in RankService.getValues())
        {
            cache[rank.id] = rank
        }
    }
}
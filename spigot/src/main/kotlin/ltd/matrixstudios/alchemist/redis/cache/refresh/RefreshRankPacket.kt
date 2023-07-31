package ltd.matrixstudios.alchemist.redis.cache.refresh

import ltd.matrixstudios.alchemist.redis.RedisPacket
import ltd.matrixstudios.alchemist.service.ranks.RankService

class RefreshRankPacket : RedisPacket("refresh-rank") {

    override fun action() {
        val cache = RankService.ranks

        cache.clear()

        RankService.getValues().whenComplete { t, u ->
            for (rank in t) {
                RankService.ranks[rank.id] = rank
            }
        }
    }
}
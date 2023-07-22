package ltd.matrixstudios.alchemist.profiles.cache

import ltd.matrixstudios.alchemist.redis.RedisPacket
import ltd.matrixstudios.alchemist.service.ranks.RankService

class RefreshRankPacket : RedisPacket("refresh-rank") {

    override fun action() {
        val cache = RankService.ranks

        cache.clear()

        RankService.getValues().thenAccept {
            for (rank in it) {
                cache[rank.id] = rank
            }
        }
    }
}
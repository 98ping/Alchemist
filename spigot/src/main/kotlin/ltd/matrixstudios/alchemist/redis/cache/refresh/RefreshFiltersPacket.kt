package ltd.matrixstudios.alchemist.redis.cache.refresh

import ltd.matrixstudios.alchemist.redis.RedisPacket
import ltd.matrixstudios.alchemist.service.filter.FilterService

class RefreshFiltersPacket : RedisPacket("refresh-filters") {

    override fun action() {
        val cache = FilterService.cache

        cache.clear()

        FilterService.handler.retrieveAllAsync().thenAccept {
            for (item in it) {
                cache[item.word] = item
            }
        }
    }
}
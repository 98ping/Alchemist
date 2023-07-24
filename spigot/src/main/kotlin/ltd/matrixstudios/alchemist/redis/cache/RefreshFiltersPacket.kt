package ltd.matrixstudios.alchemist.redis.cache

import ltd.matrixstudios.alchemist.redis.RedisPacket
import ltd.matrixstudios.alchemist.service.filter.FilterService

class RefreshFiltersPacket : RedisPacket("refresh-filters") {

    override fun action() {
        val cache = FilterService.cache

        cache.clear()

        for (filter in FilterService.getValues())
        {
            cache[filter.word] = filter
        }
    }
}
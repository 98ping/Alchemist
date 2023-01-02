package ltd.matrixstudios.alchemist.caches.redis

import ltd.matrixstudios.alchemist.models.profile.GameProfile
import ltd.matrixstudios.alchemist.redis.RedisPacket
import ltd.matrixstudios.alchemist.service.filter.FilterService
import ltd.matrixstudios.alchemist.service.profiles.ProfileGameService
import ltd.matrixstudios.alchemist.service.ranks.RankService

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
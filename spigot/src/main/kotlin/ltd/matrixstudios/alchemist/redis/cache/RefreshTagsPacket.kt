package ltd.matrixstudios.alchemist.redis.cache

import ltd.matrixstudios.alchemist.redis.RedisPacket
import ltd.matrixstudios.alchemist.service.filter.FilterService
import ltd.matrixstudios.alchemist.service.tags.TagService

class RefreshTagsPacket : RedisPacket("refresh-tags") {

    override fun action() {
        val cache = TagService.cache

        cache.clear()

        TagService.getValues().thenAccept {
            for (tag in it) {
                cache[tag.id] = tag
            }
        }
    }
}
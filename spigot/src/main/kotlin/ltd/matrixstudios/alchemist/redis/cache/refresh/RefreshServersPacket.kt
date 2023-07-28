package ltd.matrixstudios.alchemist.redis.cache.refresh

import ltd.matrixstudios.alchemist.redis.RedisPacket
import ltd.matrixstudios.alchemist.service.filter.FilterService
import ltd.matrixstudios.alchemist.service.server.UniqueServerService
import ltd.matrixstudios.alchemist.service.tags.TagService

class RefreshServersPacket : RedisPacket("refresh-tags") {

    override fun action() {
        val cache = UniqueServerService.servers

        cache.clear()

        UniqueServerService.handler.retrieveAllAsync().thenAccept {
            for (server in it) {
                cache[server.id] = server
            }
        }
    }
}
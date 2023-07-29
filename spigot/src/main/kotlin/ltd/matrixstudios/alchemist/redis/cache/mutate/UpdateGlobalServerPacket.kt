package ltd.matrixstudios.alchemist.redis.cache.mutate

import ltd.matrixstudios.alchemist.Alchemist
import ltd.matrixstudios.alchemist.models.server.UniqueServer
import ltd.matrixstudios.alchemist.redis.RedisPacket

/**
 * Class created on 7/29/2023

 * @author 98ping
 * @project Alchemist
 * @website https://solo.to/redis
 */
class UpdateGlobalServerPacket(val server: UniqueServer) : RedisPacket("update-global-server") {
    override fun action() {
        if (Alchemist.globalServer.id == server.id) {
            Alchemist.globalServer = server
        }
    }
}
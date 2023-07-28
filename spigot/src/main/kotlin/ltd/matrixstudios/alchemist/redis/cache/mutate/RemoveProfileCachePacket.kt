package ltd.matrixstudios.alchemist.redis.cache.mutate

import ltd.matrixstudios.alchemist.models.profile.GameProfile
import ltd.matrixstudios.alchemist.redis.RedisPacket
import ltd.matrixstudios.alchemist.service.profiles.ProfileGameService

class RemoveProfileCachePacket(val gameprofile: GameProfile) : RedisPacket("remove-from-cache") {

    override fun action() {
        val cache = ProfileGameService.cache

        if (cache.containsKey(gameprofile.uuid)) {
            cache.remove(gameprofile.uuid)
        }
    }
}
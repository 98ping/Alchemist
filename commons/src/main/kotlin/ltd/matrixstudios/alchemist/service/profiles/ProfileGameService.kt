package ltd.matrixstudios.alchemist.service.profiles

import io.github.nosequel.data.DataStoreType
import ltd.matrixstudios.alchemist.Alchemist
import ltd.matrixstudios.alchemist.models.profile.GameProfile
import ltd.matrixstudios.alchemist.redis.RedisPacketManager
import java.util.*
import java.util.concurrent.CompletableFuture
import java.util.concurrent.TimeUnit
import javax.security.auth.callback.Callback

object ProfileGameService {


    var handler = Alchemist.dataHandler.createStoreType<UUID, GameProfile>(DataStoreType.MONGO)

    var cache = hashMapOf<UUID, GameProfile?>()

    fun byId(uuid: UUID) : GameProfile? {
        return ProfileSearchService.getAsync(uuid).get()!!
    }
    

    fun save(gameProfile: GameProfile) {
        cache[gameProfile.uuid] = gameProfile

        handler.store(gameProfile.uuid, gameProfile)

    }

}
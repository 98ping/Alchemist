package ltd.matrixstudios.alchemist.service.profiles

import io.github.nosequel.data.DataStoreType
import ltd.matrixstudios.alchemist.Alchemist
import ltd.matrixstudios.alchemist.models.profile.GameProfile
import java.util.*

object ProfileGameService {


    var handler = Alchemist.dataHandler.createStoreType<UUID, GameProfile>(DataStoreType.MONGO)


    fun getValues(): Collection<GameProfile> {
        return handler.retrieveAll()
    }

    fun save(profile: GameProfile) {
        handler.store(profile.uuid, profile)
    }

    fun byId(id: UUID): GameProfile? {
        return getValues().firstOrNull { it.uuid == id }
    }


    fun byName(name: String): GameProfile? {
        return getValues().firstOrNull { it.username.equals(name, ignoreCase = true) }
    }

}
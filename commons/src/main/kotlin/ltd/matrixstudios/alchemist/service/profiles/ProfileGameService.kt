package ltd.matrixstudios.alchemist.service.profiles

import com.mongodb.client.model.Filters
import ltd.matrixstudios.alchemist.Alchemist
import ltd.matrixstudios.alchemist.models.profile.GameProfile
import java.util.*

object ProfileGameService {

    fun getValues(): List<GameProfile> {
        return Alchemist.dataflow.createQuery(GameProfile::class.java).toList()
    }

    fun save(element: GameProfile) {
        Alchemist.dataflow.save(element.uuid.toString(), element, GameProfile::class.java)
    }

    fun byId(uuid: UUID): GameProfile {
        return getValues().first { it.uuid == uuid }
    }
}
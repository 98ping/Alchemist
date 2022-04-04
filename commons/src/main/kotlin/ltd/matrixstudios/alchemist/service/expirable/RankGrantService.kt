package ltd.matrixstudios.alchemist.service.expirable

import io.github.nosequel.data.DataStoreType
import ltd.matrixstudios.alchemist.Alchemist
import ltd.matrixstudios.alchemist.models.grant.types.Punishment
import ltd.matrixstudios.alchemist.models.grant.types.RankGrant
import ltd.matrixstudios.alchemist.punishments.PunishmentType
import ltd.matrixstudios.alchemist.punishments.actor.ActorType
import java.util.*

object RankGrantService : ExpiringService<RankGrant>() {

    var handler = Alchemist.dataHandler.createStoreType<UUID, RankGrant>(DataStoreType.MONGO)


    fun getValues() : Collection<RankGrant> {
        return handler.retrieveAll()
    }

    fun save(rankGrant: RankGrant) {
        handler.store(rankGrant.uuid, rankGrant)
    }

    fun findByTarget(target: UUID) : Collection<RankGrant> {
        return getValues().filter { it.target == target }
    }


    override fun clearOutModels() {
        getValues().forEach {
            if (!it.expirable.isActive() && it.removedBy == null) {
                it.removedBy = UUID.fromString("00000000-0000-0000-0000-000000000000")
                it.removedReason = "Expired"

                save(it)
            }
        }
    }

}
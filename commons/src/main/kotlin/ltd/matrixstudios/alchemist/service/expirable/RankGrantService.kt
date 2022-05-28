package ltd.matrixstudios.alchemist.service.expirable

import com.mongodb.client.model.Filters
import io.github.nosequel.data.DataStoreType
import ltd.matrixstudios.alchemist.Alchemist
import ltd.matrixstudios.alchemist.models.grant.types.Punishment
import ltd.matrixstudios.alchemist.models.grant.types.RankGrant
import ltd.matrixstudios.alchemist.models.profile.GameProfile
import ltd.matrixstudios.alchemist.models.ranks.Rank
import ltd.matrixstudios.alchemist.punishments.PunishmentType
import ltd.matrixstudios.alchemist.punishments.actor.ActorType
import ltd.matrixstudios.alchemist.redis.RedisPacketManager
import java.util.*
import java.util.concurrent.CompletableFuture

object RankGrantService : ExpiringService<RankGrant>() {

    var handler = Alchemist.dataHandler.createStoreType<UUID, RankGrant>(DataStoreType.MONGO)


    fun getValues() : CompletableFuture<Collection<RankGrant>> {
        return handler.retrieveAllAsync()
    }

    fun save(rankGrant: RankGrant) {
        handler.storeAsync(rankGrant.uuid, rankGrant)
    }

    fun findByTarget(target: UUID) : Collection<RankGrant> {
        return getValues().get().filter { it.target == target }
    }


    override fun clearOutModels() {
        getValues().get().forEach {
            if (!it.expirable.isActive() && it.removedBy == null) {
                it.removedBy = UUID.fromString("00000000-0000-0000-0000-000000000000")
                it.removedReason = "Expired"

                save(it)
            }
        }
    }
}
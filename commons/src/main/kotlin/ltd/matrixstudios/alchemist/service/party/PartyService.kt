package ltd.matrixstudios.alchemist.service.party

import io.github.nosequel.data.DataStoreType
import io.github.nosequel.data.store.type.MongoStoreType
import ltd.matrixstudios.alchemist.Alchemist
import ltd.matrixstudios.alchemist.models.filter.Filter
import ltd.matrixstudios.alchemist.models.party.Party
import ltd.matrixstudios.alchemist.mongo.MongoStorageCache
import ltd.matrixstudios.alchemist.service.GeneralizedService
import java.util.*
import java.util.concurrent.CompletableFuture

object PartyService : GeneralizedService {

    var handler = MongoStorageCache.create<UUID, Party>("party")
    private val backingPartyCache = mutableMapOf<UUID, Party>()

    fun getParty(uuid: UUID): CompletableFuture<Party?>
    {
        for (party in backingPartyCache.values)
        {
            if (party.leader == uuid
                ||
                party.members.firstOrNull {
                    it.first.toString() == uuid.toString()
                } != null
            ) {
                return CompletableFuture.completedFuture(party)
            }
        }

        return handler.getAll().thenApply { parties ->
            for (mongoParty in parties)
            {
                if (mongoParty.members.any {
                        it.first.toString() == uuid.toString()
                } || mongoParty.leader == uuid)
                {
                    backingPartyCache[mongoParty.id] = mongoParty

                    return@thenApply mongoParty
                }
            }

            null
        }
    }
}
package ltd.matrixstudios.alchemist.service.party

import io.github.nosequel.data.DataStoreType
import ltd.matrixstudios.alchemist.Alchemist
import ltd.matrixstudios.alchemist.models.filter.Filter
import ltd.matrixstudios.alchemist.models.party.Party
import ltd.matrixstudios.alchemist.service.GeneralizedService
import java.util.*

object PartyService : GeneralizedService {

    var handler = Alchemist.dataHandler.createStoreType<UUID, Party>(DataStoreType.MONGO)


    fun getValues(): Collection<Party> {
        return handler.retrieveAll()
    }

    fun getParty(uuid: UUID): Party? {
        for (party in getValues()) {
            if (party.leader == uuid
                || party.members.firstOrNull {
                    it.first.toString() == uuid.toString()
                } != null
            ) {
                return party
            }
        }

        return null
    }
}
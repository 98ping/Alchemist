package ltd.matrixstudios.alchemist.service.party

import com.sun.xml.internal.ws.wsdl.writer.document.Part
import io.github.nosequel.data.DataStoreType
import ltd.matrixstudios.alchemist.Alchemist
import ltd.matrixstudios.alchemist.models.filter.Filter
import ltd.matrixstudios.alchemist.models.party.Party
import java.util.*

object PartyService {

    var handler = Alchemist.dataHandler.createStoreType<UUID, Party>(DataStoreType.REDIS)


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
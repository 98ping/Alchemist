package ltd.matrixstudios.alchemist.service.server

import io.github.nosequel.data.DataStoreType
import ltd.matrixstudios.alchemist.Alchemist
import ltd.matrixstudios.alchemist.models.server.UniqueServer
import ltd.matrixstudios.alchemist.service.GeneralizedService

object UniqueServerService : GeneralizedService {

    var handler = Alchemist.dataHandler.createStoreType<String, UniqueServer>(DataStoreType.MONGO)


    fun getValues() : Collection<UniqueServer> {
        return handler.retrieveAll()
    }

    fun save(server: UniqueServer) {
        handler.storeAsync(server.id, server)
        Alchemist.globalServer = server
    }

    fun byId(id: String) : UniqueServer? {
        return getValues().firstOrNull { it.id.equals(id, ignoreCase = true) }
    }
}
package ltd.matrixstudios.alchemist.service.server

import io.github.nosequel.data.DataStoreType
import ltd.matrixstudios.alchemist.Alchemist
import ltd.matrixstudios.alchemist.models.ranks.Rank
import ltd.matrixstudios.alchemist.models.server.UniqueServer
import java.util.*
import java.util.stream.Collectors
import kotlin.collections.ArrayList

object UniqueServerService  {

    var handler = Alchemist.dataHandler.createStoreType<String, UniqueServer>(DataStoreType.MONGO)


    fun getValues() : Collection<UniqueServer> {
        return handler.retrieveAll()
    }

    fun save(server: UniqueServer) {
        handler.storeAsync(server.id, server)
    }

    fun byId(id: String) : UniqueServer? {
        return getValues().firstOrNull { it.id.equals(id, ignoreCase = true) }
    }
}
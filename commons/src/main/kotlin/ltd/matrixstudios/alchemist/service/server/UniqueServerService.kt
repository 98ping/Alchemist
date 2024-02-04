package ltd.matrixstudios.alchemist.service.server

import io.github.nosequel.data.DataStoreType
import ltd.matrixstudios.alchemist.Alchemist
import ltd.matrixstudios.alchemist.models.server.UniqueServer
import ltd.matrixstudios.alchemist.service.GeneralizedService
import java.util.concurrent.CompletableFuture
import java.util.concurrent.ConcurrentHashMap

object UniqueServerService : GeneralizedService {

    var handler = Alchemist.dataHandler.createStoreType<String, UniqueServer>(Alchemist.getDataStoreMethod())

    val servers = ConcurrentHashMap<String, UniqueServer>()

    fun loadAll() {
        val list = handler.retrieveAll()

        for (server in list) {
            servers[server.id] = server
        }
    }

    fun getValues() : Collection<UniqueServer> {
        return servers.values
    }

    fun save(server: UniqueServer) {
        CompletableFuture.runAsync {
            handler.store(server.id, server)
        }

        servers[server.id] = server
    }

    fun updateGlobalServer(server: UniqueServer) {
        Alchemist.globalServer = server
    }

    fun byId(id: String) : UniqueServer? {
        if (servers.containsKey(id)) return servers[id]

        return handler.retrieve(id)
    }
}
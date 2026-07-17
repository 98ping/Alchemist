package ltd.matrixstudios.alchemist.service.server

import ltd.matrixstudios.alchemist.mongo.MongoStore
import ltd.matrixstudios.alchemist.Alchemist
import ltd.matrixstudios.alchemist.models.server.UniqueServer
import ltd.matrixstudios.alchemist.service.GeneralizedService
import java.util.concurrent.CompletableFuture
import java.util.concurrent.ConcurrentHashMap

object UniqueServerService : GeneralizedService
{

    var handler = MongoStore<String, UniqueServer>("uniqueserver", UniqueServer::class.java)

    val servers = ConcurrentHashMap<String, UniqueServer>()

    fun loadAll()
    {
        for (server in handler.retrieveAll())
        {
            servers[server.id] = server
        }
    }

    fun getValues(): Collection<UniqueServer>
    {
        return servers.values
    }

    fun save(server: UniqueServer)
    {
        servers[server.id] = server

        CompletableFuture.runAsync {
            handler.store(server.id, server)
        }
    }

    fun updateGlobalServer(server: UniqueServer)
    {
        Alchemist.globalServer = server
    }

    fun byId(id: String): UniqueServer?
    {
        if (servers.containsKey(id)) return servers[id]

        return handler.retrieve(id)
    }
}
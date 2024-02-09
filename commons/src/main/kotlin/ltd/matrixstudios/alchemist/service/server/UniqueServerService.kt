package ltd.matrixstudios.alchemist.service.server

import com.google.gson.reflect.TypeToken
import io.github.nosequel.data.DataStoreType
import ltd.matrixstudios.alchemist.Alchemist
import ltd.matrixstudios.alchemist.flatfile.FlatfileUtilities
import ltd.matrixstudios.alchemist.models.ranks.Rank
import ltd.matrixstudios.alchemist.models.server.UniqueServer
import ltd.matrixstudios.alchemist.service.GeneralizedService
import java.io.File
import java.lang.reflect.Type
import java.util.concurrent.CompletableFuture
import java.util.concurrent.ConcurrentHashMap

object UniqueServerService : GeneralizedService
{

    var handler = Alchemist.dataHandler.createStoreType<String, UniqueServer>(Alchemist.getDataStoreMethod())

    val servers = ConcurrentHashMap<String, UniqueServer>()

    // flatfile info
    lateinit var jsonFile: File
    private val type: Type = object : TypeToken<MutableList<UniqueServer>>()
    {}.type

    fun loadAll()
    {
        val list = handler.retrieveAll()

        if (Alchemist.usingMongo)
        {

            for (server in list)
            {
                servers[server.id] = server
            }
        } else
        {
            FlatfileUtilities.getAllEntries(jsonFile, type, mutableListOf<UniqueServer>())?.forEach {
                servers[it.id] = it
            }
        }
    }

    fun getValues(): Collection<UniqueServer>
    {
        return servers.values
    }

    fun save(server: UniqueServer)
    {
        servers[server.id] = server

        if (Alchemist.usingMongo)
        {
            CompletableFuture.runAsync {
                handler.store(server.id, server)
            }
        } else
        {
            FlatfileUtilities.writeToFile(jsonFile, servers.values, type)
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
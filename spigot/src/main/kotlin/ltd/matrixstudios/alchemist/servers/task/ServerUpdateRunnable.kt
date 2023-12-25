package ltd.matrixstudios.alchemist.servers.task

import ltd.matrixstudios.alchemist.Alchemist
import ltd.matrixstudios.alchemist.service.server.UniqueServerService
import org.bukkit.Bukkit
import org.bukkit.scheduler.BukkitRunnable
import java.util.concurrent.TimeUnit

object ServerUpdateRunnable : BukkitRunnable()
{

    override fun run()
    {
        val server = Alchemist.globalServer

        server.players = Bukkit.getOnlinePlayers().map { it.uniqueId }.toCollection(arrayListOf())

        server.lastHeartbeat = System.currentTimeMillis()
        server.online = true

        UniqueServerService.handler.store(server.id, server)
        UniqueServerService.servers[server.id] = server

        for (mongoserver in UniqueServerService.handler.retrieveAll())
        {
            UniqueServerService.servers[mongoserver.id] = mongoserver

            if (mongoserver.online && System.currentTimeMillis()
                    .minus(mongoserver.lastHeartbeat) >= TimeUnit.MINUTES.toMillis(3)
            )
            {
                println("[Alchemist] [Emergency] Server " + mongoserver.displayName + " was declared online but was not responding to heartbeats. Setting server to offline")
                mongoserver.online = false
                mongoserver.players.clear()

                UniqueServerService.save(mongoserver)
            }
        }

    }
}
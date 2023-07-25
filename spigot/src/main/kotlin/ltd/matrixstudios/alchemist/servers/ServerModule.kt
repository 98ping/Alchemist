package ltd.matrixstudios.alchemist.servers

import co.aikar.commands.BaseCommand
import ltd.matrixstudios.alchemist.Alchemist
import ltd.matrixstudios.alchemist.AlchemistSpigotPlugin
import ltd.matrixstudios.alchemist.models.server.UniqueServer
import ltd.matrixstudios.alchemist.module.PluginModule
import ltd.matrixstudios.alchemist.redis.AsynchronousRedisSender
import ltd.matrixstudios.alchemist.redis.cache.refresh.RefreshServersPacket
import ltd.matrixstudios.alchemist.servers.commands.ServerEnvironmentCommand
import ltd.matrixstudios.alchemist.servers.packets.ServerStatusChangePacket
import ltd.matrixstudios.alchemist.service.server.UniqueServerService
import ltd.matrixstudios.alchemist.util.Chat
import ltd.matrixstudios.alchemist.util.NetworkUtil

/**
 * Class created on 7/21/2023

 * @author 98ping
 * @project Alchemist
 * @website https://solo.to/redis
 */
object ServerModule : PluginModule {
    override fun onLoad() {
        val config = AlchemistSpigotPlugin.instance.config
        val serversStart = System.currentTimeMillis()

        if (UniqueServerService.byId(config.getString("server.id").toLowerCase()) == null) {
            val server = UniqueServer(
                config.getString("server.id").lowercase(),
                config.getString("server.id"),
                config.getString("server.id"),
                arrayListOf(),
                true,
                (Runtime.getRuntime().maxMemory() / (1024 * 1024)).toInt(),
                config.getString("server.id"),
                -1L,
                false,
                "",
                System.currentTimeMillis()
            )

            Chat.sendConsoleMessage(
                "&cServer was not originally found so it was created instead"
            )

            UniqueServerService.save(server)

            updateUniqueServer(server)
        } else {
            val server = UniqueServerService.byId(config.getString("server.id").toLowerCase())!!
            server.ramAllocated = (Runtime.getRuntime().maxMemory() / (1024 * 1024)).toInt()
            server.online = true

            updateUniqueServer(server)
        }

        AsynchronousRedisSender.send(ServerStatusChangePacket(Chat.format("&8[&eServer Monitor&8] &fAdding server " + Alchemist.globalServer.displayName + "..."), Alchemist.globalServer))

        NetworkUtil.load()

        Chat.sendConsoleMessage(
            "&6[Servers] &fServer instance loaded in &6" + System.currentTimeMillis().minus(serversStart) + "ms"
        )
    }

    override fun getCommands(): MutableList<BaseCommand> {
        val commands = mutableListOf<BaseCommand>()

        commands.add(ServerEnvironmentCommand())

        return commands
    }

    override fun getModularConfigOption(): Boolean {
        return true
    }

    fun updateUniqueServer(server: UniqueServer) {
        Alchemist.globalServer = server
        AsynchronousRedisSender.send(RefreshServersPacket())
    }
}
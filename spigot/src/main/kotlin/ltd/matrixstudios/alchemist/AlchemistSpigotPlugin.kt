package ltd.matrixstudios.alchemist

import co.aikar.commands.PaperCommandManager
import io.github.nosequel.data.connection.mongo.AuthenticatedMongoConnectionPool
import io.github.nosequel.data.connection.mongo.NoAuthMongoConnectionPool
import io.github.nosequel.data.connection.mongo.URIMongoConnectionPool
import ltd.matrixstudios.alchemist.aikar.ACFCommandController
import ltd.matrixstudios.alchemist.commands.grants.*
import ltd.matrixstudios.alchemist.commands.player.*
import ltd.matrixstudios.alchemist.commands.punishments.create.*
import ltd.matrixstudios.alchemist.listeners.filter.FilterListener
import ltd.matrixstudios.alchemist.listeners.profile.ProfileJoinListener
import ltd.matrixstudios.alchemist.models.ranks.Rank
import ltd.matrixstudios.alchemist.models.server.UniqueServer
import ltd.matrixstudios.alchemist.network.listener.NetworkJoinAndLeaveListener
import ltd.matrixstudios.alchemist.party.DecayingPartyTask
import ltd.matrixstudios.alchemist.permissions.AccessiblePermissionHandler
import ltd.matrixstudios.alchemist.placeholder.AlchemistExpansion
import ltd.matrixstudios.alchemist.punishments.PunishmentType
import ltd.matrixstudios.alchemist.redis.AsynchronousRedisSender
import ltd.matrixstudios.alchemist.redis.LocalPacketPubSub
import ltd.matrixstudios.alchemist.redis.RedisPacketManager
import ltd.matrixstudios.alchemist.servers.listener.ServerLockListener
import ltd.matrixstudios.alchemist.servers.task.ServerUpdateRunnable
import ltd.matrixstudios.alchemist.service.server.UniqueServerService
import ltd.matrixstudios.alchemist.staff.mode.listeners.FrozenPlayerListener
import ltd.matrixstudios.alchemist.staff.mode.listeners.GenericStaffmodePreventionListener
import ltd.matrixstudios.alchemist.staff.mode.listeners.StaffmodeFunctionalityListener
import ltd.matrixstudios.alchemist.statistic.StatisticManager
import ltd.matrixstudios.alchemist.sync.SyncTask
import ltd.matrixstudios.alchemist.tasks.ClearOutExpirablesTask
import ltd.matrixstudios.alchemist.themes.ThemeLoader
import ltd.matrixstudios.alchemist.themes.commands.ThemeSelectCommand
import ltd.matrixstudios.alchemist.util.menu.listener.MenuListener
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import java.util.logging.Level
import kotlin.concurrent.thread

class AlchemistSpigotPlugin : JavaPlugin() {

    companion object {
        lateinit var instance: AlchemistSpigotPlugin
    }

    lateinit var globalServer: UniqueServer
    lateinit var commandManager: PaperCommandManager

    override fun onEnable() {
        saveDefaultConfig()
        instance = this


        val startMongo = System.currentTimeMillis()
        val authEnabled = config.getBoolean("mongo.auth")
        val uri = config.getString("uri")

        if (uri != "") {
            val connectionPool = URIMongoConnectionPool().apply {
                this.databaseName = config.getString("mongo.database")
                this.uri = uri

            }

            Alchemist.start(connectionPool,
                config.getString("redis.host"),
                config.getInt("redis.port"),
                (if (config.getString("redis.username") == "") null else config.getString("redis.username")),
                (if (config.getString("redis.password") == "") null else config.getString("redis.password"))
            )
        } else if (authEnabled) {
            val connectionPool = AuthenticatedMongoConnectionPool().apply {
                hostname = config.getString("mongo.host")
                password = config.getString("mongo.password")
                username = config.getString("mongo.username")
                port = config.getInt("mongo.port")
                databaseName = config.getString("mongo.database")
                authDb = config.getString("mongo.authDB")
            }

            Alchemist.start(
                connectionPool,
                config.getString("redis.host"),
                config.getInt("redis.port"),
                (if (config.getString("redis.username") == "") null else config.getString("redis.username")),
                (if (config.getString("redis.password") == "") null else config.getString("redis.password"))
            )
        } else {
            val connectionPool = NoAuthMongoConnectionPool().apply {
                hostname = config.getString("mongo.host")
                port = config.getInt("mongo.port")
                databaseName = config.getString("mongo.database")
            }

            Alchemist.start(
                connectionPool,
                config.getString("redis.host"),
                config.getInt("redis.port"),
                (if (config.getString("redis.username") == "") null else config.getString("redis.username")),
                (if (config.getString("redis.password") == "") null else config.getString("redis.password"))
            )
        }

        logger.log(
            Level.INFO,
            "[Mongo] Detected mongo auth type and loaded in " + System.currentTimeMillis().minus(startMongo) + "ms"
        )

        val themeStart = System.currentTimeMillis()
        ThemeLoader.loadAllThemes()

        logger.log(Level.INFO, "[Themes] All themes loaded in " + System.currentTimeMillis().minus(themeStart) + "ms")

        val pubsubStart = System.currentTimeMillis()
        thread {
            RedisPacketManager.pool.resource.use {
                it.subscribe(LocalPacketPubSub(), "Alchemist||Packets||")
            }
        }

        logger.log(
            Level.INFO,
            "[Jedis] Jedis publisher started in " + System.currentTimeMillis().minus(pubsubStart) + "ms"
        )

        val listenerStart = System.currentTimeMillis()
        server.pluginManager.registerEvents(ProfileJoinListener(), this)
        server.pluginManager.registerEvents(MenuListener(), this)

        if (config.getBoolean("modules.filters")) {
            server.pluginManager.registerEvents(FilterListener, this)
        }

        server.pluginManager.registerEvents(NetworkJoinAndLeaveListener(), this)
        server.pluginManager.registerEvents(ServerLockListener(), this)

        server.pluginManager.registerEvents(FrozenPlayerListener(), this)
        server.pluginManager.registerEvents(GenericStaffmodePreventionListener(), this)
        server.pluginManager.registerEvents(StaffmodeFunctionalityListener(), this)

        logger.log(
            Level.INFO,
            "[Listeners] Listeners loaded in " + System.currentTimeMillis().minus(listenerStart) + "ms"
        )

        val permissionStart = System.currentTimeMillis()
        AccessiblePermissionHandler.load()

        logger.log(
            Level.INFO,
            "[Permissions] All permissions loaded in " + System.currentTimeMillis().minus(permissionStart) + "ms"
        )

        ClearOutExpirablesTask.runTaskTimerAsynchronously(this, 0L, 20L)
        ServerUpdateRunnable.runTaskTimerAsynchronously(this, 0L, 80L)
        (SyncTask()).runTaskTimer(this, 0L, 60 * 20L)

        if (config.getBoolean("modules.parties")) {
            (DecayingPartyTask()).runTaskTimer(this, 0L, 40L)
        }

        val serversStart = System.currentTimeMillis()

        if (UniqueServerService.byId(config.getString("server.id")) == null) {
            val server = UniqueServer(
                config.getString("server.id").lowercase(),
                config.getString("server.id"),
                config.getString("server.id"),
                arrayListOf(),
                true,
                1024,
                config.getString("server.id"),
                false,
                "",
                System.currentTimeMillis()
            )

            println("[Alchemist] [Debug] Created a new server instance because none was found")
            UniqueServerService.save(server)

            globalServer = server
        } else {
            globalServer = UniqueServerService.byId(config.getString("server.id"))!!

            globalServer.online = true
        }

        logger.log(
            Level.INFO,
            "[Servers] Server instance loaded in " + System.currentTimeMillis().minus(serversStart) + "ms"
        )

        StatisticManager.loadStats()

        val papiStart = System.currentTimeMillis()
        registerExpansion()

        logger.log(
            Level.INFO,
            "[Placeholders] All placeholders loaded in " + System.currentTimeMillis().minus(papiStart) + "ms"
        )

        val commandsStart = System.currentTimeMillis()

        ACFCommandController.registerAll()

        logger.log(
            Level.INFO,
            "[Commands] All commands registered in " + System.currentTimeMillis().minus(commandsStart) + "ms"
        )

    }

    fun registerExpansion()
    {
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null)
        {
            AlchemistExpansion().register()
        }

    }
}
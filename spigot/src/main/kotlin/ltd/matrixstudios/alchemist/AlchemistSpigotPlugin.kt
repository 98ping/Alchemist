package ltd.matrixstudios.alchemist

import co.aikar.commands.PaperCommandManager
import io.github.nosequel.data.connection.mongo.AuthenticatedMongoConnectionPool
import io.github.nosequel.data.connection.mongo.NoAuthMongoConnectionPool
import ltd.matrixstudios.alchemist.chatcolors.ChatColorLoader
import ltd.matrixstudios.alchemist.chatcolors.commands.ChatColorCommands
import ltd.matrixstudios.alchemist.commands.admin.AdminChatCommand
import ltd.matrixstudios.alchemist.commands.alts.AltsCommand
import ltd.matrixstudios.alchemist.commands.context.GameProfileContextResolver
import ltd.matrixstudios.alchemist.commands.context.PunishmentTypeResolver
import ltd.matrixstudios.alchemist.commands.context.RankContextResolver
import ltd.matrixstudios.alchemist.commands.filter.FilterCommands
import ltd.matrixstudios.alchemist.commands.friends.FriendCommands
import ltd.matrixstudios.alchemist.commands.grants.*
import ltd.matrixstudios.alchemist.commands.party.PartyCommands
import ltd.matrixstudios.alchemist.commands.player.*
import ltd.matrixstudios.alchemist.commands.punishments.create.*
import ltd.matrixstudios.alchemist.commands.punishments.menu.HistoryCommand
import ltd.matrixstudios.alchemist.commands.punishments.menu.PunishmentLookupCommands
import ltd.matrixstudios.alchemist.commands.punishments.remove.UnbanCommand
import ltd.matrixstudios.alchemist.commands.punishments.remove.UnblacklistCommand
import ltd.matrixstudios.alchemist.commands.punishments.remove.UnmuteCommand
import ltd.matrixstudios.alchemist.commands.punishments.remove.WipePunishmentsCommand
import ltd.matrixstudios.alchemist.commands.rank.GenericRankCommands
import ltd.matrixstudios.alchemist.commands.server.ServerEnvironmentCommand
import ltd.matrixstudios.alchemist.commands.sessions.SessionCommands
import ltd.matrixstudios.alchemist.commands.staff.StaffchatCommand
import ltd.matrixstudios.alchemist.commands.tags.TagAdminCommand
import ltd.matrixstudios.alchemist.commands.tags.TagCommand
import ltd.matrixstudios.alchemist.commands.tags.grants.TagGrantCommand
import ltd.matrixstudios.alchemist.commands.tags.grants.TagGrantsCommand
import ltd.matrixstudios.alchemist.listeners.filter.FilterListener
import ltd.matrixstudios.alchemist.listeners.profile.ProfileJoinListener
import ltd.matrixstudios.alchemist.models.profile.GameProfile
import ltd.matrixstudios.alchemist.models.ranks.Rank
import ltd.matrixstudios.alchemist.models.server.UniqueServer
import ltd.matrixstudios.alchemist.network.listener.NetworkJoinAndLeaveListener
import ltd.matrixstudios.alchemist.party.DecayingPartyTask
import ltd.matrixstudios.alchemist.permissions.AccessiblePermissionHandler
import ltd.matrixstudios.alchemist.punishments.PunishmentType
import ltd.matrixstudios.alchemist.redis.LocalPacketPubSub
import ltd.matrixstudios.alchemist.redis.RedisPacketManager
import ltd.matrixstudios.alchemist.servers.listener.ServerLockListener
import ltd.matrixstudios.alchemist.servers.task.ServerUpdateRunnable
import ltd.matrixstudios.alchemist.service.server.UniqueServerService
import ltd.matrixstudios.alchemist.statistic.StatisticManager
import ltd.matrixstudios.alchemist.tasks.ClearOutExpirablesTask
import ltd.matrixstudios.alchemist.util.menu.listener.MenuListener
import org.bukkit.plugin.java.JavaPlugin
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


        val authEnabled = config.getBoolean("mongo.auth")

        if (authEnabled) {
            val connectionPool = AuthenticatedMongoConnectionPool().apply {
                hostname = config.getString("mongo.host")
                password = config.getString("mongo.password")
                username = config.getString("mongo.username")
                port = config.getInt("mongo.port")
                databaseName = config.getString("mongo.database")
                authDb = config.getString("mongo.authDB")
            }

            Alchemist.start(connectionPool,
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

            Alchemist.start(connectionPool,
                config.getString("redis.host"),
                config.getInt("redis.port"),
                (if (config.getString("redis.username") == "") null else config.getString("redis.username")),
                (if (config.getString("redis.password") == "") null else config.getString("redis.password"))
            )
        }

        thread {
            RedisPacketManager.pool.resource.use {
                it.subscribe(LocalPacketPubSub(), "Alchemist||Packets||")
            }
        }

        server.pluginManager.registerEvents(ProfileJoinListener(), this)
        server.pluginManager.registerEvents(MenuListener(), this)

        if (config.getBoolean("modules.filters"))
        {
            server.pluginManager.registerEvents(FilterListener, this)
        }

        server.pluginManager.registerEvents(NetworkJoinAndLeaveListener(), this)
        server.pluginManager.registerEvents(ServerLockListener(), this)

        AccessiblePermissionHandler.load()

        ClearOutExpirablesTask.runTaskTimerAsynchronously(this, 0L, 20L)
        ServerUpdateRunnable.runTaskTimerAsynchronously(this, 0L, 80L)

        if (config.getBoolean("modules.parties"))
        {
            (DecayingPartyTask()).runTaskTimer(this, 0L, 40L)
        }


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

        StatisticManager.loadStats()

        val config = this.config

        this.commandManager = PaperCommandManager(this).apply {
            this.commandContexts.registerContext(GameProfile::class.java, GameProfileContextResolver())
            this.commandContexts.registerContext(Rank::class.java, RankContextResolver())
            this.commandContexts.registerContext(PunishmentType::class.java, PunishmentTypeResolver())


            this.commandCompletions.registerCompletion("gameprofile") {
                return@registerCompletion server.onlinePlayers.map { it.name }.toCollection(arrayListOf())
            }
            if (config.getBoolean("modules.ranks"))
            {
                registerCommand(GenericRankCommands())
            }

            registerCommand(GrantCommand())
            registerCommand(GrantsCommand())
            registerCommand(CGrantCommand())

            if (config.getBoolean("modules.punishments"))
            {
                registerCommand(MuteCommand())
                registerCommand(BanCommand())
                registerCommand(BlacklistCommand())
                registerCommand(TempBanCommand())
                registerCommand(TempMuteCommand())
                registerCommand(WarnCommand())

                registerCommand(UnbanCommand())
                registerCommand(UnmuteCommand())
                registerCommand(UnblacklistCommand())
                registerCommand(PunishmentLookupCommands())

                registerCommand(WipePunishmentsCommand())
            }

            if (config.getBoolean("modules.chatcolors"))
            {
                ChatColorLoader.loadAllChatColors()
                registerCommand(ChatColorCommands())
            }

            registerCommand(AuditCommand)

            registerCommand(AltsCommand())
            registerCommand(HistoryCommand())
            registerCommand(GrantHistoryCommand())

            if (config.getBoolean("modules.prefixes"))
            {
                registerCommand(TagAdminCommand())
                registerCommand(TagCommand())
                registerCommand(TagGrantCommand())
                registerCommand(TagGrantsCommand())
            }

            if (config.getBoolean("freeRank.enabled"))
            {
                registerCommand(FreerankCommand())
            }


            if (config.getBoolean("modules.filters"))
            {
                registerCommand(FilterCommands())
            }

            if (config.getBoolean("modules.friends"))
            {
                registerCommand(FriendCommands())
            }

            registerCommand(ServerEnvironmentCommand())
            registerCommand(ListCommand())
            registerCommand(SudoCommand())
            registerCommand(StaffchatCommand())
            registerCommand(AdminChatCommand())
            registerCommand(PlayerAdminCommand())

            registerCommand(WipeProfileCommand())
            registerCommand(SessionCommands())

            if (config.getBoolean("modules.parties"))
            {
                registerCommand(PartyCommands())
            }


            registerCommand(LookupCommand())
        }
        



    }
}
package ltd.matrixstudios.alchemist

import co.aikar.commands.PaperCommandManager
import io.github.nosequel.data.connection.mongo.AuthenticatedMongoConnectionPool
import io.github.nosequel.data.connection.mongo.NoAuthMongoConnectionPool
import javafx.collections.transformation.FilteredList
import ltd.matrixstudios.alchemist.commands.context.GameProfileContextResolver
import ltd.matrixstudios.alchemist.commands.context.RankContextResolver
import ltd.matrixstudios.alchemist.commands.filter.FilterCommands
import ltd.matrixstudios.alchemist.commands.friends.FriendCommands
import ltd.matrixstudios.alchemist.commands.grants.CGrantCommand
import ltd.matrixstudios.alchemist.commands.grants.GrantCommand
import ltd.matrixstudios.alchemist.commands.grants.GrantsCommand
import ltd.matrixstudios.alchemist.commands.player.ListCommand
import ltd.matrixstudios.alchemist.commands.player.SudoCommand
import ltd.matrixstudios.alchemist.commands.punishments.create.*
import ltd.matrixstudios.alchemist.commands.punishments.menu.HistoryCommand
import ltd.matrixstudios.alchemist.commands.punishments.remove.UnbanCommand
import ltd.matrixstudios.alchemist.commands.punishments.remove.UnblacklistCommand
import ltd.matrixstudios.alchemist.commands.punishments.remove.UnmuteCommand
import ltd.matrixstudios.alchemist.commands.rank.GenericRankCommands
import ltd.matrixstudios.alchemist.commands.staff.StaffchatCommand
import ltd.matrixstudios.alchemist.commands.tags.TagAdminCommand
import ltd.matrixstudios.alchemist.commands.tags.TagCommand
import ltd.matrixstudios.alchemist.commands.tags.grants.TagGrantCommand
import ltd.matrixstudios.alchemist.commands.tags.grants.TagGrantsCommand
import ltd.matrixstudios.alchemist.listeners.filter.FilterListener
import ltd.matrixstudios.alchemist.listeners.profile.ProfileJoinListener
import ltd.matrixstudios.alchemist.models.profile.GameProfile
import ltd.matrixstudios.alchemist.models.ranks.Rank
import ltd.matrixstudios.alchemist.permissions.AccessiblePermissionHandler
import ltd.matrixstudios.alchemist.redis.LocalPacketPubSub
import ltd.matrixstudios.alchemist.redis.RedisPacketManager
import ltd.matrixstudios.alchemist.service.profiles.ProfileGameService
import ltd.matrixstudios.alchemist.tasks.ClearOutExpirablesTask
import ltd.matrixstudios.alchemist.util.menu.listener.MenuListener
import org.bukkit.plugin.java.JavaPlugin
import kotlin.concurrent.thread

class AlchemistSpigotPlugin : JavaPlugin() {

    companion object {
        lateinit var instance: AlchemistSpigotPlugin
    }

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
        server.pluginManager.registerEvents(FilterListener, this)

        AccessiblePermissionHandler.load()

        ClearOutExpirablesTask.runTaskTimerAsynchronously(this, 0L, 20L)

        val commandHandler = PaperCommandManager(this).apply {
            this.commandContexts.registerContext(GameProfile::class.java, GameProfileContextResolver())
            this.commandContexts.registerContext(Rank::class.java, RankContextResolver())
            registerCommand(GenericRankCommands())
            registerCommand(GrantCommand())
            registerCommand(GrantsCommand())
            registerCommand(CGrantCommand())

            registerCommand(MuteCommand())
            registerCommand(BanCommand())
            registerCommand(BlacklistCommand())
            registerCommand(TempBanCommand())
            registerCommand(TempMuteCommand())
            registerCommand(WarnCommand())

            registerCommand(UnbanCommand())
            registerCommand(UnmuteCommand())
            registerCommand(UnblacklistCommand())

            registerCommand(HistoryCommand())

            registerCommand(TagAdminCommand())
            registerCommand(TagCommand())
            registerCommand(TagGrantCommand())
            registerCommand(TagGrantsCommand())

            registerCommand(FilterCommands())

            registerCommand(ListCommand())
            registerCommand(FriendCommands())
            registerCommand(SudoCommand())
            registerCommand(StaffchatCommand())
        }
        



    }
}
package ltd.matrixstudios.alchemist

import co.aikar.commands.PaperCommandManager
import com.sun.javafx.geom.transform.BaseTransform
import io.github.nosequel.data.connection.mongo.AuthenticatedMongoConnectionPool
import io.github.nosequel.data.connection.mongo.NoAuthMongoConnectionPool
import javafx.scene.shape.Shape3D
import ltd.matrixstudios.alchemist.commands.context.GameProfileContextResolver
import ltd.matrixstudios.alchemist.commands.grants.GrantCommand
import ltd.matrixstudios.alchemist.commands.grants.GrantsCommand
import ltd.matrixstudios.alchemist.commands.punishments.create.*
import ltd.matrixstudios.alchemist.commands.punishments.menu.HistoryCommand
import ltd.matrixstudios.alchemist.commands.punishments.remove.UnbanCommand
import ltd.matrixstudios.alchemist.commands.punishments.remove.UnblacklistCommand
import ltd.matrixstudios.alchemist.commands.punishments.remove.UnmuteCommand
import ltd.matrixstudios.alchemist.commands.rank.GenericRankCommands
import ltd.matrixstudios.alchemist.listeners.profile.ProfileJoinListener
import ltd.matrixstudios.alchemist.models.profile.GameProfile
import ltd.matrixstudios.alchemist.permissions.AccessiblePermissionHandler
import ltd.matrixstudios.alchemist.util.menu.listener.MenuListener
import org.bukkit.plugin.java.JavaPlugin
import java.awt.Shape
import java.util.*

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
                databaseName = "Alchemist"
                authDb = "admin"
            }

            Alchemist.start(connectionPool, "172.18.0.1")
        } else {
            val connectionPool = NoAuthMongoConnectionPool().apply {
                hostname = config.getString("mongo.host")
                port = config.getInt("mongo.port")
                databaseName = "Alchemist"
            }

            Alchemist.start(connectionPool, "172.18.0.1")
        }

        server.pluginManager.registerEvents(ProfileJoinListener(), this)
        server.pluginManager.registerEvents(MenuListener(), this)

        AccessiblePermissionHandler.load()

        val commandHandler = PaperCommandManager(this).apply {
            this.commandContexts.registerContext(GameProfile::class.java, GameProfileContextResolver())
            registerCommand(GenericRankCommands())
            registerCommand(GrantCommand())
            registerCommand(GrantsCommand())

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
        }
        



    }
}
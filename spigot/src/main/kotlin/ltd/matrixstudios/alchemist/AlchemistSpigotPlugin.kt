package ltd.matrixstudios.alchemist

import co.aikar.commands.PaperCommandManager
import com.sun.javafx.geom.transform.BaseTransform
import io.github.nosequel.data.connection.mongo.AuthenticatedMongoConnectionPool
import io.github.nosequel.data.connection.mongo.NoAuthMongoConnectionPool
import javafx.scene.shape.Shape3D
import ltd.matrixstudios.alchemist.commands.context.GameProfileContextResolver
import ltd.matrixstudios.alchemist.commands.grants.GrantCommand
import ltd.matrixstudios.alchemist.commands.grants.GrantsCommand
import ltd.matrixstudios.alchemist.commands.punishments.create.BanCommand
import ltd.matrixstudios.alchemist.commands.rank.GenericRankCommands
import ltd.matrixstudios.alchemist.listeners.profile.ProfileJoinListener
import ltd.matrixstudios.alchemist.models.profile.GameProfile
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
                password = config.getString("mongo.pass")
                username = config.getString("mongo.username")
                port = config.getInt("mongo.port")
                databaseName = "Alchemist"
            }

            Alchemist.start(connectionPool, "127.0.0.1")
        } else {
            val connectionPool = NoAuthMongoConnectionPool().apply {
                hostname = config.getString("mongo.host")
                port = config.getInt("mongo.port")
                databaseName = "Alchemist"
            }

            Alchemist.start(connectionPool, "127.0.0.1")
        }

        server.pluginManager.registerEvents(ProfileJoinListener(), this)
        server.pluginManager.registerEvents(MenuListener(), this)

        val commandHandler = PaperCommandManager(this).apply {
            this.commandContexts.registerContext(GameProfile::class.java, GameProfileContextResolver())
            registerCommand(GenericRankCommands())
            registerCommand(BanCommand())
            registerCommand(GrantCommand())
            registerCommand(GrantsCommand())
        }
        



    }
}
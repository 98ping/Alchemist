package ltd.matrixstudios.alchemist

import com.google.inject.Inject
import com.velocitypowered.api.event.PostOrder
import com.velocitypowered.api.event.Subscribe
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent
import com.velocitypowered.api.plugin.Plugin
import com.velocitypowered.api.plugin.annotation.DataDirectory
import com.velocitypowered.api.proxy.ProxyServer
import io.github.nosequel.data.connection.mongo.AuthenticatedMongoConnectionPool
import io.github.nosequel.data.connection.mongo.NoAuthMongoConnectionPool
import io.github.nosequel.data.connection.mongo.URIMongoConnectionPool
import ltd.matrixstudios.alchemist.listener.VelocityListener
import net.kyori.adventure.text.TextComponent
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer
import org.simpleyaml.configuration.file.YamlFile
import java.nio.file.Path
import java.util.logging.Logger


@Plugin(
    id = "alchemist",
    name = "AlchemisVelocity",
    version = "1.0",
    authors = ["98ping", "AndyReckt"],
)
class AlchemistVelocity @Inject constructor(val server: ProxyServer, val logger: Logger, @DataDirectory val dataDirectory: Path) {

    val config = YamlFile(dataDirectory.resolve("config.yml").toFile())

    init {
        instance = this

        config.createOrLoad()
        config.addDefaults(
            mapOf(
                "lockdown" to true,

                "mongo.host" to "127.0.0.1",
                "mongo.port" to 27017,
                "mongo.authDB" to "admin",
                "mongo.password" to "",
                "mongo.username" to "",
                "mongo.database" to "Alchemist",
                "mongo.auth" to false,

                "uri" to "",

                "redis.host" to "127.0.0.1",
                "redis.port" to 6379,
                "redis.username" to "",
                "redis.password" to ""
            )
        )
        config.save()

        this.setupDatabases()
    }

    @Subscribe(order = PostOrder.FIRST)
    fun initPlugin(event: ProxyInitializeEvent) {
        server.eventManager.register(this, VelocityListener(this))
    }

    @Subscribe
    fun onProxyShutdown(event: ProxyShutdownEvent) {

    }

    companion object {
        @JvmStatic
        lateinit var instance: AlchemistVelocity

        private var serializer: LegacyComponentSerializer =
            LegacyComponentSerializer.builder().character('&').hexColors().build()

        @JvmStatic
        fun color(message: String): TextComponent {
            return serializer.deserialize(message)
        }
    }

    private fun setupDatabases() {
        val authEnabled = config.getBoolean("mongo.auth")
        if (config.getString("uri") != "") {
            val connectionPool = URIMongoConnectionPool().apply {
                this.databaseName = config.getString("mongo.database")
                this.uri = config.getString("uri")
            }

            Alchemist.start(true, connectionPool,
                true,
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

            Alchemist.start(true, connectionPool,
                true,
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

            Alchemist.start(true, connectionPool,
                true,
                config.getString("redis.host"),
                config.getInt("redis.port"),
                (if (config.getString("redis.username") == "") null else config.getString("redis.username")),
                (if (config.getString("redis.password") == "") null else config.getString("redis.password"))
            )
        }
    }
}
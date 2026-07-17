package ltd.matrixstudios.alchemist

import com.google.inject.Inject
import com.velocitypowered.api.event.PostOrder
import com.velocitypowered.api.event.Subscribe
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent
import com.velocitypowered.api.plugin.Plugin
import com.velocitypowered.api.plugin.annotation.DataDirectory
import com.velocitypowered.api.proxy.ProxyServer
import ltd.matrixstudios.alchemist.listener.VelocityListener
import ltd.matrixstudios.alchemist.service.fakeplayers.FakePlayerCountService
import ltd.matrixstudios.alchemist.service.server.UniqueServerService
import net.kyori.adventure.text.TextComponent
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer
import org.apache.logging.log4j.Level
import org.apache.logging.log4j.core.config.Configurator
import org.simpleyaml.configuration.file.YamlFile
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import java.nio.file.Path
import java.util.concurrent.TimeUnit
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

        Configurator.setLevel("org.mongodb.driver", Level.WARN)

        config.createOrLoad()
        config.addDefaults(
            mapOf(
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

        server.scheduler.buildTask(this) {
            UniqueServerService.loadAll()
        }.repeat(5, TimeUnit.SECONDS).schedule()

        server.scheduler.buildTask(this) {
            try
            {
                FakePlayerCountService.refreshTotal()
            } catch (ignored: Throwable)
            {
            }
        }.repeat(3, TimeUnit.SECONDS).schedule()
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
        val database = config.getString("mongo.database")

        val mongoUri = if (config.getString("uri") != "") {
            config.getString("uri")
        } else if (authEnabled) {
            val user = URLEncoder.encode(config.getString("mongo.username") ?: "", StandardCharsets.UTF_8.name())
            val pass = URLEncoder.encode(config.getString("mongo.password") ?: "", StandardCharsets.UTF_8.name())
            val host = config.getString("mongo.host")
            val port = config.getInt("mongo.port")
            val authDb = config.getString("mongo.authDB")
            val authSource = if (authDb.isNullOrEmpty()) "admin" else authDb

            "mongodb://$user:$pass@$host:$port/?authSource=$authSource"
        } else {
            "mongodb://${config.getString("mongo.host")}:${config.getInt("mongo.port")}"
        }

        Alchemist.start(
            mongoUri,
            database,
            true,
            config.getString("redis.host"),
            config.getInt("redis.port"),
            (if (config.getString("redis.username") == "") null else config.getString("redis.username")),
            (if (config.getString("redis.password") == "") null else config.getString("redis.password"))
        )
    }
}
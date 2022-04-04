package ltd.matrixstudios.alchemist

import co.aikar.commands.PaperCommandManager
import ltd.matrixstudios.alchemist.commands.context.GameProfileContextResolver
import ltd.matrixstudios.alchemist.commands.punishments.create.BanCommand
import ltd.matrixstudios.alchemist.commands.rank.GenericRankCommands
import ltd.matrixstudios.alchemist.listeners.profile.ProfileJoinListener
import ltd.matrixstudios.alchemist.models.profile.GameProfile
import ltd.matrixstudios.mongo.credientials.MongoPoolConnection
import org.bukkit.plugin.java.JavaPlugin

class AlchemistSpigotPlugin : JavaPlugin() {

    companion object {
        lateinit var instance: AlchemistSpigotPlugin
    }

    override fun onEnable() {
        saveDefaultConfig()
        instance = this


        val mongoPoolConnection = MongoPoolConnection(
            config.getString("mongo.host"),
            config.getInt("mongo.port"),
            config.getBoolean("mongo.authRequired"))

        if (mongoPoolConnection.requireAuth) {
            mongoPoolConnection.authenticate(
                config.getString("mongo.username"),
                config.getString("mongo.password"),
                config.getString("mongo.authDB"))
        }

        Alchemist.start(mongoPoolConnection, config.getString("redis.host"))

        server.pluginManager.registerEvents(ProfileJoinListener(), this)

        val commandHandler = PaperCommandManager(this).apply {
            this.commandContexts.registerContext(GameProfile::class.java, GameProfileContextResolver())
            registerCommand(GenericRankCommands())
            registerCommand(BanCommand())
        }



    }
}
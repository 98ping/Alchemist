package ltd.matrixstudios.alchemist

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

        Alchemist.start(mongoPoolConnection)



    }
}
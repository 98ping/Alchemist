package ltd.matrixstudios.alchemist

import co.aikar.commands.PaperCommandManager
import io.github.nosequel.data.connection.mongo.AuthenticatedMongoConnectionPool
import io.github.nosequel.data.connection.mongo.MongoConnectionPool
import io.github.nosequel.data.connection.mongo.NoAuthMongoConnectionPool
import io.github.nosequel.data.connection.mongo.URIMongoConnectionPool
import ltd.matrixstudios.alchemist.aikar.ACFCommandController
import ltd.matrixstudios.alchemist.broadcasts.BroadcastService
import ltd.matrixstudios.alchemist.commands.server.task.ServerReleaseTask
import ltd.matrixstudios.alchemist.filter.listener.FilterListener
import ltd.matrixstudios.alchemist.models.server.UniqueServer
import ltd.matrixstudios.alchemist.module.PluginModuleHandler
import ltd.matrixstudios.alchemist.network.listener.NetworkJoinAndLeaveListener
import ltd.matrixstudios.alchemist.party.DecayingPartyTask
import ltd.matrixstudios.alchemist.profiles.permissions.AccessiblePermissionHandler
import ltd.matrixstudios.alchemist.placeholder.AlchemistExpansion
import ltd.matrixstudios.alchemist.profiles.BukkitProfileAdaptation
import ltd.matrixstudios.alchemist.profiles.ProfileJoinListener
import ltd.matrixstudios.alchemist.punishment.limitation.PunishmentLimitationUnderstander
import ltd.matrixstudios.alchemist.queue.BukkitQueueHandler
import ltd.matrixstudios.alchemist.redis.AsynchronousRedisSender
import ltd.matrixstudios.alchemist.redis.LocalPacketPubSub
import ltd.matrixstudios.alchemist.redis.RedisPacketManager
import ltd.matrixstudios.alchemist.servers.listener.ServerLockListener
import ltd.matrixstudios.alchemist.servers.packets.ServerStatusChangePacket
import ltd.matrixstudios.alchemist.servers.task.ServerUpdateRunnable
import ltd.matrixstudios.alchemist.service.server.UniqueServerService
import ltd.matrixstudios.alchemist.service.vouchers.VoucherService
import ltd.matrixstudios.alchemist.servers.statistic.StatisticManager
import ltd.matrixstudios.alchemist.staff.mode.listeners.FrozenPlayerListener
import ltd.matrixstudios.alchemist.staff.mode.listeners.GenericStaffmodePreventionListener
import ltd.matrixstudios.alchemist.staff.mode.listeners.StaffmodeFunctionalityListener
import ltd.matrixstudios.alchemist.sync.SyncTask
import ltd.matrixstudios.alchemist.tasks.ClearOutExpirablesTask
import ltd.matrixstudios.alchemist.themes.ThemeLoader
import ltd.matrixstudios.alchemist.util.Chat
import ltd.matrixstudios.alchemist.util.NetworkUtil
import ltd.matrixstudios.alchemist.util.menu.listener.MenuListener
import ltd.matrixstudios.alchemist.vault.VaultHookManager
import ltd.matrixstudios.alchemist.webhook.WebhookService
import net.kyori.adventure.platform.bukkit.BukkitAudiences
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import kotlin.concurrent.thread


class AlchemistSpigotPlugin : JavaPlugin() {

    companion object {
        lateinit var instance: AlchemistSpigotPlugin
    }

    lateinit var commandManager: PaperCommandManager
    lateinit var audience: BukkitAudiences

    override fun onEnable() {
        saveDefaultConfig()
        instance = this

        sendStartupMSG()

        val startMongo = System.currentTimeMillis()
        val authEnabled = config.getBoolean("mongo.auth")
        val uri = config.getString("uri")
        var connectionPool: MongoConnectionPool

        if (uri != "") {
            connectionPool = URIMongoConnectionPool().apply {
                this.databaseName = config.getString("mongo.database")
                this.uri = uri
            }
        } else if (authEnabled) {
            connectionPool = AuthenticatedMongoConnectionPool().apply {
                hostname = config.getString("mongo.host")
                password = config.getString("mongo.password")
                username = config.getString("mongo.username")
                port = config.getInt("mongo.port")
                databaseName = config.getString("mongo.database")
                authDb = config.getString("mongo.authDB")
            }
        } else {
            connectionPool = NoAuthMongoConnectionPool().apply {
                hostname = config.getString("mongo.host")
                port = config.getInt("mongo.port")
                databaseName = config.getString("mongo.database")
            }
        }

        Alchemist.start(connectionPool,
            config.getString("redis.host"),
            config.getInt("redis.port"),
            (if (config.getString("redis.username") == "") null else config.getString("redis.username")),
            (if (config.getString("redis.password") == "") null else config.getString("redis.password"))
        )

        Chat.sendConsoleMessage("&a[Mongo] &fDetected mongo auth type and loaded in &a" + System.currentTimeMillis().minus(startMongo) + "ms")

        audience = BukkitAudiences.create(this)

        val commandsStart = System.currentTimeMillis()

        ACFCommandController.registerAll()

        Chat.sendConsoleMessage(
            "&3[Commands] &fAll commands registered in &3" + System.currentTimeMillis().minus(commandsStart) + "ms"
        )

        PluginModuleHandler.loadModules()

        val themeStart = System.currentTimeMillis()
        ThemeLoader.loadAllThemes()

        Chat.sendConsoleMessage("&d[Themes] &fAll themes loaded in &d" + System.currentTimeMillis().minus(themeStart) + "ms")

        val pubsubStart = System.currentTimeMillis()
        thread {
            RedisPacketManager.pool.resource.use {
                it.subscribe(LocalPacketPubSub(), "Alchemist||Packets||")
            }
        }

        Chat.sendConsoleMessage(
            "&4[Jedis] &fJedis publisher started in &4" + System.currentTimeMillis().minus(pubsubStart) + "ms"
        )

        val listenerStart = System.currentTimeMillis()
        server.pluginManager.registerEvents(ProfileJoinListener(), this)
        server.pluginManager.registerEvents(MenuListener(), this)

        if (config.getBoolean("modules.filters")) {
            server.pluginManager.registerEvents(FilterListener, this)
        }

        server.pluginManager.registerEvents(NetworkJoinAndLeaveListener(), this)
        server.pluginManager.registerEvents(ServerLockListener(), this)

        if (config.getBoolean("modules.staffmode")) {
            server.pluginManager.registerEvents(FrozenPlayerListener(), this)
            server.pluginManager.registerEvents(GenericStaffmodePreventionListener(), this)
            server.pluginManager.registerEvents(StaffmodeFunctionalityListener(), this)
        }

        Chat.sendConsoleMessage(
            "&e[Listeners] &fListeners loaded in &e" + System.currentTimeMillis().minus(listenerStart) + "ms"
        )

        val broadcastStart = System.currentTimeMillis()

        if (config.getBoolean("autobroadcast.enabled")) {
            BroadcastService.loadMessages()
        }

        Chat.sendConsoleMessage(
            "&2[Broadcasts] &fAll permissions loaded in &9" + System.currentTimeMillis().minus(broadcastStart) + "ms"
        )


        ClearOutExpirablesTask.runTaskTimerAsynchronously(this, 0L, 20L)
        ServerUpdateRunnable.runTaskTimerAsynchronously(this, 0L, 80L)
        (ServerReleaseTask()).runTaskTimer(this, 0L, 20L)
        (SyncTask()).runTaskTimer(this, 0L, 60L * 20L)

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
                (Runtime.getRuntime().maxMemory() / (1024 * 1024)).toInt(),
                config.getString("server.id"),
                -1L,
                false,
                "",
                System.currentTimeMillis()
            )

            println("[Alchemist] [Debug] Created a new server instance because none was found")
            UniqueServerService.save(server)

            updateUniqueServer(server)
        } else {
            val server = UniqueServerService.byId(config.getString("server.id"))!!
            server.ramAllocated = (Runtime.getRuntime().maxMemory() / (1024 * 1024)).toInt()
            server.online = true

            updateUniqueServer(server)
        }

        AsynchronousRedisSender.send(ServerStatusChangePacket(Chat.format("&8[&eServer Monitor&8] &fAdding server " + Alchemist.globalServer.displayName + "..."), Alchemist.globalServer))

        NetworkUtil.load()

        Chat.sendConsoleMessage(
            "&6[Servers] &fServer instance loaded in &6" + System.currentTimeMillis().minus(serversStart) + "ms"
        )

        StatisticManager.loadStats()

        val vaultStart = System.currentTimeMillis()
        VaultHookManager.loadVault()

        Chat.sendConsoleMessage(
            "&6[Vault] &fHooked in &6" + System.currentTimeMillis().minus(vaultStart) + "ms"
        )

        val voucherStart = System.currentTimeMillis()
        VoucherService.loadVoucherGrants()
        VoucherService.loadVoucherTemplates()

        Chat.sendConsoleMessage(
            "&5[Vouchers] &fAll vouchers loaded in &5" + System.currentTimeMillis().minus(voucherStart) + "ms"
        )


        val papiStart = System.currentTimeMillis()
        registerExpansion()

        Chat.sendConsoleMessage(
            "&b[Placeholders] &fAll placeholders loaded in &b" + System.currentTimeMillis().minus(papiStart) + "ms"
        )

        val discordStart = System.currentTimeMillis()

        if (config.getBoolean("discord.punishments.enabled")) {

            WebhookService.createPunishmentClient(config.getString("discord.punishments.webhookLink"))
        }

        if (config.getBoolean("discord.grants.enabled")) {

            WebhookService.createPunishmentClient(config.getString("discord.grants.webhookLink"))
        }

        Chat.sendConsoleMessage(
            "&5[Discord] &fAll modules registered in &5" + System.currentTimeMillis().minus(discordStart) + "ms"
        )


        val queueStart = System.currentTimeMillis()

        BukkitQueueHandler.load()

        Chat.sendConsoleMessage(
            "&e[Queue] &fAll queues registered in &e" + System.currentTimeMillis().minus(queueStart) + "ms"
        )
    }

    fun sendStartupMSG()
    {
        Chat.sendMultiConsoleMessage(
            arrayOf(
                "&7&m--------------------------------",
                "&a&lMatrix Studios Software &7- &f" + description.name + " &7[&a" + description.version + "&7]",
                "",
                "&fThis plugin has been distributed by Matrix Studios.",
                "&fPlugin is not intended to be resold.",
                "",
                "&aWebsite: &fhttps://matrix-studios-software.github.io/",
                "&aDiscord: &fhttps://discord.gg/UMnHT7QCSk",
                "&aGitHub: &fhttps://github.com/Matrix-Studios-Software",
                "&7&m--------------------------------",
            )
        )
    }

    fun updateUniqueServer(server: UniqueServer) {
        Alchemist.globalServer = server
    }


    fun registerExpansion()
    {
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null)
        {
            AlchemistExpansion().register()
        }
    }
}
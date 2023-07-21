package ltd.matrixstudios.alchemist.profiles

import ltd.matrixstudios.alchemist.Alchemist
import ltd.matrixstudios.alchemist.AlchemistSpigotPlugin
import ltd.matrixstudios.alchemist.api.AlchemistAPI
import ltd.matrixstudios.alchemist.metric.Metric
import ltd.matrixstudios.alchemist.metric.MetricService
import ltd.matrixstudios.alchemist.models.grant.types.Punishment
import ltd.matrixstudios.alchemist.packets.StaffGeneralMessagePacket
import ltd.matrixstudios.alchemist.profiles.permissions.AccessiblePermissionHandler
import ltd.matrixstudios.alchemist.profiles.connection.postlog.BukkitPostLoginConnection
import ltd.matrixstudios.alchemist.profiles.connection.prelog.BukkitPreLoginConnection
import ltd.matrixstudios.alchemist.punishments.PunishmentType
import ltd.matrixstudios.alchemist.redis.AsynchronousRedisSender
import ltd.matrixstudios.alchemist.service.expirable.PunishmentService
import ltd.matrixstudios.alchemist.service.expirable.RankGrantService
import ltd.matrixstudios.alchemist.service.profiles.ProfileGameService
import ltd.matrixstudios.alchemist.staff.mode.StaffSuiteManager
import ltd.matrixstudios.alchemist.util.Chat
import ltd.matrixstudios.alchemist.util.SHA
import ltd.matrixstudios.alchemist.util.TimeUtil
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.player.AsyncPlayerPreLoginEvent
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.CompletableFuture
import java.util.logging.Level

/**
 * Class created on 5/27/2023

 * @author 98ping
 * @project Alchemist
 * @website https://solo.to/redis
 */
object BukkitProfileAdaptation {

    fun loadAllEvents()
    {

        for (task in BukkitPreLoginConnection.getAllTasks()) {
            if (!task.shouldBeLazy()) {
                BukkitPreLoginConnection.registerNewCallback {
                    task.run(it)
                }
            } else BukkitPreLoginConnection.registerNewLazyCallback { task.run(it) }
        }

        for (task in BukkitPostLoginConnection.getAllTasks())
        {
            BukkitPostLoginConnection.registerNewCallback {
                task.run(it)
            }
        }
    }
}
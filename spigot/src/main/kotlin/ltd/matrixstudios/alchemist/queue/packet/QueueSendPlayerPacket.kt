package ltd.matrixstudios.alchemist.queue.packet

import com.google.common.io.ByteStreams
import ltd.matrixstudios.alchemist.AlchemistSpigotPlugin
import ltd.matrixstudios.alchemist.redis.AsynchronousRedisSender
import ltd.matrixstudios.alchemist.redis.RedisPacket
import ltd.matrixstudios.alchemist.service.queue.QueueService
import ltd.matrixstudios.alchemist.service.server.UniqueServerService
import ltd.matrixstudios.alchemist.util.Chat
import org.bukkit.Bukkit
import org.bukkit.scheduler.BukkitRunnable
import java.util.*

class QueueSendPlayerPacket(val id: UUID, val queueDestination: String) : RedisPacket("queue-send-player") {

    override fun action() {
        val player = Bukkit.getPlayer(id) ?: return
        QueueService.byId(queueDestination).thenAccept {
            if (it == null) return@thenAccept
            val toSend = UniqueServerService.byId(it.uniqueServerId)

            if (toSend == null) {
                player.sendMessage(Chat.format("&cThe destination server does not exist!"))
                return@thenAccept
            }

            player.sendMessage(Chat.format("&6Connecting you to &f" + it.displayName + "&6..."))
            AsynchronousRedisSender.send(QueueRemovePlayerPacket(it.id, player.uniqueId))
            object : BukkitRunnable() {
                override fun run() {
                    val out = ByteStreams.newDataOutput()
                    out.writeUTF("Connect")
                    out.writeUTF(toSend.bungeeName)
                    player.sendPluginMessage(AlchemistSpigotPlugin.instance, "BungeeCord", out.toByteArray())
                }
            }.runTask(AlchemistSpigotPlugin.instance)
        }
    }
}
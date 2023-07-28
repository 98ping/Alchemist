package ltd.matrixstudios.alchemist.queue.task

import ltd.matrixstudios.alchemist.AlchemistSpigotPlugin
import ltd.matrixstudios.alchemist.api.AlchemistAPI
import ltd.matrixstudios.alchemist.queue.packet.QueueRemovePlayerPacket
import ltd.matrixstudios.alchemist.redis.AsynchronousRedisSender
import ltd.matrixstudios.alchemist.service.queue.QueueService
import ltd.matrixstudios.alchemist.service.server.UniqueServerService
import ltd.matrixstudios.alchemist.util.Chat
import org.bukkit.Bukkit
import org.bukkit.scheduler.BukkitRunnable

class QueueRemindUsersTask : BukkitRunnable() {

    override fun run() {
        for (queue in QueueService.cache.values) {
            for (entry in queue.playersInQueue) {
                val uuid = entry.id
                val bukkitPlayer = Bukkit.getPlayer(uuid) ?: return

                val message = AlchemistSpigotPlugin.instance.config.getStringList("queue-message")

                for (line in message) {
                    val toSend = Chat.format(line
                        .replace("<pos>", queue.getPosition(uuid).toString())
                        .replace("<queue_name>", queue.displayName)
                        .replace("<colored_rank>", AlchemistAPI.getPlayerRankString(uuid))
                        .replace("<front>", (queue.playersInQueue.size - queue.getPosition(uuid)).toString())
                    )

                    bukkitPlayer.sendMessage(toSend)
                }
            }
        }
    }
}
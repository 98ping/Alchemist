package ltd.matrixstudios.alchemist.queue.command

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandPermission
import co.aikar.commands.annotation.Name
import co.aikar.commands.annotation.Subcommand
import ltd.matrixstudios.alchemist.models.queue.QueueStatus
import ltd.matrixstudios.alchemist.queue.command.menu.QueueEditorMenu
import ltd.matrixstudios.alchemist.queue.packet.QueueUpdatePacket
import ltd.matrixstudios.alchemist.redis.AsynchronousRedisSender
import ltd.matrixstudios.alchemist.service.queue.QueueService
import ltd.matrixstudios.alchemist.service.server.UniqueServerService
import ltd.matrixstudios.alchemist.util.Chat
import org.bukkit.entity.Player

/**
 * Class created on 7/12/2023

 * @author 98ping
 * @project Alchemist
 * @website https://solo.to/redis
 */
@CommandAlias("queue")
@CommandPermission("alchemist.queues.admin")
class QueueCommands : BaseCommand() {

    @Subcommand("editor")
    fun editor(player: Player) {
        QueueEditorMenu(player).updateMenu()
    }

    @Subcommand("pause")
    fun pause(player: Player, @Name("queue") queue: String) {
        val existing = QueueService.byId(queue.lowercase())

        existing.thenAccept {
            if (it == null) {
                player.sendMessage(Chat.format("&cThis queue does not exist!"))
                return@thenAccept
            }

            val uniqueServer = UniqueServerService.byId(it.uniqueServerId)

            if (uniqueServer == null) {
                player.sendMessage(Chat.format("&cCannot modify a queue with a null destination!"))
                return@thenAccept
            }

            if (it.status == QueueStatus.PAUSED) {
                it.status = QueueStatus.OPEN
                player.sendMessage(Chat.format("&aYou have unpaused the &f" + it.displayName + " &aqueue!"))
                QueueService.saveQueue(it)
                AsynchronousRedisSender.send(QueueUpdatePacket())
            } else if (it.status == QueueStatus.CLOSED || it.status == QueueStatus.OPEN) {
                it.status = QueueStatus.PAUSED
                player.sendMessage(Chat.format("&cYou have paused the &f" + it.displayName + " &cqueue!"))
                QueueService.saveQueue(it)
                AsynchronousRedisSender.send(QueueUpdatePacket())
            }
        }
    }
}
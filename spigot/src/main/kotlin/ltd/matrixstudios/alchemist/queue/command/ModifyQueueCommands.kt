package ltd.matrixstudios.alchemist.queue.command

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.Name
import ltd.matrixstudios.alchemist.models.queue.player.QueueEntry
import ltd.matrixstudios.alchemist.queue.packet.QueueRemovePlayerPacket
import ltd.matrixstudios.alchemist.queue.packet.QueueUpdatePacket
import ltd.matrixstudios.alchemist.redis.AsynchronousRedisSender
import ltd.matrixstudios.alchemist.service.queue.QueueService
import ltd.matrixstudios.alchemist.util.Chat
import org.bukkit.entity.Player

class ModifyQueueCommands : BaseCommand()
{

    @CommandAlias("joinqueue|jq|play|queuejoin")
    fun joinQueue(player: Player, @Name("queue") queue: String)
    {
        val existing = QueueService.byId(queue.lowercase())

        existing.thenAccept {
            if (it == null)
            {
                player.sendMessage(Chat.format("&cThis queue does not exist!"))
                return@thenAccept
            }

            if (it.playersInQueue.size == it.limit)
            {
                player.sendMessage(Chat.format("&cThis queue has reached full capacity!"))
                return@thenAccept
            }

            val current = QueueService.playerAlreadyQueued(player.uniqueId)

            if (current != null)
            {
                player.sendMessage(Chat.format("&cYou must leave your current queue in order to join a new one!"))
                return@thenAccept
            }

            val entry = QueueEntry(player.uniqueId, System.currentTimeMillis())
            it.playersInQueue.add(entry)
            QueueService.saveQueue(it)
            AsynchronousRedisSender.send(QueueUpdatePacket())
            player.sendMessage(Chat.format("&aYou have joined the queue for &f" + it.displayName))
        }
    }

    @CommandAlias("checkqueue")
    fun checkQueue(player: Player)
    {
        val current = QueueService.playerAlreadyQueued(player.uniqueId)

        if (current == null)
        {
            player.sendMessage(Chat.format("&cYou are not in a queue!"))
            return
        }

        player.sendMessage(Chat.format("&e&lQueue Status"))
        player.sendMessage(Chat.format("&7&m---------------------------------"))
        player.sendMessage(Chat.format("&eName: &f" + current.displayName))
        player.sendMessage(Chat.format("&eSlots: &f" + current.playersInQueue.size + "/" + current.limit))
        player.sendMessage(Chat.format("&eYour Position: &f#" + current.getPosition(player.uniqueId)))
        player.sendMessage(Chat.format("&7&m---------------------------------"))
    }

    @CommandAlias("leavequeue|lq|queueleave")
    fun leaveQueue(player: Player)
    {
        val current = QueueService.playerAlreadyQueued(player.uniqueId)

        if (current == null)
        {
            player.sendMessage(Chat.format("&cYou are not in a queue so you are unable to leave it!"))
            return
        }

        AsynchronousRedisSender.send(QueueRemovePlayerPacket(current.id, player.uniqueId))
        player.sendMessage(Chat.format("&cYou have left the &f" + current.displayName + " &cqueue!"))
    }
}
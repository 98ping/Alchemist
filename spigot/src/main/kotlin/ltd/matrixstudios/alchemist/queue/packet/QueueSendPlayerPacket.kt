package ltd.matrixstudios.alchemist.queue.packet

import com.google.common.io.ByteStreams
import ltd.matrixstudios.alchemist.AlchemistSpigotPlugin
import ltd.matrixstudios.alchemist.redis.RedisPacket
import ltd.matrixstudios.alchemist.service.queue.QueueService
import ltd.matrixstudios.alchemist.service.server.UniqueServerService
import ltd.matrixstudios.alchemist.util.Chat
import org.bukkit.Bukkit
import java.util.*

class QueueSendPlayerPacket(val id: UUID, val queueDestination: String) : RedisPacket("queue-send-player")
{

    override fun action()
    {
        val player = Bukkit.getPlayer(id) ?: return
        val queue = QueueService.byId(queueDestination).get() ?: return
        val toSend = UniqueServerService.byId(queue.uniqueServerId)

        if (toSend == null)
        {
            player.sendMessage(Chat.format("&cThe destination server does not exist!"))
            return
        }

        player.sendMessage(Chat.format("&6Connecting you to &f" + queue.displayName + "&6..."))

        val out = ByteStreams.newDataOutput()
        out.writeUTF("Connect")
        out.writeUTF(toSend.bungeeName)
        player.sendPluginMessage(AlchemistSpigotPlugin.instance, "BungeeCord", out.toByteArray())
    }
}
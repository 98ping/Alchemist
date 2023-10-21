package ltd.matrixstudios.alchemist.servers.commands.task

import ltd.matrixstudios.alchemist.Alchemist
import ltd.matrixstudios.alchemist.packets.StaffGeneralMessagePacket
import ltd.matrixstudios.alchemist.redis.AsynchronousRedisSender
import ltd.matrixstudios.alchemist.servers.packets.ExplicitServerWhitelistPacket
import ltd.matrixstudios.alchemist.service.server.UniqueServerService
import ltd.matrixstudios.alchemist.util.Chat
import org.bukkit.scheduler.BukkitRunnable

class ServerReleaseTask : BukkitRunnable()
{

    override fun run()
    {
        val server = Alchemist.globalServer

        if (server.setToRelease != -1L && (server.setToRelease - System.currentTimeMillis()) <= 0L)
        {
            AsynchronousRedisSender.send(StaffGeneralMessagePacket(Chat.format("&8[&eServer Monitor&8] &fInstance &a" + server.displayName + " &fhas been automatically &eunwhitelisted")))
            server.setToRelease = -1L
            AsynchronousRedisSender.send(ExplicitServerWhitelistPacket(server.id, false))

            UniqueServerService.save(server)
        }
    }
}
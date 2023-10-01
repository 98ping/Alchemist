package ltd.matrixstudios.alchemist.party

import ltd.matrixstudios.alchemist.network.NetworkManager
import ltd.matrixstudios.alchemist.redis.AsynchronousRedisSender
import ltd.matrixstudios.alchemist.packets.NetworkMessagePacket
import ltd.matrixstudios.alchemist.service.party.PartyService
import ltd.matrixstudios.alchemist.util.Chat
import org.bukkit.scheduler.BukkitRunnable
import java.util.concurrent.TimeUnit

class DecayingPartyTask : BukkitRunnable() {

    override fun run() {
        val parties = PartyService.handler.getAll().get()

        for (party in parties)
        {

            var changed = false

            if (changed)
            {
                PartyService.handler.insert(party.id, party)
            }

        }
    }
}
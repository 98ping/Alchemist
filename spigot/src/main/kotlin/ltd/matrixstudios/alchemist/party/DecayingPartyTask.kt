package ltd.matrixstudios.alchemist.party

import ltd.matrixstudios.alchemist.Alchemist
import ltd.matrixstudios.alchemist.redis.AsynchronousRedisSender
import ltd.matrixstudios.alchemist.redis.impl.NetworkMessagePacket
import ltd.matrixstudios.alchemist.service.party.PartyService
import org.bukkit.scheduler.BukkitRunnable
import java.util.concurrent.TimeUnit

object DecayingPartyTask : BukkitRunnable() {

    override fun run() {
        val parties = PartyService.getValues()

        for (party in parties)
        {

            if (System.currentTimeMillis() - party.createdAt >= TimeUnit.SECONDS.toMillis(30L) && !party.alive)
            {
                PartyService.handler.delete(party.id)

                return
            }

            if (party.getAllMembers().size > 0)
            {
                party.alive = true
            }

            for (invite in party.invited)
            {
                if ((System.currentTimeMillis() - invite.value)  >= TimeUnit.MINUTES.toMillis(1L))
                {
                    party.invited.remove(invite.key)
                }
            }

            PartyService.handler.storeAsync(party.id, party)
        }
    }
}
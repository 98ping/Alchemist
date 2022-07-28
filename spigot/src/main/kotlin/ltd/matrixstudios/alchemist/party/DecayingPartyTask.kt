package ltd.matrixstudios.alchemist.party

import ltd.matrixstudios.alchemist.network.NetworkManager
import ltd.matrixstudios.alchemist.redis.AsynchronousRedisSender
import ltd.matrixstudios.alchemist.redis.impl.NetworkMessagePacket
import ltd.matrixstudios.alchemist.service.party.PartyService
import org.bukkit.scheduler.BukkitRunnable
import java.util.concurrent.TimeUnit

class DecayingPartyTask : BukkitRunnable() {

    override fun run() {
        val parties = PartyService.getValues()

        for (party in parties)
        {


            if (NetworkManager.hasFullyDCed(party.leader))
            {
                party.members.forEach {
                    AsynchronousRedisSender.send(NetworkMessagePacket(it.first, "&8[&dParties&8] &fYour party has been &cdisbanded"))
                }

                PartyService.handler.delete(party.id)

                return
            }

            party.members.map { it.first }.forEach {
                if (NetworkManager.hasFullyDCed(it))
                {
                    party.removeMember(it)
                }
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
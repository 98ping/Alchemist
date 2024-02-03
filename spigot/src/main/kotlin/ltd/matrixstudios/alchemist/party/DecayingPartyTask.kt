package ltd.matrixstudios.alchemist.party

import ltd.matrixstudios.alchemist.service.party.PartyService
import org.bukkit.scheduler.BukkitRunnable

class DecayingPartyTask : BukkitRunnable()
{

    override fun run()
    {
        val parties = PartyService.handler.retrieveAllAsync().get()

        for (party in parties)
        {

            var changed = false

            if (changed)
            {
                PartyService.handler.store(party.id, party)
            }

        }
    }
}
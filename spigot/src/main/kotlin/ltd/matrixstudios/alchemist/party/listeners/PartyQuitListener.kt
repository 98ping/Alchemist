package ltd.matrixstudios.alchemist.party.listeners

import ltd.matrixstudios.alchemist.AlchemistSpigotPlugin
import ltd.matrixstudios.alchemist.networking.NetworkManager
import ltd.matrixstudios.alchemist.redis.AsynchronousRedisSender
import ltd.matrixstudios.alchemist.redis.impl.NetworkMessagePacket
import ltd.matrixstudios.alchemist.service.party.PartyService
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.scheduler.BukkitRunnable
import java.util.concurrent.TimeUnit

class PartyQuitListener : Listener {

    @EventHandler
    fun quit(e: PlayerQuitEvent)
    {
        val party = PartyService.getParty(e.player.uniqueId)

        if (party != null)
        {
            object : BukkitRunnable() {
                override fun run() {
                    if (party.leader == e.player.uniqueId && NetworkManager.hasFullyDCed(e.player.uniqueId))
                    {
                        party.members.forEach {
                            AsynchronousRedisSender.send(NetworkMessagePacket(it.first, "&8[&dParties&8] &fYour party has been &cdisbanded"))
                        }
                        PartyService.handler.delete(party.id)
                    }

                    if (party.members.map { it.first }.contains(e.player.uniqueId) && NetworkManager.hasFullyDCed(e.player.uniqueId))
                    {
                        party.removeMember(e.player.uniqueId)
                    }
                }

            }.runTaskLater(AlchemistSpigotPlugin.instance, TimeUnit.MINUTES.toMillis(5L))
        }
    }
}
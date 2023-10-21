package ltd.matrixstudios.alchemist.staff.mode.listeners

import ltd.matrixstudios.alchemist.util.Chat
import org.bukkit.Location
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.player.PlayerMoveEvent

class FrozenPlayerListener : Listener
{

    @EventHandler(priority = EventPriority.HIGHEST)
    fun move(e: PlayerMoveEvent)
    {
        val player = e.player

        if (player.hasMetadata("frozen"))
        {
            val from: Location = e.from
            val to: Location = e.to
            if (from.x != to.x || e.from.z != e.to.z)
            {
                val newLocation: Location = from.block.location.add(0.5, 0.0, 0.5)
                newLocation.pitch = to.pitch
                newLocation.yaw = to.yaw
                e.to = newLocation
            }
        }
    }

    @EventHandler
    fun damage(e: EntityDamageByEntityEvent)
    {
        val entity = e.entity

        if (entity is Player)
        {
            if (entity.hasMetadata("frozen"))
            {
                val damager = e.damager

                damager.sendMessage(Chat.format("&cPlayer is currently frozen and cannot be damaged"))

                e.isCancelled = true
            }
        }
    }

    @EventHandler
    fun damageEntity(e: EntityDamageByEntityEvent)
    {
        val entity = e.damager

        if (entity is Player)
        {
            if (entity.hasMetadata("frozen"))
            {
                entity.sendMessage(Chat.format("&cYou are currently frozen"))

                e.isCancelled = true
            }
        }
    }
}
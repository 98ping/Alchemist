package ltd.matrixstudios.alchemist.staff.mode.listeners

import ltd.matrixstudios.alchemist.staff.mode.StaffItems
import ltd.matrixstudios.alchemist.staff.mode.StaffSuiteManager
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.player.PlayerDropItemEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerPickupItemEvent
import org.bukkit.event.player.PlayerQuitEvent

class GenericStaffmodePreventionListener : Listener {

    @EventHandler
    fun breakBlock(e: BlockBreakEvent)
    {
        val player = e.player

        if (StaffSuiteManager.isModMode(player))
        {
            if (!player.hasPermission("alchemist.staffmode.edit"))
            {
                e.isCancelled = true
            }
        }
    }

    @EventHandler
    fun placeBlock(e: BlockPlaceEvent)
    {
        val player = e.player

        if (StaffSuiteManager.isModMode(player))
        {
            if (!player.hasPermission("alchemist.staffmode.edit"))
            {
                e.isCancelled = true
            }
        }
    }

    @EventHandler
    fun interact(e: PlayerInteractEvent)
    {
        val player = e.player

        if (StaffSuiteManager.isModMode(player))
        {

            if (e.action == Action.PHYSICAL)
            {
                e.isCancelled = true
                return
            }

            if (!player.hasPermission("alchemist.staffmode.edit"))
            {
                e.isCancelled = true
            }
        }
    }

    @EventHandler
    fun damage(e: EntityDamageEvent)
    {
        val player = e.entity

        if (player is Player)
        {

            if (StaffSuiteManager.isModMode(player))
            {
                e.isCancelled = true
            }
        }
    }

    @EventHandler
    fun moveItem(e: InventoryClickEvent)
    {
        val player = e.whoClicked

        if (player is Player)
        {

            if (StaffSuiteManager.isModMode(player))
            {
                if (!player.hasPermission("alchemist.staffmode.edit"))
                {
                    e.isCancelled = true
                }
            }
        }
    }

    @EventHandler
    fun pickup(e: PlayerPickupItemEvent)
    {
        if (StaffSuiteManager.isModMode(e.player))
        {
            e.isCancelled = true
        }
    }

    @EventHandler
    fun drop(e: PlayerDropItemEvent) {
        if (StaffSuiteManager.isModMode(e.player))
        {
            if (!e.player.hasPermission("alchemist.staffmode.edit"))
            {
                e.isCancelled = true
            }
        }
    }

    @EventHandler
    fun playerQuit(e: PlayerQuitEvent) {
        if (StaffSuiteManager.isModMode(e.player))
        {
           StaffSuiteManager.removeStaffMode(e.player)
        }
    }


    @EventHandler
    fun damagedBy(e: EntityDamageByEntityEvent)
    {
        val player = e.entity
        val damager = e.damager

        if (player is Player && damager is Player)
        {

            if (StaffSuiteManager.isModMode(player))
            {
                e.isCancelled = true
            }
        }
    }

    @EventHandler
    fun damagedFrom(e: EntityDamageByEntityEvent)
    {
        val player = e.entity
        val damager = e.damager

        if (player is Player && damager is Player)
        {

            StaffItems.lastPvP = player.location

            if (StaffSuiteManager.isModMode(damager))
            {
                e.isCancelled = true
            }
        }
    }
}
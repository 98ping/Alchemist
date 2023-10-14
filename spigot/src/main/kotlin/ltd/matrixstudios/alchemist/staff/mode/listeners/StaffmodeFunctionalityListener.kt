package ltd.matrixstudios.alchemist.staff.mode.listeners

import ltd.matrixstudios.alchemist.AlchemistSpigotPlugin
import ltd.matrixstudios.alchemist.api.AlchemistAPI
import ltd.matrixstudios.alchemist.staff.mode.StaffItems
import ltd.matrixstudios.alchemist.staff.mode.StaffSuiteVisibilityHandler
import ltd.matrixstudios.alchemist.staff.mode.StaffSuiteManager
import ltd.matrixstudios.alchemist.staff.mode.menu.OnlineStaffMenu
import ltd.matrixstudios.alchemist.staff.settings.edit.menu.EditModModeMenu
import ltd.matrixstudios.alchemist.util.Chat
import ltd.matrixstudios.alchemist.util.items.ItemBuilder
import ltd.matrixstudios.alchemist.util.skull.SkullUtil
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.player.PlayerInteractEntityEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.metadata.FixedMetadataValue
import java.text.SimpleDateFormat
import java.util.*

class StaffmodeFunctionalityListener : Listener {
    val timestamps = mutableMapOf<UUID, Long>()
    val entityInteractTimestamps = mutableMapOf<UUID, Long>()

    @EventHandler(priority = EventPriority.HIGHEST)
    fun interact(e: PlayerInteractEvent) {
        val player = e.player

        if (StaffSuiteManager.isModMode(player))
        {
            if (e.action == Action.RIGHT_CLICK_AIR || e.action == Action.RIGHT_CLICK_BLOCK)
            {

                val itemInHand = player.itemInHand

                if (itemInHand == null || itemInHand.type == Material.AIR) return

                val time = timestamps[player.uniqueId]
                if (time != null) {
                    if (System.currentTimeMillis().minus(time) < 300L) {
                        e.isCancelled = true
                        timestamps.remove(player.uniqueId)

                        return
                    }
                }

                timestamps[player.uniqueId] = System.currentTimeMillis()

                if (itemInHand.isSimilar(StaffItems.RANDOMTP))
                {
                    e.isCancelled = true
                    val actualPlayer = Bukkit.getOnlinePlayers().shuffled().first()

                    if (actualPlayer == null)
                    {
                        player.sendMessage(Chat.format("&cAcutal player nulled"))
                        return
                    }

                    if (actualPlayer == player)
                    {
                        player.sendMessage(Chat.format("&cYou cannot teleport to yourself"))
                        return
                    }


                    player.teleport(actualPlayer)
                    player.sendMessage(Chat.format("&6Teleporting..."))
                }

                // only proceed if they have the online staff item
                if (itemInHand.hasItemMeta() && itemInHand.itemMeta.hasDisplayName() && itemInHand.itemMeta.displayName.contains("Online Staff"))
                {
                    e.isCancelled = true
                    OnlineStaffMenu(player).updateMenu()
                }

                if (itemInHand.isSimilar(StaffItems.LAST_PVP))
                {
                    e.isCancelled = true
                    val location = StaffItems.lastPvP

                    if (location == null)
                    {
                        player.sendMessage(Chat.format("&cNobody has fought anyone yet!"))
                        return
                    }

                    player.teleport(location)
                }

                if (itemInHand.isSimilar(StaffItems.VANISH))
                {
                    player.inventory.itemInHand = StaffItems.UNVANISH

                    StaffSuiteVisibilityHandler.onDisableVisbility(player)

                    player.removeMetadata("vanish", AlchemistSpigotPlugin.instance)
                }

                if (itemInHand.isSimilar(StaffItems.UNVANISH))
                {
                    player.inventory.itemInHand = StaffItems.VANISH

                    StaffSuiteVisibilityHandler.onEnableVisibility(player)

                    player.setMetadata("vanish", FixedMetadataValue(AlchemistSpigotPlugin.instance, true))
                }

                if (itemInHand.isSimilar(StaffItems.INVENTORY_INSPECT))
                {
                    e.isCancelled = true
                }

                if (itemInHand.isSimilar(StaffItems.FREEZE))
                {
                    e.isCancelled = true
                }
            }
        }
    }

    @EventHandler
    fun inventoryClickEvent(e: InventoryClickEvent)
    {
        val player = e.whoClicked as Player

        if (StaffSuiteManager.isModMode(player))
        {
            if (e.currentItem != null)
            {
                if (e.currentItem.isSimilar(StaffItems.EDIT_MOD_MODE))
                {
                    EditModModeMenu(player).openMenu()
                    player.sendMessage(Chat.format("&eYou are now editing your &amod mode"))
                    player.sendMessage(Chat.format("&7&oTo save any changes, execute /savemodmode"))
                    e.isCancelled = true
                }
            }
        }
    }

    @EventHandler
    fun interactWithEntity(e: PlayerInteractEntityEvent)
    {
        val player = e.player

        if (StaffSuiteManager.isModMode(player))
        {
            val itemInHand = player.itemInHand

            if (itemInHand == null || itemInHand.type == Material.AIR) return

            if (e.rightClicked is Player)
            {
                val time = entityInteractTimestamps[player.uniqueId]
                if (time != null) {
                    if (System.currentTimeMillis().minus(time) < 300L) {
                        e.isCancelled = true
                        entityInteractTimestamps.remove(player.uniqueId)

                        return
                    }
                }

                entityInteractTimestamps[player.uniqueId] = System.currentTimeMillis()

                if (itemInHand.isSimilar(StaffItems.INVENTORY_INSPECT))
                {
                    player.performCommand("invsee ${e.rightClicked.name}")
                    e.isCancelled = true
                }

                if (itemInHand.isSimilar(StaffItems.FREEZE))
                {
                    player.performCommand("freeze ${e.rightClicked.name}")
                    e.isCancelled = true
                }
            }
        }
    }
}
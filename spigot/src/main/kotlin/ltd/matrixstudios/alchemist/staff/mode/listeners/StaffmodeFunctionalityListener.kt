package ltd.matrixstudios.alchemist.staff.mode.listeners

import ltd.matrixstudios.alchemist.CompoundPlugin
import ltd.matrixstudios.alchemist.chat.Chat
import ltd.matrixstudios.alchemist.staff.mode.StaffItems
import ltd.matrixstudios.alchemist.staff.mode.StaffSuiteVisibilityHandler
import ltd.matrixstudios.alchemist.staff.menu.StaffOnlineMenu
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEntityEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.metadata.FixedMetadataValue

class StaffmodeFunctionalityListener : Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    fun interact(e: PlayerInteractEvent) {
        val player = e.player

        if (CompoundPlugin.instance.staffManager.isModMode(player))
        {
            if (e.action == Action.RIGHT_CLICK_AIR || e.action == Action.RIGHT_CLICK_BLOCK)
            {

                val itemInHand = player.itemInHand


                if (itemInHand.isSimilar(StaffItems.RANDOMTP))
                {
                    e.isCancelled = true
                    val actualPlayer = Bukkit.getOnlinePlayers().shuffled().first()

                    if (actualPlayer == null)
                    {
                        player.sendMessage(Chat.format("&cThis mf wasn't even found LOL"))
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

                if (itemInHand.isSimilar(StaffItems.VANISH))
                {
                    player.inventory.itemInHand = StaffItems.UNVANISH

                    StaffSuiteVisibilityHandler.onDisableVisbility(player)

                    player.removeMetadata("vanish", CompoundPlugin.instance)
                }

                if (itemInHand.isSimilar(StaffItems.UNVANISH))
                {
                    player.inventory.itemInHand = StaffItems.VANISH

                    StaffSuiteVisibilityHandler.onEnableVisibility(player)

                    player.setMetadata("vanish", FixedMetadataValue(CompoundPlugin.instance, true))
                }

                if (itemInHand.isSimilar(StaffItems.ONLINE_STAFF))
                {
                    e.isCancelled = true
                    StaffOnlineMenu(player).updateMenu()
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
    fun interactWithEntity(e: PlayerInteractEntityEvent)
    {
        val player = e.player

        if (CompoundPlugin.instance.staffManager.isModMode(player))
        {
            val itemInHand = player.itemInHand

            if (e.rightClicked is Player)
            {
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
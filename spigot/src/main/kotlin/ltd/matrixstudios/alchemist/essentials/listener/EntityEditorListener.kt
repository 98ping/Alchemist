package ltd.matrixstudios.alchemist.essentials.listener

import ltd.matrixstudios.alchemist.essentials.commands.EntityCommands
import ltd.matrixstudios.alchemist.essentials.menus.entity.EntityEditorMenu
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityInteractEvent
import org.bukkit.event.player.PlayerInteractEntityEvent

class EntityEditorListener : Listener
{

    @EventHandler
    fun onEntityClick(e: PlayerInteractEntityEvent)
    {
        val entity = e.rightClicked ?: return
        val item = e.player.itemInHand

        if (item.isSimilar(EntityCommands.item))
        {
            if (e.player.hasPermission("alchemist.essentials.editentity"))
            {
                EntityEditorMenu(e.player, entity).openMenu()
            }
        }
    }
}
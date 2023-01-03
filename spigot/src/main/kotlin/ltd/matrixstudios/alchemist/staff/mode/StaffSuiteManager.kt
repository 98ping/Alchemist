package ltd.matrixstudios.alchemist.staff.mode

import ltd.matrixstudios.alchemist.AlchemistSpigotPlugin
import org.bukkit.GameMode
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.metadata.FixedMetadataValue
import java.util.*

object StaffSuiteManager {

    var modInventories = hashMapOf<UUID, Array<ItemStack?>>()
    var modArmor = hashMapOf<UUID, Array<ItemStack?>>()

    var modModePlayers = arrayListOf<UUID>()

    fun isModMode(player: Player) : Boolean
    {
        return modModePlayers.contains(player.uniqueId)
    }

    fun removeStaffMode(player: Player)
    {
        player.gameMode = GameMode.SURVIVAL
        player.inventory.clear()

        player.spigot().collidesWithEntities = true

        if (modInventories.containsKey(player.uniqueId)) {
            val items = modInventories[player.uniqueId]!!

            player.inventory.contents = items
        }

        if (modArmor.containsKey(player.uniqueId))
        {

            val armor = modArmor[player.uniqueId]!!

            player.inventory.armorContents = armor
        }

        player.updateInventory()

        modModePlayers.remove(player.uniqueId)
        modInventories.remove(player.uniqueId)
        modArmor.remove(player.uniqueId)

        StaffSuiteVisibilityHandler.onDisableVisbility(player)

        player.removeMetadata("modmode", AlchemistSpigotPlugin.instance)
        player.removeMetadata("vanish", AlchemistSpigotPlugin.instance)

    }

    fun setStaffMode(player: Player)
    {
        player.gameMode = GameMode.CREATIVE
        player.health = 20.0

        modInventories[player.uniqueId] = player.inventory.contents
        modArmor[player.uniqueId] = player.inventory.armorContents
        modModePlayers.add(player.uniqueId)

        player.inventory.clear()
        player.inventory.armorContents = null

        StaffItems.equip(player)

        //makes it so arrows dont hit you when you are in staff mode
        player.spigot().collidesWithEntities = false

        player.updateInventory()

        StaffSuiteVisibilityHandler.onEnableVisibility(player)

        player.setMetadata("modmode", FixedMetadataValue(AlchemistSpigotPlugin.instance, true))
        player.setMetadata("vanish", FixedMetadataValue(AlchemistSpigotPlugin.instance, true))
    }
}
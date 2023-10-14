package ltd.matrixstudios.alchemist.staff.mode

import ltd.matrixstudios.alchemist.redis.RedisPacketManager
import ltd.matrixstudios.alchemist.serialize.Serializers
import ltd.matrixstudios.alchemist.serialize.type.ItemStackAdapter
import ltd.matrixstudios.alchemist.util.Chat
import ltd.matrixstudios.alchemist.util.items.ItemBuilder
import ltd.matrixstudios.alchemist.util.skull.SkullUtil
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

object StaffItems {

    val COMPASS = ItemBuilder.of(Material.COMPASS).name("&bCompass").build()
    val INVENTORY_INSPECT = ItemBuilder.of(Material.BOOK).name("&bInspect Inventory").build()
    val RANDOMTP = ItemBuilder.of(Material.BEACON).name("&bRandom TP").build()
    val WORLDEDIT_AXE = ItemBuilder.of(Material.WOOD_AXE).name("&bWorldedit Axe").build()
    val BETTER_VIEW = ItemBuilder.of(Material.CARPET).data(7).name("&bBetter view").build()
    val VANISH = ItemBuilder.of(Material.INK_SACK).data(8).name("&bUnvanish").build()
    val UNVANISH = ItemBuilder.of(Material.INK_SACK).data(10).name("&bVanish").build()
    val FREEZE = ItemBuilder.of(Material.ICE).name("&bFreeze Player").build()
    val LAST_PVP = ItemBuilder.of(Material.EMERALD).name("&bLast PvP").build()
    val EDIT_MOD_MODE = ItemBuilder.of(Material.EMERALD).name(Chat.format("&a&lEdit Mod Mode")).build()

    val ITEMS_IN_LIST = listOf(COMPASS, INVENTORY_INSPECT, RANDOMTP, WORLDEDIT_AXE, BETTER_VIEW, VANISH, UNVANISH, FREEZE, LAST_PVP, EDIT_MOD_MODE)

    var lastPvP: Location? = null

    fun equip(player: Player)
    {
        val resource = RedisPacketManager.pool.resource

        resource.use {
            val item = it.hget("Alchemist:ModMode:", player.uniqueId.toString())

            if (item != null)
            {
                val items = ItemStackAdapter.itemStackArrayFromBase64(item)

                player.inventory.contents = items
            } else {
                player.inventory.setItem(0, COMPASS)
                player.inventory.setItem(1, INVENTORY_INSPECT)
                player.inventory.setItem(2, RANDOMTP)
                player.inventory.setItem(3, BETTER_VIEW)

                if (player.hasPermission("alchemist.staffmode.worldedit"))
                {
                    player.inventory.setItem(4, WORLDEDIT_AXE)
                }

                player.inventory.setItem(6, ItemBuilder.copyOf(SkullUtil.generate(player.name, "")).name("&bOnline Staff").build())
                player.inventory.setItem(7, VANISH)
                player.inventory.setItem(8, FREEZE)
            }
        }

        player.inventory.setItem(22, EDIT_MOD_MODE)

    }

}
package ltd.matrixstudios.alchemist.staff.mode

import ltd.matrixstudios.alchemist.util.items.ItemBuilder
import org.bukkit.Material

object StaffItems {

    val COMPASS = ItemBuilder.of(Material.COMPASS).name("&bCompass").build()
    val INVENTORY_INSPECT = ItemBuilder.of(Material.BOOK).name("&bInspect Inventory").build()
    val RANDOMTP = ItemBuilder.of(Material.BEACON).name("&bRandom TP").build()
    val WORLDEDIT_AXE = ItemBuilder.of(Material.WOOD_AXE).name("&bWorldedit Axe").build()
    val BETTER_VIEW = ItemBuilder.of(Material.CARPET).data(7).name("&bBetter view").build()
    val ONLINE_STAFF = ItemBuilder.of(Material.SKULL_ITEM).name("&bOnline Staff").build()
    val VANISH = ItemBuilder.of(Material.INK_SACK).data(8).name("&bUnvanish").build()
    val UNVANISH = ItemBuilder.of(Material.INK_SACK).data(10).name("&bVanish").build()
    val FREEZE = ItemBuilder.of(Material.ICE).name("&bFreeze Player").build()

}
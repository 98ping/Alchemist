package ltd.matrixstudios.alchemist.util.menu

import ltd.matrixstudios.alchemist.util.Chat
import ltd.matrixstudios.alchemist.util.menu.buttons.PlaceholderButton
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import org.bukkit.inventory.ItemStack

abstract class Button
{

    abstract fun getMaterial(player: Player): Material
    abstract fun getDescription(player: Player): MutableList<String>?
    abstract fun getDisplayName(player: Player): String?
    abstract fun getData(player: Player): Short

    abstract fun onClick(player: Player, slot: Int, type: ClickType)

    open fun getButtonItem(player: Player): ItemStack?
    {
        return null
    }

    open fun setCustomAmount(player: Player): Int
    {
        return 1
    }

    companion object
    {
        fun placeholder(): Button
        {
            return PlaceholderButton(Material.STAINED_GLASS_PANE, mutableListOf(), Chat.format("&f"), 7)
        }
    }

    fun constructItemStack(player: Player): ItemStack
    {
        if (getButtonItem(player) != null)
        {
            return getButtonItem(player)!!
        }

        var material = getMaterial(player)
        val data = getData(player)

        val typeName = material.name
        if (typeName == "WOOL" || typeName == "STAINED_GLASS_PANE" || typeName == "STAINED_GLASS" || typeName == "STAINED_CLAY" || typeName == "CARPET") {
            try {
                val dye = org.bukkit.DyeColor.getByWoolData(data.toByte())
                if (dye != null) {
                    val suffix = when (typeName) {
                        "STAINED_GLASS_PANE" -> "_STAINED_GLASS_PANE"
                        "STAINED_GLASS" -> "_STAINED_GLASS"
                        "STAINED_CLAY" -> "_TERRACOTTA"
                        "CARPET" -> "_CARPET"
                        else -> "_WOOL"
                    }
                    val modernName = dye.name + suffix
                    var modern: org.bukkit.Material? = null
                    try {
                        val method = org.bukkit.Material::class.java.getMethod("matchMaterial", String::class.java, Boolean::class.javaPrimitiveType)
                        modern = method.invoke(null, modernName, false) as org.bukkit.Material?
                    } catch (e: Exception) {}
                    if (modern != null) {
                        material = modern
                    }
                }
            } catch (e: Exception) {}
        }

        val itemStack = ItemStack(material)
        if (material == getMaterial(player)) {
            itemStack.durability = data
        }

        val itemMeta = itemStack.itemMeta

        itemMeta.displayName = getDisplayName(player)
        itemMeta.lore = getDescription(player)
        itemStack.itemMeta = itemMeta
        itemStack.amount = setCustomAmount(player)

        return itemStack
    }
}
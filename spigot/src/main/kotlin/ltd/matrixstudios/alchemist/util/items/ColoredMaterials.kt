package ltd.matrixstudios.alchemist.util.items

import com.cryptomorin.xseries.XMaterial
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

object ColoredMaterials
{
    private val BLOCK_COLORS = arrayOf(
        "WHITE", "ORANGE", "MAGENTA", "LIGHT_BLUE", "YELLOW", "LIME", "PINK", "GRAY",
        "LIGHT_GRAY", "CYAN", "PURPLE", "BLUE", "BROWN", "GREEN", "RED", "BLACK"
    )

    private val DYE_COLORS = arrayOf(
        "BLACK", "RED", "GREEN", "BROWN", "BLUE", "PURPLE", "CYAN", "LIGHT_GRAY",
        "GRAY", "PINK", "LIME", "YELLOW", "LIGHT_BLUE", "MAGENTA", "ORANGE", "WHITE"
    )

    fun resolve(material: Material, data: Short): ItemStack?
    {
        val suffix = when (material.name)
        {
            "WOOL" -> "_WOOL"
            "CARPET" -> "_CARPET"
            "STAINED_GLASS" -> "_STAINED_GLASS"
            "STAINED_GLASS_PANE" -> "_STAINED_GLASS_PANE"
            "STAINED_CLAY" -> "_TERRACOTTA"
            "INK_SACK", "DYE" -> "_DYE"
            else -> return null
        }

        val index = data.toInt().coerceIn(0, 15)
        val colour = if (suffix == "_DYE") DYE_COLORS[index] else BLOCK_COLORS[index]

        return XMaterial.matchXMaterial(colour + suffix)
            .map { it.parseItem() }
            .orElse(null)
    }
}

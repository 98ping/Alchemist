package ltd.matrixstudios.alchemist.util.menu.buttons

import ltd.matrixstudios.alchemist.util.menu.Button
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType

class PlaceholderButton(
    val material: Material,
    val description: MutableList<String>?,
    val name: String?, val data: Short
) : Button()
{

    override fun getMaterial(player: Player): Material
    {
        return material
    }

    override fun getDescription(player: Player): MutableList<String>
    {
        return description ?: mutableListOf()
    }

    override fun getDisplayName(player: Player): String
    {
        if (name != null)
        {
            return name
        } else
        {
            return material.name
        }

    }

    override fun getData(player: Player): Short
    {
        return data
    }

    override fun onClick(player: Player, slot: Int, type: ClickType)
    {

    }
}
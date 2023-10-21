package ltd.matrixstudios.alchemist.staff.settings.edit.menu

import ltd.matrixstudios.alchemist.util.items.ItemBuilder.Companion.of
import ltd.matrixstudios.alchemist.util.menu.Button
import ltd.matrixstudios.alchemist.util.menu.Menu
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import org.bukkit.inventory.ItemStack


class EditModModeMenu(val player: Player) : Menu(player)
{

    init
    {
        staticSize = 18
        stealable = true
    }

    override fun getButtons(player: Player): MutableMap<Int, Button>
    {
        val buttons = hashMapOf<Int, Button>()
        buttons[0] = EditModModeMenuButton(of(Material.CARPET).data(14.toShort()).name("&bBetter View").build())
        buttons[1] = EditModModeMenuButton(of(Material.CARPET).data(1.toShort()).name("&bBetter View").build())
        buttons[2] = EditModModeMenuButton(of(Material.CARPET).data(4.toShort()).name("&bBetter View").build())
        buttons[3] = EditModModeMenuButton(of(Material.CARPET).data(5.toShort()).name("&bBetter View").build())
        buttons[4] = EditModModeMenuButton(of(Material.CARPET).data(9.toShort()).name("&bBetter View").build())
        buttons[5] = EditModModeMenuButton(of(Material.CARPET).data(11.toShort()).name("&bBetter View").build())
        buttons[6] = EditModModeMenuButton(of(Material.CARPET).data(6.toShort()).name("&bBetter View").build())
        buttons[7] = EditModModeMenuButton(of(Material.CARPET).data(2.toShort()).name("&bBetter View").build())
        buttons[8] = EditModModeMenuButton(of(Material.CARPET).data(7.toShort()).name("&bBetter View").build())
        buttons[9] = EditModModeMenuButton(of(Material.EMERALD).name("&bLast PvP").build())
        return buttons
    }

    override fun getTitle(player: Player): String
    {
        return "Edit your ModMode"
    }

    class EditModModeMenuButton(val itemStack: ItemStack) : Button()
    {
        override fun getMaterial(player: Player): Material
        {
            return Material.DIRT
        }

        override fun getDescription(player: Player): MutableList<String>
        {
            return mutableListOf()
        }

        override fun getDisplayName(player: Player): String
        {
            return ""
        }

        override fun getButtonItem(player: Player): ItemStack
        {
            return itemStack
        }

        override fun getData(player: Player): Short
        {
            return 0
        }

        override fun onClick(player: Player, slot: Int, type: ClickType)
        {
        }


    }
}
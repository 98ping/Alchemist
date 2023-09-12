package ltd.matrixstudios.alchemist.staff.requests.commands.menu

import ltd.matrixstudios.alchemist.util.menu.Button
import ltd.matrixstudios.alchemist.util.menu.Menu
import ltd.matrixstudios.alchemist.util.menu.buttons.SimpleActionButton
import org.bukkit.Material
import org.bukkit.entity.Player

class ReportSelectCategoryMenu(val player: Player) : Menu(player)
{
    init
    {
        staticSize = 9
        placeholder = true
    }

    override fun getButtons(player: Player): MutableMap<Int, Button>
    {
        return mutableMapOf(
            2 to SimpleActionButton(Material.ANVIL, mutableListOf(), "&aValid Reasons", 0),
            4 to SimpleActionButton(Material.BOOK, mutableListOf(), "&eYour Server", 0),
            6 to SimpleActionButton(Material.NETHER_STAR, mutableListOf(), "&bPast Hour", 0)
        )
    }

    override fun getTitle(player: Player): String
    {
        return "Select a Report Category"
    }
}
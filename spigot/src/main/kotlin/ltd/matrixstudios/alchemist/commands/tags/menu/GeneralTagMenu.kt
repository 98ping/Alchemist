package ltd.matrixstudios.alchemist.commands.tags.menu

import ltd.matrixstudios.alchemist.service.tags.TagService
import ltd.matrixstudios.alchemist.util.menu.Button
import ltd.matrixstudios.alchemist.util.menu.pagination.PaginatedMenu
import org.bukkit.entity.Player

class GeneralTagMenu(var player: Player) : PaginatedMenu(18, player)
{


    override fun getPagesButtons(player: Player): MutableMap<Int, Button>
    {
        val buttons = hashMapOf<Int, Button>()

        var index = 0

        for (tag in TagService.cache.values)
        {
            buttons[index++] = GeneralTagButton(tag)
        }

        return buttons
    }

    override fun getTitle(player: Player): String
    {
        return "Select a tag"
    }
}
package ltd.matrixstudios.alchemist.commands.tags.menu

import ltd.matrixstudios.alchemist.service.tags.TagService
import ltd.matrixstudios.alchemist.util.menu.Button
import ltd.matrixstudios.alchemist.util.menu.pagination.PaginatedMenu
import org.bukkit.entity.Player

class TagCustomizationMenu(var player: Player) : PaginatedMenu(18, player) {

    override fun getPagesButtons(player: Player): MutableMap<Int, Button> {
        val buttons = hashMapOf<Int, Button>()

        var index = 0

        for (tag in TagService.getValues().get()) {
            buttons[index++] = TagCustomizationButton(tag)
        }

        return buttons
    }

    override fun getTitle(player: Player): String {
       return "Edit a Tag"
    }
}
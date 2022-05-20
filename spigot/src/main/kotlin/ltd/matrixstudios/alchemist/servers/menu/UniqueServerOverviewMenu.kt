package ltd.matrixstudios.alchemist.servers.menu

import ltd.matrixstudios.alchemist.service.server.UniqueServerService
import ltd.matrixstudios.alchemist.util.menu.Button
import ltd.matrixstudios.alchemist.util.menu.pagination.PaginatedMenu
import org.bukkit.entity.Player

class UniqueServerOverviewMenu(var player: Player) : PaginatedMenu(18, player) {

    override fun getPagesButtons(player: Player): MutableMap<Int, Button> {
        val buttons = hashMapOf<Int, Button>()

        var index = 0

        for (server in UniqueServerService.getValues()) {
            buttons[index++] = UniqueServerButton(server)
        }

        return buttons
    }

    override fun getTitle(player: Player): String {
        return "All Servers"
    }
}
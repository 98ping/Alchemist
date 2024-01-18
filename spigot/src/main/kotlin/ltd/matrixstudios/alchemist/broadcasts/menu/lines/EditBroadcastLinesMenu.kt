package ltd.matrixstudios.alchemist.broadcasts.menu.lines

import ltd.matrixstudios.alchemist.broadcasts.BroadcastService
import ltd.matrixstudios.alchemist.broadcasts.model.BroadcastMessage
import ltd.matrixstudios.alchemist.util.menu.type.TextEditorMenu
import org.bukkit.entity.Player
import java.util.*

/**
 * Class created on 1/17/2024

 * @author 98ping
 * @project Alchemist
 * @website https://solo.to/redis
 */
class EditBroadcastLinesMenu(player: Player, private val broadcast: BroadcastMessage) :
    TextEditorMenu(LinkedList(broadcast.lines), player)
{
    override fun onSave(player: Player, lines: LinkedList<String>)
    {
        val cached = BroadcastService.cached()
            ?: return

        broadcast.lines = lines.toMutableList()
        cached.broadcasts[broadcast.id] = broadcast

        BroadcastService.cache(cached)
        EditBroadcastLinesMenu(player, broadcast).updateMenu()
    }
}
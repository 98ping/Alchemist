package ltd.matrixstudios.alchemist.commands.filter.menu.editor

import ltd.matrixstudios.alchemist.models.filter.Filter
import ltd.matrixstudios.alchemist.service.filter.FilterService
import ltd.matrixstudios.alchemist.util.Chat
import ltd.matrixstudios.alchemist.util.menu.Button
import ltd.matrixstudios.alchemist.util.menu.Menu
import ltd.matrixstudios.alchemist.util.menu.buttons.SimpleActionButton
import org.bukkit.Material
import org.bukkit.entity.Player

class FilterSubEditorMenu(val player: Player, val filter: Filter) : Menu(player) {

    init {
        placeholder = true
        staticSize = 27
    }

    override fun getButtons(player: Player): MutableMap<Int, Button> {
        val buttons = mutableMapOf<Int, Button>()

        buttons[10] = SimpleActionButton(
            Material.QUARTZ,
            mutableListOf(" ", Chat.format("&eChange the silent status of the filter"), Chat.format("&eCurrently: &f" + if (filter.silent) "&aSilent" else "&cPublic"), " "),
            "&eChange Silent Status", 0
        ).setBody { player, slot, clicktype ->
            val otherBool = !filter.silent
            filter.silent = otherBool
            FilterService.save(filter)
            player.sendMessage(Chat.format("&eUpdate the silent status of &6${filter.word} &eto " + if (otherBool) "&aSilent" else "&cPublic"))
            FilterSubEditorMenu(player, filter).openMenu()
        }

        return buttons
    }

    override fun getTitle(player: Player): String {
        return "Editing: ${filter.word}"
    }
}
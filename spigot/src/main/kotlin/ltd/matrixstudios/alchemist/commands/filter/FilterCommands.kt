package ltd.matrixstudios.alchemist.commands.filter

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandPermission
import co.aikar.commands.annotation.Name
import co.aikar.commands.annotation.Subcommand
import ltd.matrixstudios.alchemist.commands.filter.menu.FilterEditorMenu
import ltd.matrixstudios.alchemist.models.filter.Filter
import ltd.matrixstudios.alchemist.service.filter.FilterService
import ltd.matrixstudios.alchemist.util.Chat
import org.bukkit.entity.Player
import java.util.*

class FilterCommands : BaseCommand() {

    @CommandAlias("filter|filters")
    @CommandPermission("alchemist.filters.admin")
    fun create(player: Player) {
        FilterEditorMenu(player).updateMenu()
    }


}
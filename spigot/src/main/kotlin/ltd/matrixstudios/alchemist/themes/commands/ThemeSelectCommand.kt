package ltd.matrixstudios.alchemist.themes.commands

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandPermission
import ltd.matrixstudios.alchemist.themes.commands.menu.ThemeSelectMenu
import org.bukkit.entity.Player

class ThemeSelectCommand : BaseCommand() {

    @CommandAlias("themes|theme")
    @CommandPermission("alchemist.themes")
    fun theme(player: Player) {
        ThemeSelectMenu(player).openMenu()
    }
}
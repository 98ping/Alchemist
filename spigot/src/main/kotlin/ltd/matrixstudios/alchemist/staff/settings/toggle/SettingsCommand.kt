package ltd.matrixstudios.alchemist.staff.settings.toggle

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandPermission
import ltd.matrixstudios.alchemist.staff.settings.toggle.menu.SettingsMenu
import org.bukkit.entity.Player

class SettingsCommand : BaseCommand() {

    @CommandAlias("tsm|tsc|staffsettings")
    @CommandPermission("alchemist.staff")
    fun settings(player: Player)
    {
        SettingsMenu(player).openMenu()
    }
}
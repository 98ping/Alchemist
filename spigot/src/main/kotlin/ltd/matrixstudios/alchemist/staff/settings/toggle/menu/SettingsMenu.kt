package ltd.matrixstudios.alchemist.staff.settings.toggle.menu

import ltd.matrixstudios.alchemist.service.profiles.ProfileGameService
import ltd.matrixstudios.alchemist.staff.settings.toggle.types.ToggleModModeOnJoinSetting
import ltd.matrixstudios.alchemist.staff.settings.toggle.types.ToggleRequestsSetting
import ltd.matrixstudios.alchemist.staff.settings.toggle.types.ToggleStaffChatSetting
import ltd.matrixstudios.alchemist.util.menu.Button
import ltd.matrixstudios.alchemist.util.menu.Menu
import org.bukkit.entity.Player

class SettingsMenu(val player: Player) : Menu(player) {

    init {
        staticSize = 27
        placeholder = true
    }

    val profile = ProfileGameService.byId(player.uniqueId)!!

    override fun getButtons(player: Player): MutableMap<Int, Button> {
        val buttons = mutableMapOf<Int, Button>()

        buttons[10] = ToggleRequestsSetting(profile)
        buttons[11] = ToggleStaffChatSetting(profile)
        buttons[12] = ToggleModModeOnJoinSetting(profile)

        return buttons
    }

    override fun getTitle(player: Player): String {
        return "Edit your Settings!"
    }
}
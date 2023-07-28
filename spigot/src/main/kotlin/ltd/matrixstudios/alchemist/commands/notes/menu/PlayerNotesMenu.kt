package ltd.matrixstudios.alchemist.commands.notes.menu

import ltd.matrixstudios.alchemist.commands.notes.menu.button.PlayerNotesButton
import ltd.matrixstudios.alchemist.models.profile.GameProfile
import ltd.matrixstudios.alchemist.util.menu.Button
import ltd.matrixstudios.alchemist.util.menu.pagination.PaginatedMenu
import org.bukkit.entity.Player

class PlayerNotesMenu(private val p: Player, val gameProfile: GameProfile) : PaginatedMenu(18, player = p) {

    override fun getPagesButtons(player: Player): MutableMap<Int, Button> {
        val buttons = mutableMapOf<Int, Button>()

        gameProfile.notes.forEachIndexed { index, profileNote ->
            buttons[index] = PlayerNotesButton(profileNote, gameProfile)
        }

        return buttons
    }

    override fun getTitle(player: Player): String {
        return "Notes for ${gameProfile.username}"
    }
}
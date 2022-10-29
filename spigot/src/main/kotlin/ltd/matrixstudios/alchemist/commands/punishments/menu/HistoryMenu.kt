package ltd.matrixstudios.alchemist.commands.punishments.menu

import ltd.matrixstudios.alchemist.api.AlchemistAPI
import ltd.matrixstudios.alchemist.models.profile.GameProfile
import ltd.matrixstudios.alchemist.punishments.PunishmentType
import ltd.matrixstudios.alchemist.util.Chat
import ltd.matrixstudios.alchemist.util.menu.Button
import ltd.matrixstudios.alchemist.util.menu.Menu
import org.bukkit.entity.Player

class HistoryMenu(var gameprofile: GameProfile, var player: Player) : Menu(player) {

    init {
        staticSize = 9
    }
    override fun getButtons(player: Player): MutableMap<Int, Button> {
        val buttons = hashMapOf<Int, Button>()

        buttons[1] = HistoryPlaceholderButton(PunishmentType.WARN, gameprofile)
        buttons[3] = HistoryPlaceholderButton(PunishmentType.MUTE, gameprofile)
        buttons[5] = HistoryPlaceholderButton(PunishmentType.BAN, gameprofile)
        buttons[7] = HistoryPlaceholderButton(PunishmentType.BLACKLIST, gameprofile)

        return buttons
    }

    override fun getTitle(player: Player): String {
        return Chat.format(AlchemistAPI.getRankDisplay(gameprofile.uuid))
    }
}
package ltd.matrixstudios.alchemist.util.menu.sound

import org.bukkit.Sound
import org.bukkit.entity.Player




object MenuSound {
    private val REACHED_END = Sound.DIG_GRASS
    private val SUCCESS = Sound.NOTE_PIANO
    private val PAGENAV = Sound.CLICK

    fun playFail(player: Player) {
        player.playSound(player.location, REACHED_END, 20.0f, 0.1f)
    }

    fun playSuccess(player: Player) {
        player.playSound(player.location, SUCCESS, 20.0f, 15.0f)
    }

    fun playNeutral(player: Player) {
        player.playSound(player.location, PAGENAV, 20.0f, 1.0f)
    }
}
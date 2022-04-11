package ltd.matrixstudios.alchemist.util

import org.bukkit.ChatColor

object Chat {

    @JvmStatic
    fun format(string: String) : String {
        return ChatColor.translateAlternateColorCodes('&', string)
    }
}
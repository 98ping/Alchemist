package ltd.matrixstudios.alchemist.util

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.DyeColor


object Chat {

    @JvmStatic
    fun format(string: String) : String {
        return ChatColor.translateAlternateColorCodes('&', string)
    }

    fun sendConsoleMessage(line: String) {
        Bukkit.getServer().consoleSender.sendMessage(format(line))

    }

    fun getDyeColor(str: String): DyeColor {
        if (str.contains("&1") || str.contains("&9")) return DyeColor.BLUE
        if (str.contains("&2")) return DyeColor.GREEN
        if (str.contains("&3")) return DyeColor.CYAN
        if (str.contains("&4") || str.contains("&c")) return DyeColor.RED
        if (str.contains("&5")) return DyeColor.PURPLE
        if (str.contains("&6")) return DyeColor.ORANGE
        if (str.contains("&7")) return DyeColor.GRAY
        if (str.contains("&8")) return DyeColor.SILVER
        if (str.contains("&a")) return DyeColor.LIME
        if (str.contains("&b")) return DyeColor.LIGHT_BLUE
        if (str.contains("&d")) return DyeColor.PINK
        return if (str.contains("&e")) DyeColor.YELLOW else DyeColor.WHITE
    }

}
package ltd.matrixstudios.alchemist.util

import net.md_5.bungee.api.ChatColor
import org.bukkit.Bukkit
import org.bukkit.DyeColor
import java.util.regex.Matcher
import java.util.regex.Pattern


object Chat {

    @JvmStatic
    fun format(message: String): String {
        val HEX_PATTERN: Pattern = Pattern.compile("&#(\\w{5}[0-9a-f])")
        val matcher: Matcher = HEX_PATTERN.matcher(message)
        val buffer = StringBuffer()
        while (matcher.find()) {
            try {
                matcher.appendReplacement(
                    buffer,
                    ChatColor.of("#" + matcher.group(1)).toString()
                )
            } catch (e: NoSuchMethodError) {
                return message
            }
        }
        return ChatColor.translateAlternateColorCodes('&', matcher.appendTail(buffer).toString())
    }

    fun sendConsoleMessage(line: String) {
        Bukkit.getServer().consoleSender.sendMessage(format(line))
    }

    fun sendMultiConsoleMessage(`in`: Array<String>) {
        for (line in `in`) {
            Bukkit.getServer().consoleSender.sendMessage(format(line))
        }
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
        if (str.contains("&e")) return DyeColor.YELLOW

        return DyeColor.WHITE
    }

}
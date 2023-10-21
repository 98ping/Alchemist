package ltd.matrixstudios.alchemist.util

import ltd.matrixstudios.alchemist.util.skull.SkullConstants
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextColor
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Color
import org.bukkit.DyeColor
import java.util.regex.Matcher
import java.util.regex.Pattern


object Chat
{

    @JvmStatic
    fun format(message: String): String
    {
        val HEX_PATTERN: Pattern = Pattern.compile("&#(\\w{5}[0-9a-f])")
        val matcher: Matcher = HEX_PATTERN.matcher(message)
        val buffer = StringBuffer()
        while (matcher.find())
        {
            try
            {
                matcher.appendReplacement(
                    buffer,
                    net.md_5.bungee.api.ChatColor.of("#" + matcher.group(1)).toString()
                )
            } catch (e: NoSuchMethodError)
            {
                return message
            }
        }
        return ChatColor.translateAlternateColorCodes('&', matcher.appendTail(buffer).toString())
    }

    fun sendConsoleMessage(line: String)
    {
        Bukkit.getServer().consoleSender.sendMessage(format(line))
    }

    fun sendMultiConsoleMessage(`in`: Array<String>)
    {
        for (line in `in`)
        {
            Bukkit.getServer().consoleSender.sendMessage(format(line))
        }
    }

    fun enumToDisplay(string: String): String
    {
        val name = string
        return if (name.contains("_"))
        {
            val split = name.split("_")
            val p1 = split[0]
            val p2 = split[1]
            var n = ""

            n = (n + p1.lowercase().replaceFirstChar { p1[0].uppercase() })
            n = ("$n ")
            n = (n + p2.lowercase().replaceFirstChar { p2[0].uppercase() })

            n
        } else
        {
            name.lowercase().replaceFirstChar { name[0].uppercase() }
        }
    }

    fun findTextColorFromString(string: String): TextColor?
    {
        return TextColor.fromHexString(string.replace("&", "")) ?: (getNamedTextColorFromBukkitColor(string)
            ?: NamedTextColor.WHITE)
    }

    fun getNamedTextColorFromBukkitColor(color: String): NamedTextColor?
    {
        val names = NamedTextColor.NAMES

        return names.value(org.bukkit.ChatColor.getByChar(color.replace("&", "")).name.lowercase())
    }

    fun mapChatColorToSkullTexture(str: String): String
    {
        if (str.contains("&1") || str.contains("&9")) return SkullConstants.BLUE
        if (str.contains("&2")) return SkullConstants.DARK_GREEN
        if (str.contains("&3")) return SkullConstants.AQUA
        if (str.contains("&4") || str.contains("&c")) return SkullConstants.RED
        if (str.contains("&5")) return SkullConstants.PURPLE
        if (str.contains("&6")) return SkullConstants.ORANGE
        if (str.contains("&7")) return SkullConstants.LIGHT_GRAY
        if (str.contains("&8")) return SkullConstants.SILVER
        if (str.contains("&a")) return SkullConstants.LIME
        if (str.contains("&b")) return SkullConstants.LIGHT_BLUE
        if (str.contains("&d")) return SkullConstants.PINK
        if (str.contains("&e")) return SkullConstants.YELLOW

        return SkullConstants.WHITE
    }

    fun getDyeColor(str: String): DyeColor
    {
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

    fun getLeatherMetaColor(str: String): Color
    {
        if (str.contains("&1") || str.contains("&9")) return Color.BLUE
        if (str.contains("&2")) return Color.OLIVE
        if (str.contains("&3")) return Color.TEAL
        if (str.contains("&4") || str.contains("&c")) return Color.RED
        if (str.contains("&5")) return Color.PURPLE
        if (str.contains("&6")) return Color.ORANGE
        if (str.contains("&7")) return Color.GRAY
        if (str.contains("&8")) return Color.SILVER
        if (str.contains("&a")) return Color.LIME
        if (str.contains("&b")) return Color.AQUA
        if (str.contains("&d")) return Color.FUCHSIA
        if (str.contains("&e")) return Color.YELLOW

        return Color.WHITE
    }

}
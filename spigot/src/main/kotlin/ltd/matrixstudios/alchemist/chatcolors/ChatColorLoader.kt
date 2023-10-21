package ltd.matrixstudios.alchemist.chatcolors

import ltd.matrixstudios.alchemist.models.chatcolor.ChatColor

object ChatColorLoader
{

    val colors = hashMapOf<String, ChatColor>()

    fun loadAllChatColors()
    {
        for (color in org.bukkit.ChatColor.values())
        {
            if (color.isColor)
            {
                colors[color.name.lowercase()] = ChatColor(
                    color.name.lowercase(),
                    color.name,
                    color.toString().replace("§", "&"),
                    "alchemist.chatcolor.${color.name.lowercase()}"
                )
            }
        }
    }

    fun proper(color: ChatColor): String
    {
        val name = color.displayname
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
}
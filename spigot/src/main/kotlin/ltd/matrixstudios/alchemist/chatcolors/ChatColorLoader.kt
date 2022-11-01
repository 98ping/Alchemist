package ltd.matrixstudios.alchemist.chatcolors

import ltd.matrixstudios.alchemist.models.chatcolor.ChatColor
import ltd.matrixstudios.alchemist.models.profile.GameProfile

object ChatColorLoader {

    val colors = hashMapOf<String, ChatColor>()

    fun loadAllChatColors()
    {
        for (color in org.bukkit.ChatColor.values())
        {
            if (color.isColor)
            {
                colors[color.name.lowercase()] = ChatColor(color.name.lowercase(), color.name, color.toString().replace("§", "&"), "alchemist.chatcolor.${color.name.lowercase()}")
            }
        }
    }

    fun setColor(gameProfile: GameProfile, color: ChatColor)
    {
        gameProfile.activeColor = color
    }
}
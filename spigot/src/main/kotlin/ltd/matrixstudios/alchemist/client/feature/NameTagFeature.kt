package ltd.matrixstudios.alchemist.client.feature

import com.lunarclient.bukkitapi.LunarClientAPI
import com.lunarclient.bukkitapi.nethandler.client.LCPacketNametagsOverride
import ltd.matrixstudios.alchemist.profiles.getCurrentRank
import ltd.matrixstudios.alchemist.util.Chat
import org.bukkit.Bukkit
import org.bukkit.entity.Player

/**
 * Class created on 9/13/2023

 * @author 98ping
 * @project Alchemist
 * @website https://solo.to/redis
 */
object NameTagFeature
{
    fun sendNameTag(player: Player)
    {
        if (LunarClientAPI.getInstance().isRunningLunarClient(player))
        {
            LunarClientAPI.getInstance().sendPacket(player, LCPacketNametagsOverride(player.uniqueId, mutableListOf(
                Chat.format(player.getCurrentRank().color + player.name),
                Chat.format("&7[Mod Mode]")
            )))
        }
    }

    fun removeNameTag(player: Player)
    {
        if (LunarClientAPI.getInstance().isRunningLunarClient(player))
        {
            Bukkit.getServer().onlinePlayers.forEach { staff ->
                LunarClientAPI.getInstance().resetNametag(player, staff)
            }
        }
    }
}
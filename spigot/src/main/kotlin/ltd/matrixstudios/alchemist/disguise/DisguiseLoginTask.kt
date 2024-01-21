package ltd.matrixstudios.alchemist.disguise

import ltd.matrixstudios.alchemist.AlchemistSpigotPlugin
import ltd.matrixstudios.alchemist.profiles.connection.postlog.BukkitPostLoginTask
import ltd.matrixstudios.alchemist.profiles.getProfile
import ltd.matrixstudios.alchemist.util.Chat
import net.pinger.disguise.DisguiseAPI
import net.pinger.disguise.skin.Skin
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.entity.Player

object DisguiseLoginTask :  BukkitPostLoginTask
{
    override fun run(player: Player)
    {
        Bukkit.getScheduler().runTaskLater(AlchemistSpigotPlugin.instance, Runnable {
            val profile = player.getProfile()
                ?: return@Runnable
            val attribute = profile.skinDisguiseAttribute

            if (attribute != null)
            {
                player.displayName = attribute.customName
                player.playerListName = player.displayName
                player.customName = player.displayName

                DisguiseAPI.getDefaultProvider().updatePlayer(player, Skin(attribute.texture, attribute.signature), attribute.customName)
                val location = player.location

                // refresh player skin
                player.teleport(Location(Bukkit.getWorld("SkinUpdateWorld"), 0.0, 100.0, 0.0))
                player.teleport(location)

                player.sendMessage(Chat.format("&aSuccess! You now look like &f${attribute.customName} &awith the skin &f${attribute.skinName}&a."))
                player.sendMessage(Chat.format("&8This disguise has been transferred from an alternate server..."))
            }
        }, 20L)
    }
}
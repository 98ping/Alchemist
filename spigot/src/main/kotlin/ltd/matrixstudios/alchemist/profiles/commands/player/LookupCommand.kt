package ltd.matrixstudios.alchemist.profiles.commands.player

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandCompletion
import co.aikar.commands.annotation.CommandPermission
import co.aikar.commands.annotation.Name
import ltd.matrixstudios.alchemist.AlchemistSpigotPlugin
import ltd.matrixstudios.alchemist.api.AlchemistAPI
import ltd.matrixstudios.alchemist.models.profile.GameProfile
import ltd.matrixstudios.alchemist.util.Chat
import ltd.matrixstudios.alchemist.util.TimeUtil
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitRunnable
import java.util.logging.Level

class LookupCommand : BaseCommand() {

    @CommandAlias("lookup|find|whereis|seen")
    @CommandPermission("alchemist.staff")
    @CommandCompletion("@gameprofile")
    fun lookup(player: Player, @Name("target") gameProfile: GameProfile)
    {
        val start = System.currentTimeMillis()
        player.sendMessage(Chat.format("&aLooking up player..."))
        object : BukkitRunnable() {
            override fun run() {
                val server = "none"

                val serverFromProfile = gameProfile.metadata.get("server")

                if (serverFromProfile != null && !serverFromProfile.asString.equals("None", ignoreCase = true))
                {
                    player.sendMessage(Chat.format(AlchemistAPI.getRankDisplay(gameProfile.uuid) + " &ewas found on &f${serverFromProfile.asString}"))
                } else
                {
                    player.sendMessage(Chat.format(AlchemistAPI.getRankDisplay(gameProfile.uuid) + " &ewas last online &f${TimeUtil.formatDuration(System.currentTimeMillis().minus(gameProfile.lastSeenAt))} &eago"))
                }
            }
        }.runTaskLater(AlchemistSpigotPlugin.instance, 0L)

        Bukkit.getLogger().log(Level.INFO, "Player lookup took " + System.currentTimeMillis().minus(start) + "ms")
    }

}
package ltd.matrixstudios.alchemist.commands.player

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandCompletion
import co.aikar.commands.annotation.CommandPermission
import co.aikar.commands.annotation.Name
import ltd.matrixstudios.alchemist.AlchemistSpigotPlugin
import ltd.matrixstudios.alchemist.api.AlchemistAPI
import ltd.matrixstudios.alchemist.models.profile.GameProfile
import ltd.matrixstudios.alchemist.util.Chat
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitRunnable

class LookupCommand : BaseCommand() {

    @CommandAlias("lookup|find|whereis")
    @CommandPermission("alchemist.staff")
    @CommandCompletion("@gameprofile")
    fun lookup(player: Player, @Name("target") gameProfile: GameProfile)
    {
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
                    player.sendMessage(Chat.format("&cPlayer was not found on the proxy"))
                }
            }
        }.runTaskLater(AlchemistSpigotPlugin.instance, 25L)
    }

}
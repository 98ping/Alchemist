package ltd.matrixstudios.alchemist.profiles.commands.auth.listener

import ltd.matrixstudios.alchemist.profiles.getProfile
import ltd.matrixstudios.alchemist.util.Chat
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerCommandPreprocessEvent
import org.bukkit.event.player.PlayerMoveEvent

class AuthListener : Listener
{

    @EventHandler
    fun onMove(event: PlayerMoveEvent)
    {
        val player = event.player
        val profile = player.getProfile() ?: return

        if (profile.hasMetadata("needsAuthetication"))
        {
            val to = event.to
            val from = event.from

            if (to.blockX != from.blockX || to.blockY != from.blockY || to.blockZ != from.blockZ)
            {
                player.sendMessage(" ")
                player.sendMessage(Chat.format("&cYou cannot move until you have authenticated!"))
                player.sendMessage(Chat.format("&cPlease run /auth verify <code>"))
                player.sendMessage(" ")
                event.isCancelled = true
            }
        }
    }

    @EventHandler
    fun onCommandAttempt(event: PlayerCommandPreprocessEvent)
    {
        val command = event.message
        val player = event.player
        val profile = player.getProfile() ?: return

        if (profile.hasMetadata("needsAuthetication"))
        {
            if (!command.startsWith("/auth") && !command.startsWith("/2fa"))
            {
                player.sendMessage(" ")
                player.sendMessage(Chat.format("&cYou cannot move until you have authenticated!"))
                player.sendMessage(Chat.format("&cPlease run /auth verify <code>"))
                player.sendMessage(" ")
                event.isCancelled = true
            }
        }

    }
}
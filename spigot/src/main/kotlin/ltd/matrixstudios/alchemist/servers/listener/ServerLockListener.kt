package ltd.matrixstudios.alchemist.servers.listener

import ltd.matrixstudios.alchemist.AlchemistSpigotPlugin
import ltd.matrixstudios.alchemist.api.AlchemistAPI
import ltd.matrixstudios.alchemist.service.ranks.RankService
import ltd.matrixstudios.alchemist.util.Chat
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerPreLoginEvent

class ServerLockListener : Listener {

    @EventHandler
    fun asyncJoin(event: AsyncPlayerPreLoginEvent) {
        val server = AlchemistSpigotPlugin.instance.globalServer

        if (server.lockedWithRank) {
            val rank = RankService.byId(server.lockRank)

            if (rank != null) {
                val it = AlchemistAPI.syncFindProfile(event.uniqueId)

                if (it != null) {
                    val theirRank = it.getCurrentRank()

                    if (theirRank == null || theirRank.weight < rank.weight) {
                        event.loginResult = AsyncPlayerPreLoginEvent.Result.KICK_OTHER
                        event.kickMessage =
                            Chat.format("&cThis server is currently locked.\nTo bypass, you must have ${rank.color}${rank.displayName}&c!")
                    }
                }
            }

        }
    }
}
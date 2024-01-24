package ltd.matrixstudios.alchemist.servers.commands

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import ltd.matrixstudios.alchemist.AlchemistSpigotPlugin
import ltd.matrixstudios.alchemist.models.server.UniqueServer
import ltd.matrixstudios.alchemist.service.server.UniqueServerService
import ltd.matrixstudios.alchemist.util.Chat
import ltd.matrixstudios.alchemist.util.NetworkUtil
import org.bukkit.entity.Player

object HubCommand : BaseCommand()
{

    @CommandAlias("hub|lobby")
    fun onHub(player: Player)
    {
        val selectedServer: UniqueServer?
        val available = AlchemistSpigotPlugin.instance.config.getStringList("hubCommand.priorities")
            .mapNotNull { UniqueServerService.byId(it.lowercase()) }
            .filter { it.online }

        selectedServer = if (AlchemistSpigotPlugin.instance.config.getBoolean("hubCommand.loadBalance"))
        {
            available
                .maxByOrNull { it.players.size }
        } else
        {
            available.random()
        }

        if (selectedServer == null)
        {
            player.sendMessage(Chat.format("&cUnable to connect you to a hub at this time!"))
            return
        }

        NetworkUtil.send(player, selectedServer.bungeeName)
        player.sendMessage(
            Chat.format(
                AlchemistSpigotPlugin.instance.config.getString("hubCommand.connectedMessage")
                    .replace("{hub}", selectedServer.displayName)
            )
        )
    }
}
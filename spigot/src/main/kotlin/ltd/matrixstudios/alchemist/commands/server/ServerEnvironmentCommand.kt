package ltd.matrixstudios.alchemist.commands.server

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandPermission
import co.aikar.commands.annotation.Subcommand
import ltd.matrixstudios.alchemist.AlchemistSpigotPlugin
import ltd.matrixstudios.alchemist.servers.menu.UniqueServerOverviewMenu
import ltd.matrixstudios.alchemist.util.Chat
import org.bukkit.entity.Player

@CommandAlias("senv|env|environment")
class ServerEnvironmentCommand : BaseCommand() {

    @Subcommand("menu")
    @CommandPermission("alchemist.servers.admin")
    fun servermenu(player: Player) {
        UniqueServerOverviewMenu(player).updateMenu()
    }

    @Subcommand("dump")
    fun dump(player: Player) {
        player.sendMessage(" ")
        player.sendMessage(Chat.format("&6&lEnvironment"))
        player.sendMessage(" ")
        val server = AlchemistSpigotPlugin.instance.globalServer
        player.sendMessage(Chat.format("&eName: &f" + server.displayName))
        player.sendMessage(Chat.format("&eLocked: &f" + server.lockedWithRank))
        player.sendMessage(" ")
    }

}
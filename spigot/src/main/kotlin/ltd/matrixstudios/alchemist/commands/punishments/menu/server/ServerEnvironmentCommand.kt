package ltd.matrixstudios.alchemist.commands.punishments.menu.server

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandPermission
import co.aikar.commands.annotation.Subcommand
import ltd.matrixstudios.alchemist.servers.menu.UniqueServerOverviewMenu
import org.bukkit.entity.Player

@CommandAlias("senv|env|environment")
class ServerEnvironmentCommand : BaseCommand() {

    @Subcommand("menu")
    @CommandPermission("alchemist.servers.admin")
    fun servermenu(player: Player) {
        UniqueServerOverviewMenu(player).updateMenu()
    }
}
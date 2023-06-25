package ltd.matrixstudios.alchemist.commands.server

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandPermission
import co.aikar.commands.annotation.HelpCommand
import co.aikar.commands.annotation.Name
import co.aikar.commands.annotation.Subcommand
import ltd.matrixstudios.alchemist.Alchemist
import ltd.matrixstudios.alchemist.AlchemistSpigotPlugin
import ltd.matrixstudios.alchemist.servers.menu.UniqueServerOverviewMenu
import ltd.matrixstudios.alchemist.service.server.UniqueServerService
import ltd.matrixstudios.alchemist.util.Chat
import ltd.matrixstudios.alchemist.util.TimeUtil
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

@CommandAlias("senv|env|environment")
class ServerEnvironmentCommand : BaseCommand() {

    @Subcommand("menu")
    @CommandPermission("alchemist.servers.admin")
    fun servermenu(player: Player) {
        UniqueServerOverviewMenu(player).updateMenu()
    }

    @Subcommand("help")
    @HelpCommand
    fun help(player: Player) {
        player.sendMessage(Chat.format("&7&m-------------------"))
        player.sendMessage(Chat.format("&6&lServer Commands"))
        player.sendMessage(" ")
        player.sendMessage(Chat.format("&e/senv dump"))
        if (player.hasPermission("alchemist.servers.admin")) {
            player.sendMessage(Chat.format("&e/senv checkrelease"))
            player.sendMessage(Chat.format("&e/senv releasetimer &f<duration>"))
        }
        player.sendMessage(Chat.format("&e/senv menu"))
        player.sendMessage(Chat.format("&7&m-------------------"))
    }

    @Subcommand("dump")
    fun dump(player: Player) {
        player.sendMessage(" ")
        player.sendMessage(Chat.format("&eServer Monitor"))
        player.sendMessage(" ")
        val server = Alchemist.globalServer
        player.sendMessage(Chat.format("&eName: &f" + server.displayName))
        player.sendMessage(Chat.format("&eLocked: &f" + server.lockedWithRank))
        player.sendMessage(" ")
    }

    @Subcommand("checkrelease")
    @CommandPermission("alchemist.servers.admin")
    fun check(player: CommandSender) {
        val server = Alchemist.globalServer

        if (server.setToRelease == -1L) {
            player.sendMessage(Chat.format("&cThere is no set time that this server is going to release!"))
            return
        }

        player.sendMessage(Chat.format("&8[&eServer Monitor&8] &fServer is set to release in &e" + TimeUtil.formatDuration(server.setToRelease - System.currentTimeMillis())))
    }

    @Subcommand("releasetimer")
    @CommandPermission("alchemist.servers.admin")
    fun release(sender: CommandSender, @Name("duration") time: String) {
        val actualTime = TimeUtil.parseTime(time).toLong() * 1000L
        val server = Alchemist.globalServer

        server.setToRelease = (System.currentTimeMillis().plus(actualTime))
        UniqueServerService.save(server)

        sender.sendMessage(Chat.format("&8[&eServer Monitor&8] &fSet a release timer for &e" + TimeUtil.formatDuration(actualTime)))
    }

}
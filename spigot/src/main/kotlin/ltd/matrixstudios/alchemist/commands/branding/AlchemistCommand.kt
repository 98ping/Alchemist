package ltd.matrixstudios.alchemist.commands.branding

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandPermission
import co.aikar.commands.annotation.Default
import co.aikar.commands.annotation.Description
import co.aikar.commands.annotation.Subcommand
import ltd.matrixstudios.alchemist.Alchemist
import ltd.matrixstudios.alchemist.AlchemistSpigotPlugin
import ltd.matrixstudios.alchemist.util.Chat
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

@CommandAlias("alchemist")
class AlchemistCommand : BaseCommand() {

    @Default
    fun alchemist(player: Player) {
        player.sendMessage(Chat.format("&7&m----------------------------------------"))
        player.sendMessage(Chat.format("&6&lAlchemist Rank Core"))
        player.sendMessage(Chat.format(" "))
        player.sendMessage(Chat.format("&eMade By&7: &f98ping"))
        player.sendMessage(Chat.format("&eGitHub&7: &fhttps://github.com/98ping/Alchemist"))
        if (player.name.equals("98ping", ignoreCase = true)) {
            player.sendMessage(Chat.format("&ePlugin Version: &f" + AlchemistSpigotPlugin.instance.description.version))
        }
        player.sendMessage(Chat.format("&7&m----------------------------------------"))
    }

    @Subcommand("reload")
    @CommandPermission("alchemist.reload")
    fun reload(sender: CommandSender) {
        AlchemistSpigotPlugin.instance.reloadConfig()
        sender.sendMessage(Chat.format("&eAlchemist has been reloaded. Files afffected: &aconfig.yml&e."))
    }

}
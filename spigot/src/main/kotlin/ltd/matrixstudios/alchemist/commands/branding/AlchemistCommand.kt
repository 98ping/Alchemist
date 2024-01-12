package ltd.matrixstudios.alchemist.commands.branding

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandPermission
import co.aikar.commands.annotation.Default
import co.aikar.commands.annotation.Subcommand
import ltd.matrixstudios.alchemist.AlchemistSpigotPlugin
import ltd.matrixstudios.alchemist.repository.AlchemistRepositoryService
import ltd.matrixstudios.alchemist.util.Chat
import org.apache.maven.artifact.versioning.DefaultArtifactVersion
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

@CommandAlias("alchemist")
class AlchemistCommand : BaseCommand()
{

    @Default
    fun alchemist(player: Player)
    {
        player.sendMessage(Chat.format("&7&m----------------------------------------"))
        player.sendMessage(Chat.format("&6&lAlchemist Rank Core"))
        player.sendMessage(Chat.format(" "))
        player.sendMessage(Chat.format("&eMade By&7: &f98ping"))
        player.sendMessage(Chat.format("&eGitHub&7: &fhttps://github.com/98ping/Alchemist"))
        if (player.name.equals("98ping", ignoreCase = true))
        {
            player.sendMessage(Chat.format("&ePlugin Version: &f" + AlchemistSpigotPlugin.instance.description.version))
        }

        if (player.hasPermission("alchemist.owner"))
        {
            player.sendMessage(" ")
            player.sendMessage(Chat.format("&6&lCommands"))
            player.sendMessage(Chat.format("&e/alchemist reload &7- Reloads the configuration."))
            player.sendMessage(Chat.format("&e/alchemist refreshplaceholders &7- Refreshes the PlaceholderAPI placeholders."))
            player.sendMessage(Chat.format("&e/alchemist checkversion &7- Checks for the newest plugin version."))
        }
        player.sendMessage(Chat.format("&7&m----------------------------------------"))
    }

    @Subcommand("reload")
    @CommandPermission("alchemist.owner")
    fun reload(sender: CommandSender)
    {
        AlchemistSpigotPlugin.instance.reloadConfig()
        sender.sendMessage(Chat.format("&eAlchemist has been reloaded. Files affected: &aconfig.yml&e."))
    }

    @Subcommand("checkversion")
    @CommandPermission("alchemist.version")
    fun checkversion(sender: CommandSender)
    {
        val version = AlchemistSpigotPlugin.instance.description.version

        val newVersion = AlchemistRepositoryService.checkLatestJarFile(
            DefaultArtifactVersion(version)
        )

        if (newVersion.first == AlchemistRepositoryService.ResponseStatus.CouldNotLoad)
        {
            sender.sendMessage(Chat.format("&cCould not load latest jar file because the repository did not respond."))
            return
        }

        if (newVersion.first == AlchemistRepositoryService.ResponseStatus.Latest)
        {
            sender.sendMessage(Chat.format("&eYou are currently running the latest version of &6Alchemist &7(${version})"))
            return
        } else
        {
            sender.sendMessage(Chat.format("&eA new version of &6Alchemist &eis now available! &7(${version} -> ${newVersion.second!!.name})"))
        }
    }

    @Subcommand("refreshplaceholders")
    @CommandPermission("alchemist.owner")
    fun reloadPlaceholders(sender: CommandSender)
    {
        AlchemistSpigotPlugin.instance.registerExpansion()
        sender.sendMessage(Chat.format("&eAlchemist has reloaded every active &aPlaceholder &efrom &bPlaceholderAPI&e."))
    }

}
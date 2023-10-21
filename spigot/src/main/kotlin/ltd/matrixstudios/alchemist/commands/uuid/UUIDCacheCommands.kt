package ltd.matrixstudios.alchemist.commands.uuid

import co.aikar.commands.BaseCommand
import co.aikar.commands.CommandHelp
import co.aikar.commands.annotation.*
import ltd.matrixstudios.alchemist.cache.types.UUIDCache
import ltd.matrixstudios.alchemist.service.profiles.ProfileGameService
import ltd.matrixstudios.alchemist.util.Chat
import org.bukkit.command.CommandSender
import java.util.*

@CommandAlias("uuidcache|uuid")
@CommandPermission("alchemist.cache.uuid")
object UUIDCacheCommands : BaseCommand()
{

    @HelpCommand
    @CommandPermission("rank.admin")
    fun help(help: CommandHelp)
    {
        help.showHelp()
    }

    @Subcommand("checkId")
    fun checkId(sender: CommandSender, @Name("uuid") id: String)
    {
        val uuid: UUID

        try
        {
            uuid = UUID.fromString(id)
        } catch (e: IllegalArgumentException)
        {
            sender.sendMessage(Chat.format("&cThe uuid &e$id &cis not valid"))
            return
        }

        UUIDCache.findById(uuid).thenAccept {
            if (it == null)
            {
                sender.sendMessage(Chat.format("&cThe uuid &e$id &cwas never found in the cache!"))
                return@thenAccept
            }

            sender.sendMessage(Chat.format("&7&m--------------------------"))
            sender.sendMessage(Chat.format("&6UUID Cache &7❘ &fInformation"))
            sender.sendMessage(Chat.format("&7&m--------------------------"))
            sender.sendMessage(Chat.format("&6UUID: &f$id"))
            sender.sendMessage(Chat.format("&6Username: &f$it"))
            sender.sendMessage(Chat.format("&6Profile Exists: &f" + if (ProfileGameService.byId(uuid) != null) "&aYes" else "&cNo"))
            sender.sendMessage(Chat.format("&7&m--------------------------"))
        }
    }

    @Subcommand("checkname")
    fun checkName(sender: CommandSender, @Name("name") name: String)
    {
        UUIDCache.findByUsername(name).thenAccept {
            if (it == null)
            {
                sender.sendMessage(Chat.format("&cThe name &e$name &cwas never found in the cache!"))
                return@thenAccept
            }

            sender.sendMessage(Chat.format("&7&m--------------------------"))
            sender.sendMessage(Chat.format("&6Name Cache &7❘ &fInformation"))
            sender.sendMessage(Chat.format("&7&m--------------------------"))
            sender.sendMessage(Chat.format("&6UUID: &f$it"))
            sender.sendMessage(Chat.format("&6Username: &f$name"))
            sender.sendMessage(Chat.format("&6Profile Exists: &f" + if (ProfileGameService.byId(it) != null) "&aYes" else "&cNo"))
            sender.sendMessage(Chat.format("&7&m--------------------------"))
        }
    }
}
package ltd.matrixstudios.alchemist.profiles.commands.sibling

import co.aikar.commands.BaseCommand
import co.aikar.commands.CommandHelp
import co.aikar.commands.annotation.*
import ltd.matrixstudios.alchemist.models.profile.GameProfile
import ltd.matrixstudios.alchemist.profiles.commands.sibling.menu.SiblingCheckMenu
import ltd.matrixstudios.alchemist.service.profiles.ProfileGameService
import ltd.matrixstudios.alchemist.util.Chat
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

/**
 * Class created on 7/4/2023

 * @author 98ping
 * @project Alchemist
 * @website https://solo.to/redis
 */
@CommandAlias("sibling")
@CommandPermission("alchemist.siblings")
class SiblingCommands : BaseCommand()
{

    @HelpCommand
    fun help(help: CommandHelp)
    {
        help.showHelp()
    }

    @Subcommand("add")
    fun add(sender: CommandSender, @Name("target") target: GameProfile, @Name("sibling") sibling: GameProfile)
    {
        target.getAllSiblings().add(sibling.uuid)
        sibling.getAllSiblings().add(target.uuid)
        ProfileGameService.save(target)
        ProfileGameService.save(sibling)
        sender.sendMessage(Chat.format("&aAdded a new sibling to " + target.getRankDisplay()))
        sender.sendMessage(Chat.format("&e&lCaution! &cIt is recommended to add siblings only if their"))
        sender.sendMessage(Chat.format("&cIP Addresses match. You can check by running /sibling check <target>"))
    }

    @Subcommand("check")
    fun check(player: Player, @Name("target") target: GameProfile)
    {
        SiblingCheckMenu(target, player).updateMenu()
    }
}
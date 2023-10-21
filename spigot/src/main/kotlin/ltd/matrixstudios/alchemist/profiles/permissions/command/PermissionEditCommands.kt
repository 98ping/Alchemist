package ltd.matrixstudios.alchemist.profiles.permissions.command

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandCompletion
import co.aikar.commands.annotation.CommandPermission
import co.aikar.commands.annotation.Name
import ltd.matrixstudios.alchemist.models.profile.GameProfile
import ltd.matrixstudios.alchemist.profiles.permissions.menu.PermissionEditMenu
import ltd.matrixstudios.alchemist.service.profiles.ProfileGameService
import ltd.matrixstudios.alchemist.util.Chat
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

/**
 * Class created on 7/21/2023

 * @author 98ping
 * @project Alchemist
 * @website https://solo.to/redis
 */
class PermissionEditCommands : BaseCommand()
{

    @CommandAlias("permissions|perms|addperm|delperm")
    @CommandPermission("alchemist.permissions.admin")
    @CommandCompletion("@gameprofile")
    fun permissionEdit(player: Player, @Name("target") target: GameProfile)
    {
        PermissionEditMenu(player, target).updateMenu()
    }

    @CommandAlias("manualsetperm")
    @CommandPermission("alchemist.permissions.admin")
    @CommandCompletion("@gameprofile")
    fun manualPermEdit(sender: CommandSender, @Name("target") target: GameProfile, @Name("permission") perm: String)
    {
        if (target.permissions.contains(perm))
        {
            target.permissions.remove(perm)
            ProfileGameService.save(target)
            sender.sendMessage(Chat.format("&cYou removed the permission &f$perm"))
        } else
        {
            target.permissions.add(perm)
            ProfileGameService.save(target)
            sender.sendMessage(Chat.format("&aYou added the permission &f$perm"))
        }
    }
}
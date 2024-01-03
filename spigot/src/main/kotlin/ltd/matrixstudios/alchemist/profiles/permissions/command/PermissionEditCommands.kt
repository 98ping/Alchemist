package ltd.matrixstudios.alchemist.profiles.permissions.command

import co.aikar.commands.BaseCommand
import co.aikar.commands.ConditionFailedException
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandCompletion
import co.aikar.commands.annotation.CommandPermission
import co.aikar.commands.annotation.Name
import ltd.matrixstudios.alchemist.models.profile.GameProfile
import ltd.matrixstudios.alchemist.models.profile.permissions.ApplicablePermission
import ltd.matrixstudios.alchemist.service.profiles.ProfileGameService
import ltd.matrixstudios.alchemist.util.Chat
import ltd.matrixstudios.alchemist.util.PaginatedOutput
import ltd.matrixstudios.alchemist.util.TimeUtil
import org.bukkit.command.CommandSender

class PermissionEditCommands : BaseCommand()
{

    @CommandAlias("addpermission|addperm")
    @CommandPermission("alchemist.command.addpermission")
    @CommandCompletion("@gameprofile")
    fun manualPermEdit(sender: CommandSender, @Name("target") target: GameProfile, @Name("permission") perm: String, @Name("scope") scope: String, @Name("duration") duration: String)
    {
        if (target.getExtraPermissions(false).any { it.node.equals(perm, ignoreCase = true) })
        {
            throw ConditionFailedException("This player already has this permission!")
        }

        if (target.additionalPermissions == null)
        {
            target.additionalPermissions = mutableListOf()
        }

        target.additionalPermissions!!.add(
            ApplicablePermission(
            scope.equals("global", ignoreCase = true),
            if (!scope.equals("global", ignoreCase = true)) scope.split(",").toMutableList() else mutableListOf(),
            if (!duration.equals("perm", ignoreCase = true) && !duration.equals("permanent", ignoreCase = true)) TimeUtil.parseTime(duration) * 1000L else Long.MAX_VALUE,
            perm.lowercase(),
        ))

        ProfileGameService.save(target)
        sender.sendMessage(Chat.format("&aYou have added the permission &f$perm &ato the player &r${target.getRankDisplay()} &aon scopes &e$scope &aand for duration &e$duration&a."))
    }

    @CommandAlias("listpermissions|listperms")
    @CommandPermission("alchemist.command.listpermissions")
    @CommandCompletion("@gameprofile")
    fun onListPermission(sender: CommandSender, @Name("target") target: GameProfile) {
        val output = object : PaginatedOutput<ApplicablePermission>() {
            override fun getHeader(page: Int, maxPages: Int): List<String>
            {
                return listOf(Chat.format("&eViewing permissions of &r${target.getRankDisplay()} &7($page/$maxPages)"))
            }

            override fun format(result: ApplicablePermission, resultIndex: Int): String
            {
                return Chat.format("&7* &6${result.node}&7, &eDuration&7: &f${TimeUtil.formatDuration(result.duration)}&7, &eScopes&7: &7[${result.scopes.joinToString(", ")}]")
            }
        }

        output.display(sender, target.getExtraPermissions(false), 1)
    }

    @CommandAlias("deletepermission|delperm")
    @CommandPermission("alchemist.command.deletepermission")
    @CommandCompletion("@gameprofile")
    fun onDeletePermission(sender: CommandSender, @Name("target") target: GameProfile, @Name("node") node: String) {
        if (target.getExtraPermissions(false).any { it.node.equals(node, ignoreCase = true) })
        {
            throw ConditionFailedException("This target does not contain this permission!")
        }

        if (target.additionalPermissions == null)
        {
            target.additionalPermissions = mutableListOf()
        }

        val perms = target.additionalPermissions.also { permissions -> permissions!!.removeIf { it.node.equals(node, ignoreCase = true) } }

        target.additionalPermissions = perms
        ProfileGameService.save(target)
        sender.sendMessage(Chat.format("&cYou have removed the permission node &f$node &cfrom the player &r${target.getRankDisplay()}&c."))
    }
}
package ltd.matrixstudios.alchemist.commands.permission

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandCompletion
import co.aikar.commands.annotation.CommandPermission
import co.aikar.commands.annotation.Name
import ltd.matrixstudios.alchemist.commands.permission.menu.PermissionEditMenu
import ltd.matrixstudios.alchemist.models.profile.GameProfile
import org.bukkit.entity.Player

class PermissionEditCommand : BaseCommand() {

    @CommandAlias("permissions|perms|addperm|delperm")
    @CommandPermission("alchemist.permissions.admin")
    @CommandCompletion("@gameprofile")
    fun permissionEdit(player: Player, @Name("target")target: GameProfile)
    {
        PermissionEditMenu(player, target).updateMenu()
    }
}
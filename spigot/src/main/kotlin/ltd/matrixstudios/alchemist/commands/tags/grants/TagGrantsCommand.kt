package ltd.matrixstudios.alchemist.commands.tags.grants

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandCompletion
import co.aikar.commands.annotation.CommandPermission
import co.aikar.commands.annotation.Name
import ltd.matrixstudios.alchemist.commands.tags.grants.menu.grants.TagGrantsMenu
import ltd.matrixstudios.alchemist.models.profile.GameProfile
import org.bukkit.entity.Player

class TagGrantsCommand : BaseCommand()
{

    @CommandAlias("taggrants|prefixgrants")
    @CommandPermission("alchemist.tags.admin")
    @CommandCompletion("@gameprofile")
    fun grants(player: Player, @Name("target") gameProfile: GameProfile)
    {
        TagGrantsMenu(player, gameProfile).updateMenu()
    }
}
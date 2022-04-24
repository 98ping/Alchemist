package ltd.matrixstudios.alchemist.commands.tags.grants

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandPermission
import co.aikar.commands.annotation.Name
import ltd.matrixstudios.alchemist.commands.tags.grants.menu.grant.TagGrantMenu
import ltd.matrixstudios.alchemist.models.profile.GameProfile
import org.bukkit.entity.Player

class TagGrantCommand : BaseCommand() {

    @CommandAlias("taggrant|prefixgrant")
    @CommandPermission("alchemist.tags.admin")
    fun tagGrant(player: Player, @Name("target")gameProfile: GameProfile) {
        TagGrantMenu(player, gameProfile).updateMenu()
    }
}
package ltd.matrixstudios.alchemist.commands.grants

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandPermission
import co.aikar.commands.annotation.Name
import ltd.matrixstudios.alchemist.commands.grants.menu.grants.GrantsMenu
import ltd.matrixstudios.alchemist.models.profile.GameProfile
import org.bukkit.entity.Player

class GrantsCommand : BaseCommand() {

    @CommandAlias("grants")
    @CommandPermission("alchemist.grants.admin")
    fun grants(player: Player, @Name("target")gameProfile: GameProfile) {
        GrantsMenu(player, gameProfile).updateMenu()
    }
}
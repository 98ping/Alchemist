package ltd.matrixstudios.alchemist.grants.apply

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandCompletion
import co.aikar.commands.annotation.CommandPermission
import co.aikar.commands.annotation.Name
import ltd.matrixstudios.alchemist.grants.menu.grant.GrantMenu
import ltd.matrixstudios.alchemist.models.profile.GameProfile
import ltd.matrixstudios.alchemist.service.expirable.RankGrantService
import org.bukkit.entity.Player

class GrantCommand : BaseCommand() {

    @CommandAlias("grant")
    @CommandPermission("alchemist.grants.admin")
    @CommandCompletion("@gameprofile")
    fun grant(player: Player, @Name("target") gameProfile: GameProfile) {
        GrantMenu(player, gameProfile).updateMenu()
    }
}
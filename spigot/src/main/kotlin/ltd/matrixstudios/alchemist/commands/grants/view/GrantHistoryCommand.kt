package ltd.matrixstudios.alchemist.commands.grants.view

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandCompletion
import co.aikar.commands.annotation.CommandPermission
import co.aikar.commands.annotation.Name
import ltd.matrixstudios.alchemist.commands.grants.menu.history.GrantHistoryMenu
import ltd.matrixstudios.alchemist.models.profile.GameProfile
import ltd.matrixstudios.alchemist.service.expirable.RankGrantService
import ltd.matrixstudios.alchemist.util.Chat
import org.bukkit.entity.Player

class GrantHistoryCommand : BaseCommand() {

    @CommandAlias("granthistory")
    @CommandPermission("alchemist.grants.admin")
    @CommandCompletion("@gameprofile")
    fun grantHistory(player: Player, @Name("target")target: GameProfile)
    {
        val grants = RankGrantService.findExecutedBy(target.uuid)

        if (grants.isEmpty())
        {
            player.sendMessage(Chat.format("&cPlayer has never granted a rank!"))
            return
        }

        GrantHistoryMenu(player, grants, target).updateMenu()
    }
}
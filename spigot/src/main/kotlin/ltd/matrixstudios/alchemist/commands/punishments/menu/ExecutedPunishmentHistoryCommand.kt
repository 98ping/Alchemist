package ltd.matrixstudios.alchemist.commands.punishments.menu

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandPermission
import co.aikar.commands.annotation.Name
import ltd.matrixstudios.alchemist.commands.punishments.menu.executed.ExecutedPunishmentHistoryMenu
import ltd.matrixstudios.alchemist.models.profile.GameProfile
import org.bukkit.entity.Player

/**
 * Class created on 5/7/2023

 * @author 98ping
 * @project Alchemist
 * @website https://solo.to/redis
 */
class ExecutedPunishmentHistoryCommand : BaseCommand() {

    @CommandAlias("staffhist|staffhistory|executedhistory")
    @CommandPermission("alchemist.punishments.check.others")
    fun checkOthers(player: Player, @Name("target") profile: GameProfile)
    {
        ExecutedPunishmentHistoryMenu(player, profile).openMenu()
    }
}
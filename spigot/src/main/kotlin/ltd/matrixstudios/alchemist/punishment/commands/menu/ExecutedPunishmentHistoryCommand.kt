package ltd.matrixstudios.alchemist.punishment.commands.menu

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.*
import ltd.matrixstudios.alchemist.api.AlchemistAPI
import ltd.matrixstudios.alchemist.models.profile.GameProfile
import ltd.matrixstudios.alchemist.punishment.commands.menu.executed.ExecutedPunishmentHistoryMenu
import ltd.matrixstudios.alchemist.util.Chat
import org.bukkit.entity.Player

/**
 * Class created on 5/7/2023

 * @author 98ping
 * @project Alchemist
 * @website https://solo.to/redis
 */
class ExecutedPunishmentHistoryCommand : BaseCommand() {

    @CommandAlias("staffhist|staffhistory")
    @CommandPermission("alchemist.punishments.check")
    @CommandCompletion("@gameprofile")
    fun checkOthers(player: Player,  @Name("target") @Optional profile: GameProfile?)
    {
        if (!player.hasPermission("alchemist.punishments.check.others")) {
            val profile1 = AlchemistAPI.syncFindProfile(player.uniqueId) ?: return
            ExecutedPunishmentHistoryMenu(player, profile1).openMenu()
        } else {
            if (profile == null) {
                player.sendMessage(Chat.format("&cYou must provide a valid profile!"))
                return
            }

            ExecutedPunishmentHistoryMenu(player, profile).openMenu()
        }
    }
}
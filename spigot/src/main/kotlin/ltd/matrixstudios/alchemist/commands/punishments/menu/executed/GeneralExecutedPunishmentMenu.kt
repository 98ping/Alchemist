package ltd.matrixstudios.alchemist.commands.punishments.menu.executed

import ltd.matrixstudios.alchemist.commands.punishments.menu.HistoryMenu
import ltd.matrixstudios.alchemist.commands.punishments.menu.impl.GeneralPunishmentButton
import ltd.matrixstudios.alchemist.models.profile.GameProfile
import ltd.matrixstudios.alchemist.punishments.PunishmentType
import ltd.matrixstudios.alchemist.service.expirable.PunishmentService
import ltd.matrixstudios.alchemist.util.Chat
import ltd.matrixstudios.alchemist.util.menu.Button
import ltd.matrixstudios.alchemist.util.menu.buttons.SimpleActionButton
import ltd.matrixstudios.alchemist.util.menu.pagination.PaginatedMenu
import org.bukkit.Material
import org.bukkit.entity.Player

/**
 * Class created on 5/7/2023

 * @author 98ping
 * @project Alchemist
 * @website https://solo.to/redis
 */
class GeneralExecutedPunishmentMenu(var profile: GameProfile, var punishmentType: PunishmentType, var player: Player): PaginatedMenu(18, player) {

    override fun getHeaderItems(player: Player): MutableMap<Int, Button> {
        val buttons = mutableMapOf<Int, Button>()

        buttons[4] = SimpleActionButton(Material.PAPER, mutableListOf(), "&cGo Back", 0).setBody { player, slot, click ->
            ExecutedPunishmentHistoryMenu(player, profile).openMenu()
        }

        return buttons
    }
    override fun getPagesButtons(player: Player): MutableMap<Int, Button> {
        val buttons = hashMapOf<Int, Button>()

        var index = 0
        for (punishment in PunishmentService.findExecutorPunishments(profile.uuid).filter { it.getGrantable() == punishmentType }) {
            buttons[index++] = GeneralPunishmentButton(punishment)
        }

        return buttons
    }

    override fun getTitle(player: Player): String {
        return Chat.format(punishmentType.color + punishmentType.id + "s")
    }
}
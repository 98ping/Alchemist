package ltd.matrixstudios.alchemist.staff.requests.commands.menu

import ltd.matrixstudios.alchemist.api.AlchemistAPI
import ltd.matrixstudios.alchemist.staff.requests.report.ReportModel
import ltd.matrixstudios.alchemist.util.Chat
import ltd.matrixstudios.alchemist.util.menu.Button
import ltd.matrixstudios.alchemist.util.menu.type.BorderedPaginatedMenu
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import java.util.Date

/**
 * Class created on 9/13/2023

 * @author 98ping
 * @project Alchemist
 * @website https://solo.to/redis
 */
class ShowReportsMenu(val player: Player, val reports: MutableList<ReportModel>) : BorderedPaginatedMenu(player) {
    override fun getPagesButtons(player: Player): MutableMap<Int, Button> {
        val buttons = mutableMapOf<Int, Button>()
        var i = 0

        for (report in reports)
        {
            buttons[i++] = ReportsButton(report)
        }

        return buttons
    }

    override fun getTitle(player: Player): String {
        return "Viewing Specific Reports"
    }

    class ReportsButton(val model: ReportModel) : Button()
    {
        override fun getMaterial(player: Player): Material {
            return Material.PAPER
        }

        override fun getDescription(player: Player): MutableList<String>? {
            val desc = mutableListOf<String>()
            desc.add(Chat.format("&8Short Id: #${model.id.toString().substring(0, 6)}"))
            desc.add(" ")
            desc.add(Chat.format("&eReason: &f${model.reason}"))
            desc.add(Chat.format("&eIssued On: &f${model.server}"))
            desc.add(Chat.format("&eIssuer: &f${AlchemistAPI.getRankDisplay(model.issuer)}"))
            desc.add(Chat.format("&eIssued To: &f${AlchemistAPI.getRankDisplay(model.issuedTo)}"))
            desc.add(" ")
            desc.add(Chat.format("&aClick to jump to the target server"))

            return desc
        }

        override fun getDisplayName(player: Player): String? {
            val name = "&a${Date(model.issuedAt)}"

            return Chat.format(name)
        }

        override fun getData(player: Player): Short {
            return 0
        }

        override fun onClick(player: Player, slot: Int, type: ClickType) {
            TODO("Not yet implemented")
        }

    }
}
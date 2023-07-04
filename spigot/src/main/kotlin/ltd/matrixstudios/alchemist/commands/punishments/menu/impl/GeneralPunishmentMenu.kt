package ltd.matrixstudios.alchemist.commands.punishments.menu.impl

import com.google.common.base.Stopwatch
import ltd.matrixstudios.alchemist.api.AlchemistAPI
import ltd.matrixstudios.alchemist.commands.grants.menu.grants.GrantsMenu
import ltd.matrixstudios.alchemist.commands.grants.menu.grants.filter.GrantFilter
import ltd.matrixstudios.alchemist.commands.player.WipeGrantsCommand
import ltd.matrixstudios.alchemist.commands.punishments.menu.HistoryMenu
import ltd.matrixstudios.alchemist.commands.punishments.menu.impl.filter.PunishmentFilter
import ltd.matrixstudios.alchemist.commands.punishments.remove.WipePunishmentsCommand
import ltd.matrixstudios.alchemist.models.grant.types.Punishment
import ltd.matrixstudios.alchemist.models.profile.GameProfile
import ltd.matrixstudios.alchemist.punishments.PunishmentType
import ltd.matrixstudios.alchemist.service.expirable.PunishmentService
import ltd.matrixstudios.alchemist.service.expirable.RankGrantService
import ltd.matrixstudios.alchemist.util.Chat
import ltd.matrixstudios.alchemist.util.menu.Button
import ltd.matrixstudios.alchemist.util.menu.buttons.SimpleActionButton
import ltd.matrixstudios.alchemist.util.menu.buttons.SkullButton
import ltd.matrixstudios.alchemist.util.menu.pagination.PaginatedMenu
import org.bukkit.Material
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import java.util.concurrent.TimeUnit

class GeneralPunishmentMenu(var profile: GameProfile, var punishmentType: PunishmentType, var punishments: MutableList<Punishment>, var punishmentFilter: PunishmentFilter, var player: Player): PaginatedMenu(36, player) {

    override fun getPagesButtons(player: Player): MutableMap<Int, Button> {
        val buttons = hashMapOf<Int, Button>()

        var index = 0
        for (punishment in punishments) {
            buttons[index++] = GeneralPunishmentButton(punishment)
        }

        return buttons
    }

    override fun getButtonPositions(): List<Int> {
        return listOf(12, 13, 14, 15, 16,
            21, 22, 23, 24, 25,
            30, 31, 32, 33, 34)
    }

    override fun getHeaderItems(player: Player): MutableMap<Int, Button> {
        return mutableMapOf(
            1 to Button.placeholder(),
            2 to Button.placeholder(),
            3 to Button.placeholder(),
            4 to SimpleActionButton(Material.PAPER, mutableListOf(), "&cGo Back", 0).setBody { player, slot, click ->
                HistoryMenu(profile, player).openMenu()
            },
            5 to Button.placeholder(),
            6 to Button.placeholder(),
            7 to Button.placeholder(),
            17 to Button.placeholder(),
            18 to Button.placeholder(),
            26 to Button.placeholder(),
            35 to Button.placeholder(),
            36 to Button.placeholder(),
            37 to Button.placeholder(),
            38 to Button.placeholder(),
            39 to Button.placeholder(),
            40 to Button.placeholder(),
            41 to Button.placeholder(),
            42 to Button.placeholder(),
            43 to Button.placeholder(),
            44 to Button.placeholder(),
            9 to Button.placeholder(),
            27 to Button.placeholder(),
            19 to SkullButton("eyJ0aW1lc3RhbXAiOjE1MTA5MzU0NTkwMTMsInByb2ZpbGVJZCI6IjdkYTJhYjNhOTNjYTQ4ZWU4MzA0OGFmYzNiODBlNjhlIiwicHJvZmlsZU5hbWUiOiJHb2xkYXBmZWwiLCJzaWduYXR1cmVSZXF1aXJlZCI6dHJ1ZSwidGV4dHVyZXMiOnsiU0tJTiI6eyJ1cmwiOiJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlL2VlMWFjMzk4MmI4MTk5MzE1MmNhZDVmZWI1NmE3NWM4MzA3MmE1NjU1ZGMwNzEzN2ZkNjVkMWZmODk1MjI4MSJ9fX0=",
                getFilterDesc(), Chat.format("&eFilter Punishments")).setBody { player, i, clickType ->
                val values = PunishmentFilter.values()
                val index = values.indexOf(punishmentFilter)
                val next = (index + 1)
                val limit = values.size - 1

                if (next > limit)
                {
                    GeneralPunishmentMenu(profile, punishmentType, values[0].lambda.invoke(PunishmentService.getFromCache(profile.uuid)).toMutableList(), values[0], player).updateMenu()

                    return@setBody
                }

                GeneralPunishmentMenu(profile, punishmentType, values[next].lambda.invoke(PunishmentService.getFromCache(profile.uuid)).toMutableList(), values[next], player).updateMenu()
            },
            10 to Button.placeholder(),
            28 to Button.placeholder(),
            11 to Button.placeholder(),
            20 to Button.placeholder(),
            29 to Button.placeholder(),
        )
    }

    fun getFilterDesc(): MutableList<String> {
        val desc = mutableListOf<String>()
        desc.add(" ")
        for (filter in PunishmentFilter.values())
        {
            if (punishmentFilter == filter)
            {
                desc.add(Chat.format("&7- &a" + punishmentFilter.displayName))
            } else {
                desc.add(Chat.format("&7- &7" + filter.displayName))
            }
        }
        desc.add(" ")
        desc.add(Chat.format("&7Click to move to next filter!"))
        desc.add(" ")

        return desc
    }

    override fun getButtonsPerPage(): Int {
        return 21
    }

    override fun getTitle(player: Player): String {
        return Chat.format(punishmentType.color + punishmentType.niceName + "s")
    }
}
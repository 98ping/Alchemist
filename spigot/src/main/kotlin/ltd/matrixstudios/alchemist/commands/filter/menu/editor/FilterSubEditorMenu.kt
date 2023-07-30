package ltd.matrixstudios.alchemist.commands.filter.menu.editor

import ltd.matrixstudios.alchemist.commands.filter.menu.editor.punishments.PunishmentTypeSelectionMenu
import ltd.matrixstudios.alchemist.models.filter.Filter
import ltd.matrixstudios.alchemist.redis.AsynchronousRedisSender
import ltd.matrixstudios.alchemist.redis.cache.refresh.RefreshFiltersPacket
import ltd.matrixstudios.alchemist.service.filter.FilterService
import ltd.matrixstudios.alchemist.util.Chat
import ltd.matrixstudios.alchemist.util.InputPrompt
import ltd.matrixstudios.alchemist.util.TimeUtil
import ltd.matrixstudios.alchemist.util.menu.Button
import ltd.matrixstudios.alchemist.util.menu.Menu
import ltd.matrixstudios.alchemist.util.menu.buttons.SimpleActionButton
import org.bukkit.Material
import org.bukkit.entity.Player

class FilterSubEditorMenu(val player: Player, val filter: Filter) : Menu(player) {

    init {
        placeholder = true
        staticSize = 27
    }

    override fun getButtons(player: Player): MutableMap<Int, Button> {
        val buttons = mutableMapOf<Int, Button>()

        buttons[10] = SimpleActionButton(
            Material.QUARTZ,
            mutableListOf(
                " ",
                Chat.format("&eChange the silent status of the filter"),
                Chat.format("&eCurrently: &f" + if (filter.silent) "&aSilent" else "&cPublic"),
                " "
            ),
            "&eChange Silent Status", 0
        ).setBody { player, slot, clicktype ->
            val otherBool = !filter.silent
            filter.silent = otherBool
            FilterService.save(filter)
            AsynchronousRedisSender.send(RefreshFiltersPacket())

            player.sendMessage(Chat.format("&eUpdate the silent status of &6${filter.word} &eto " + if (otherBool) "&aSilent" else "&cPublic"))
            FilterSubEditorMenu(player, filter).openMenu()
        }

        buttons[11] = SimpleActionButton(
            Material.REDSTONE_TORCH_ON,
            mutableListOf(
                " ",
                Chat.format("&eChange the punishment type"),
                Chat.format("&eCurrently: &f" + filter.punishmentType.color + filter.punishmentType.niceName),
                " "
            ),
            "&eChange Punishment Type", 0
        ).setBody { player, slot, clicktype ->
            PunishmentTypeSelectionMenu(player, filter).openMenu()
        }

        buttons[12] = SimpleActionButton(
            Material.WATCH,
            mutableListOf(
                " ",
                Chat.format("&eChange the duration of the punishment"),
                Chat.format("&eCurrently: &f" + filter.duration),
                " "
            ),
            "&eChange Duration of Punishment", 0
        ).setBody { player, slot, clicktype ->
            InputPrompt()
                .withText(Chat.format("&aType in the new duration you want to use!"))
                .acceptInput {
                    val verifyDuration = TimeUtil.parseTime(it)

                    filter.duration = it

                    FilterService.save(filter)
                    AsynchronousRedisSender.send(RefreshFiltersPacket())

                    player.sendMessage(Chat.format("&aSet the duration of the punishment to $it"))
                    FilterSubEditorMenu(player, filter).openMenu()
                }.start(player)
        }

        buttons[13] = SimpleActionButton(
            Material.BEACON,
            mutableListOf(
                " ",
                Chat.format("&eCan Staff Bypass the Filter?"),
                Chat.format("&eCurrently: &f" + if (filter.staffExempt) "&eTrue" else "&cFalse"),
                " "
            ),
            "&eChange Staff Bypass", 0
        ).setBody { player, slot, clicktype ->
            val otherBool = !filter.staffExempt
            filter.staffExempt = otherBool
            FilterService.save(filter)
            AsynchronousRedisSender.send(RefreshFiltersPacket())

            player.sendMessage(Chat.format("&eUpdate the staff exempt status of &6${filter.word} &eto " + if (otherBool) "&aTrue" else "&cFalse"))
            FilterSubEditorMenu(player, filter).openMenu()
        }

        buttons[14] = SimpleActionButton(
            Material.EMERALD,
            mutableListOf(
                " ",
                Chat.format("&eSet Exempt Permission"),
                Chat.format("&eCurrently: &f" + filter.exemptPermission),
                " "
            ),
            "&eChange Staff Bypass", 0
        ).setBody { player, slot, clicktype ->
            InputPrompt()
                .withText(Chat.format("&aType in the new permission you want to use!"))
                .acceptInput {
                    filter.exemptPermission = it
                    FilterService.save(filter)
                    AsynchronousRedisSender.send(RefreshFiltersPacket())

                    player.sendMessage(Chat.format("&eUpdate the exempt permission of &6${filter.word} &eto " + it))
                    FilterSubEditorMenu(player, filter).openMenu()
                }.start(player)
        }

        buttons[15] = SimpleActionButton(
            Material.PAINTING,
            mutableListOf(
                " ",
                Chat.format("&eChange the punishment status of the filter"),
                Chat.format("&eCurrently: &f" + if (filter.shouldPunish) "&aShould Punish" else "&cShouldn't Punish"),
                " "
            ),
            "&eChange Punishment Status", 0
        ).setBody { player, slot, clicktype ->
            val otherBool = !filter.shouldPunish
            filter.shouldPunish = otherBool
            FilterService.save(filter)
            AsynchronousRedisSender.send(RefreshFiltersPacket())

            player.sendMessage(Chat.format("&eUpdate the punishment status of &6${filter.word} &eto " + if (filter.shouldPunish) "&aShould Punish" else "&cShouldn't Punish"))
            FilterSubEditorMenu(player, filter).openMenu()
        }

        return buttons
    }

    override fun getTitle(player: Player): String {
        return "Editing: ${filter.word}"
    }
}
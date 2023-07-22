package ltd.matrixstudios.alchemist.commands.filter.menu.editor.punishments

import ltd.matrixstudios.alchemist.commands.filter.menu.editor.FilterSubEditorMenu
import ltd.matrixstudios.alchemist.models.filter.Filter
import ltd.matrixstudios.alchemist.punishments.PunishmentType
import ltd.matrixstudios.alchemist.redis.AsynchronousRedisSender
import ltd.matrixstudios.alchemist.profiles.cache.RefreshFiltersPacket
import ltd.matrixstudios.alchemist.service.filter.FilterService
import ltd.matrixstudios.alchemist.util.Chat
import ltd.matrixstudios.alchemist.util.menu.Button
import ltd.matrixstudios.alchemist.util.menu.Menu
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType

class PunishmentTypeSelectionMenu(val player: Player, val filter: Filter) : Menu(player) {

    init {
        staticSize = 9
    }
    override fun getButtons(player: Player): MutableMap<Int, Button> {
        val buttons = hashMapOf<Int, Button>()

        buttons[1] = SelectPunishmentTypeButton(PunishmentType.WARN, filter)
        buttons[3] = SelectPunishmentTypeButton(PunishmentType.MUTE, filter)
        buttons[5] = SelectPunishmentTypeButton(PunishmentType.BAN, filter)
        buttons[7] = SelectPunishmentTypeButton(PunishmentType.BLACKLIST, filter)

        return buttons
    }

    override fun getTitle(player: Player): String {
        return "Select a PunishmentType"
    }

    class SelectPunishmentTypeButton(val punishmentType: PunishmentType, val filter: Filter) : Button()
    {
        override fun getMaterial(player: Player): Material {
            return Material.INK_SACK
        }

        override fun getDescription(player: Player): MutableList<String>? {
            val desc = mutableListOf<String>()
            desc.add(Chat.format("&6&m---------------------"))
            desc.add(Chat.format("&ePunishment Type: " + punishmentType.color + punishmentType.niceName))
            desc.add(Chat.format(" "))
            desc.add(Chat.format("&aClick to select!"))
            desc.add(Chat.format("&6&m---------------------"))

            return desc
        }

        override fun getDisplayName(player: Player): String? {
            return Chat.format(punishmentType.color + punishmentType.niceName)
        }

        override fun getData(player: Player): Short {
            return Chat.getDyeColor(punishmentType.color).dyeData.toShort()
        }

        override fun onClick(player: Player, slot: Int, type: ClickType) {
            filter.punishmentType = punishmentType
            FilterService.save(filter)
            player.sendMessage(Chat.format("&eUpdated the punishment type to " + punishmentType.color + punishmentType.niceName))
            AsynchronousRedisSender.send(RefreshFiltersPacket())
            FilterSubEditorMenu(player, filter).openMenu()
        }

    }
}
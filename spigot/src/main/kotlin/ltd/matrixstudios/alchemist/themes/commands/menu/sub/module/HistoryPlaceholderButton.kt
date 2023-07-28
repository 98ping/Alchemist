package ltd.matrixstudios.alchemist.themes.commands.menu.sub.module

import ltd.matrixstudios.alchemist.models.profile.GameProfile
import ltd.matrixstudios.alchemist.punishments.PunishmentType
import ltd.matrixstudios.alchemist.themes.Theme
import ltd.matrixstudios.alchemist.util.Chat
import ltd.matrixstudios.alchemist.util.menu.Button
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType

class HistoryPlaceholderButton(val theme: Theme, val player: Player, val target: GameProfile) : Button() {

    var type = PunishmentType.BAN

    override fun getMaterial(player: Player): Material {
        return Material.WOOL
    }

    override fun getDescription(player: Player): MutableList<String>? {
        return theme.getHistoryPlaceholderLore(player, target, type).map { Chat.format(it) }.toMutableList()
    }

    override fun getDisplayName(player: Player): String? {
        return theme.getHistoryPlaceholderName(player, target, type)
    }

    override fun getData(player: Player): Short {
        return theme.getHistoryPlaceholderData(player, target, type)
    }

    override fun onClick(player: Player, slot: Int, type: ClickType) {
        return
    }
}
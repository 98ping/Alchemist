package ltd.matrixstudios.alchemist.commands.punishments.menu

import ltd.matrixstudios.alchemist.Alchemist
import ltd.matrixstudios.alchemist.api.AlchemistAPI
import ltd.matrixstudios.alchemist.commands.punishments.menu.impl.GeneralPunishmentMenu
import ltd.matrixstudios.alchemist.models.profile.GameProfile
import ltd.matrixstudios.alchemist.punishments.PunishmentType
import ltd.matrixstudios.alchemist.util.Chat
import ltd.matrixstudios.alchemist.util.menu.Button
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType

class HistoryPlaceholderButton(var punishmentType: PunishmentType, var gameProfile: GameProfile) : Button() {

    override fun getMaterial(player: Player): Material {
        return Material.WOOL
    }

    override fun getDescription(player: Player): MutableList<String>? {
        return arrayListOf()
    }

    override fun getDisplayName(player: Player): String? {
        return Chat.format(punishmentType.color + punishmentType.id.replaceFirstChar { it.uppercase()}) + "s"
    }

    override fun getData(player: Player): Short {
        return AlchemistAPI.getWoolColor(punishmentType.color).woolData.toShort()
    }

    override fun onClick(player: Player, slot: Int, type: ClickType) {
        GeneralPunishmentMenu(gameProfile, punishmentType, player).updateMenu()
    }



}

package ltd.matrixstudios.alchemist.commands.punishments.menu

import ltd.matrixstudios.alchemist.Alchemist
import ltd.matrixstudios.alchemist.api.AlchemistAPI
import ltd.matrixstudios.alchemist.commands.punishments.menu.impl.GeneralPunishmentMenu
import ltd.matrixstudios.alchemist.commands.punishments.menu.impl.filter.PunishmentFilter
import ltd.matrixstudios.alchemist.models.profile.GameProfile
import ltd.matrixstudios.alchemist.punishments.PunishmentType
import ltd.matrixstudios.alchemist.service.expirable.PunishmentService
import ltd.matrixstudios.alchemist.service.profiles.ProfileGameService
import ltd.matrixstudios.alchemist.themes.ThemeLoader
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
        return ThemeLoader.defaultTheme.getHistoryPlaceholderLore(player, gameProfile, punishmentType).map { Chat.format(it) }.toMutableList()
    }

    override fun getDisplayName(player: Player): String? {
        return ThemeLoader.defaultTheme.getHistoryPlaceholderName(player, gameProfile, punishmentType)
    }

    override fun getData(player: Player): Short {
        return ThemeLoader.defaultTheme.getHistoryPlaceholderData(player, gameProfile, punishmentType)
    }

    override fun onClick(player: Player, slot: Int, type: ClickType) {
        GeneralPunishmentMenu(gameProfile, punishmentType, PunishmentService.getFromCache(player.uniqueId).toMutableList(), PunishmentFilter.ALL, player).updateMenu()
    }



}

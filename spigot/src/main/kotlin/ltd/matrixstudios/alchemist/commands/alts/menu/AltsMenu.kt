package ltd.matrixstudios.alchemist.commands.alts.menu

import ltd.matrixstudios.alchemist.Alchemist
import ltd.matrixstudios.alchemist.api.AlchemistAPI
import ltd.matrixstudios.alchemist.models.profile.GameProfile
import ltd.matrixstudios.alchemist.util.Chat
import ltd.matrixstudios.alchemist.util.menu.Button
import ltd.matrixstudios.alchemist.util.menu.pagination.PaginatedMenu
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import java.util.Date

class AltsMenu(var player: Player, var target: GameProfile, var alts: MutableList<GameProfile>) : PaginatedMenu(18, player) {
    override fun getPagesButtons(player: Player): MutableMap<Int, Button> {
        var buttons = hashMapOf<Int, Button>()

        var index = 0
        for (alt in alts)
        {
            buttons[index++] = AltButton(alt, target)
        }

        return buttons
    }

    override fun getTitle(player: Player): String {
        return Chat.format(AlchemistAPI.getRankDisplay(target.uuid) + "&6's Alts")
    }


    class AltButton(val gameProfile: GameProfile, val target: GameProfile) : Button() {
        override fun getMaterial(player: Player): Material {
            return Material.SKULL_ITEM
        }

        override fun getDescription(player: Player): MutableList<String>? {
            val desc = mutableListOf<String>()
            desc.add(Chat.format("&eName: &r" + AlchemistAPI.getRankDisplay(gameProfile.uuid)))
            desc.add(Chat.format("&eLast Seen: &f" + Date(gameProfile.lastSeenAt)))
            desc.add(Chat.format("&6&m-------------------------------"))
            if (target.ip == gameProfile.ip)
            {
                desc.add(Chat.format("&aCurrently matching ${AlchemistAPI.getRankDisplay(target.uuid)}"))
            }

            val punishments = target.getActivePunishments()
            if (!punishments.isEmpty())
            {
                val firstPunishment = punishments.first()
                desc.add(Chat.format("${firstPunishment.getGrantable().color}${firstPunishment.getGrantable().niceName} &ais currently matching ${AlchemistAPI.getRankDisplay(target.uuid)}"))
            }
            desc.add(Chat.format("&6&m-------------------------------"))
            desc.add(Chat.format(AlchemistAPI.getRankDisplay(gameProfile.uuid) + "&e's Current IP data:"))
            desc.add(Chat.format("  &eLast Login: " + Date(gameProfile.lastSeenAt)))
            desc.add(Chat.format("  &ePercent Of Accuracy: &f" + gameProfile.accuracyOfRelation(target)))

            return desc
        }

        override fun getDisplayName(player: Player): String? {
            return Chat.format(AlchemistAPI.getRankDisplay(gameProfile.uuid))
        }

        override fun getData(player: Player): Short {
            return 3
        }

        override fun onClick(player: Player, slot: Int, type: ClickType) {

        }

    }
}
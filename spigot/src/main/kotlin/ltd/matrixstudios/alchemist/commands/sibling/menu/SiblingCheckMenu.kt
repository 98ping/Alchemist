package ltd.matrixstudios.alchemist.commands.sibling.menu

import ltd.matrixstudios.alchemist.api.AlchemistAPI
import ltd.matrixstudios.alchemist.models.profile.GameProfile
import ltd.matrixstudios.alchemist.service.profiles.ProfileGameService
import ltd.matrixstudios.alchemist.util.Chat
import ltd.matrixstudios.alchemist.util.menu.Button
import ltd.matrixstudios.alchemist.util.menu.pagination.PaginatedMenu
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import java.util.UUID

/**
 * Class created on 7/4/2023

 * @author 98ping
 * @project Alchemist
 * @website https://solo.to/redis
 */
class SiblingCheckMenu(val target: GameProfile, val player: Player) : PaginatedMenu(18, player) {
    override fun getPagesButtons(player: Player): MutableMap<Int, Button> {
        var i = 0
        val buttons = mutableMapOf<Int, Button>()

        for (sibling in target.getAllSiblings()) {
            buttons[i++] = SiblingButton(sibling, target)
        }

        return buttons
    }

    override fun getButtonPositions(): List<Int> {
        return listOf(10, 11, 12, 13, 14, 15, 16)
    }

    override fun getButtonsPerPage(): Int {
        return 7
    }

    override fun getHeaderItems(player: Player): MutableMap<Int, Button> {
        return mutableMapOf(
        1 to Button.placeholder(),
        2 to Button.placeholder(),
        3 to Button.placeholder(),
        4 to Button.placeholder(),
        5 to Button.placeholder(),
        6 to Button.placeholder(),
        7 to Button.placeholder(),
        9 to Button.placeholder(),
        17 to Button.placeholder(),
        18 to Button.placeholder(),
        19 to Button.placeholder(),
        20 to Button.placeholder(),
        21 to Button.placeholder(),
        22 to Button.placeholder(),
        23 to Button.placeholder(),
        24 to Button.placeholder(),
        25 to Button.placeholder(),
        26 to Button.placeholder())
    }

    override fun getTitle(player: Player): String {
        return Chat.format("&7Siblings of " + target.getRankDisplay())
    }

    class SiblingButton(val uuid: UUID, val target: GameProfile) : Button() {

        val profile = AlchemistAPI.syncFindProfile(uuid)!!
        override fun getMaterial(player: Player): Material {
            return Material.PAPER
        }

        override fun getDescription(player: Player): MutableList<String>? {
            val desc = mutableListOf<String>()

            desc.add(Chat.format("&6&m------------------------"))
            desc.add(Chat.format("&eName: &f" + profile.getRankDisplay()))
            desc.add(Chat.format("&eMatching IPs: &f" + if (target.ip == profile.ip) "&aYes" else "&cNo"))
            desc.add(Chat.format("&eOnline: &f" + if (profile.isOnline()) "&aYes" else "&cNo"))
            desc.add(Chat.format("&eFound on: &f" + if (profile.isOnline()) profile.metadata.get("server").asString else "&cN/A"))
            desc.add(Chat.format("&eRank: &f" + (profile.getCurrentRank()!!.color + profile.getCurrentRank()!!.displayName)))
            desc.add(Chat.format("&6&m------------------------"))
            desc.add(Chat.format("&a&lClick to remove this sibling"))
            desc.add(Chat.format("&6&m------------------------"))

            return desc
        }

        override fun getDisplayName(player: Player): String? {
            return Chat.format(profile.getRankDisplay())
        }

        override fun getData(player: Player): Short {
            return 0
        }

        override fun onClick(player: Player, slot: Int, type: ClickType) {
            target.getAllSiblings().remove(profile.uuid)
            profile.getAllSiblings().remove(target.uuid)

            ProfileGameService.save(target)
            ProfileGameService.save(profile)
            player.sendMessage(Chat.format("&aUpdated siblings!"))
            player.closeInventory()
            SiblingCheckMenu(target, player).updateMenu()
        }

    }
}
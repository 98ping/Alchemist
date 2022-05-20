package ltd.matrixstudios.alchemist.commands.tags.menu

import ltd.matrixstudios.alchemist.api.AlchemistAPI
import ltd.matrixstudios.alchemist.models.tags.Tag
import ltd.matrixstudios.alchemist.service.profiles.ProfileGameService
import ltd.matrixstudios.alchemist.service.profiles.ProfileSearchService
import ltd.matrixstudios.alchemist.util.Chat
import ltd.matrixstudios.alchemist.util.menu.Button
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType

class GeneralTagButton(var tag: Tag) : Button() {

    override fun getMaterial(player: Player): Material {
        return Material.INK_SACK
    }

    override fun getDescription(player: Player): MutableList<String>? {
        val desc = arrayListOf<String>()

        desc.add(Chat.format("&7&m------------------------"))
        desc.add(Chat.format("&eName: &c" + tag.menuName))
        desc.add(Chat.format("&ePurchasable: &c" + tag.purchasable))
        desc.add(Chat.format("&ePrefix: &c" + tag.prefix))
        desc.add(Chat.format("&7&m------------------------"))

        return desc
    }

    override fun getDisplayName(player: Player): String? {
        return Chat.format(tag.menuName)
    }

    override fun getData(player: Player): Short {
        return AlchemistAPI.getWoolColor(tag.menuName).dyeData.toShort()
    }

    override fun onClick(player: Player, slot: Int, type: ClickType) {
        val playerProfile = ProfileSearchService.getAsync(player.uniqueId).get()!!

        if (playerProfile == null) {
            player.sendMessage(Chat.format("&cNull profile. Cannot equip prefixes"))
            return
        }

        if (playerProfile.canUse(tag)) {
            playerProfile.activePrefix = tag.id
            ProfileGameService.save(playerProfile)
            player.sendMessage(Chat.format("&aSet your tag to " + tag.menuName))
        } else {
            player.sendMessage(Chat.format("&cYou are unable to use this tag. " + (if (tag.purchasable) "&cYou may purchase it on our store to use it" else "&cThis prefix is unable to be purchased")))
        }
    }
}
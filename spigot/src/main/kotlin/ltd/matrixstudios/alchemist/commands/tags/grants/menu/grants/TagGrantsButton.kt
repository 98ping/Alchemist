package ltd.matrixstudios.alchemist.commands.tags.grants.menu.grants

import ltd.matrixstudios.alchemist.api.AlchemistAPI
import ltd.matrixstudios.alchemist.models.grant.types.TagGrant
import ltd.matrixstudios.alchemist.models.tags.Tag
import ltd.matrixstudios.alchemist.util.Chat
import ltd.matrixstudios.alchemist.util.TimeUtil
import ltd.matrixstudios.alchemist.util.menu.Button
import org.bukkit.DyeColor
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import java.util.*

class TagGrantsButton(var tag: TagGrant) : Button() {


    override fun getMaterial(player: Player): Material {
        return Material.WOOL
    }

    override fun getDescription(player: Player): MutableList<String>? {
        val desc = arrayListOf<String>()

        desc.add(Chat.format("&7&m--------------------"))
        desc.add(Chat.format("&eGrant Properties:"))
        desc.add(Chat.format("&7* &eTag: &c${tag.getGrantable()!!.menuName}"))
        desc.add(Chat.format("&7* &eExecutor: ${AlchemistAPI.getRankDisplay(tag.executor)}"))
        desc.add(Chat.format("&7* &eDuration: &c${TimeUtil.formatDuration(tag.expirable.duration)}"))
        desc.add(" ")
        desc.add(Chat.format("&eActor:"))
        desc.add(Chat.format("&7* &eType: &c" + tag.internalActor.actorType.name))
        desc.add(Chat.format("&7* &eExecuted From: &c" + tag.internalActor.executor.name))
        desc.add(Chat.format("&7&m--------------------"))


        return desc
    }

    override fun getDisplayName(player: Player): String? {
        return Chat.format((if (tag.expirable.isActive()) "&a" else "&c") + Date(tag.expirable.addedAt))
    }

    override fun getData(player: Player): Short {
        return (if (tag.expirable.isActive()) DyeColor.GREEN.woolData.toShort() else DyeColor.RED.woolData.toShort())
    }

    override fun onClick(player: Player, slot: Int, type: ClickType) {
    }
}
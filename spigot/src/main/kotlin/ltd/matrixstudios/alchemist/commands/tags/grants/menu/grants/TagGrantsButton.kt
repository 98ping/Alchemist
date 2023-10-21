package ltd.matrixstudios.alchemist.commands.tags.grants.menu.grants

import ltd.matrixstudios.alchemist.api.AlchemistAPI
import ltd.matrixstudios.alchemist.models.grant.types.TagGrant
import ltd.matrixstudios.alchemist.util.Chat
import ltd.matrixstudios.alchemist.util.TimeUtil
import ltd.matrixstudios.alchemist.util.menu.Button
import org.bukkit.DyeColor
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import java.util.*

class TagGrantsButton(var tag: TagGrant) : Button()
{


    override fun getMaterial(player: Player): Material
    {
        return Material.WOOL
    }

    override fun getDescription(player: Player): MutableList<String>
    {
        val desc = arrayListOf<String>()

        desc.add(Chat.format("&6&m-------------------------------------"))
        desc.add(Chat.format("&eTag: &c${tag.getGrantable()?.menuName ?: "&fUnknown"}"))
        desc.add(Chat.format("&eTarget: ${AlchemistAPI.getRankDisplay(tag.target)}"))
        desc.add(Chat.format("&eDuration: &f${TimeUtil.formatDuration(tag.expirable.duration)}"))
        desc.add(Chat.format("&6&m-------------------------------------"))
        desc.add(Chat.format("&eType: &f" + Chat.enumToDisplay(tag.internalActor.actorType.name)))
        desc.add(Chat.format("&eExecuted From: &f" + Chat.enumToDisplay(tag.internalActor.executor.name)))
        desc.add(Chat.format("&6&m-------------------------------------"))
        desc.add(Chat.format("&eAdded By: &f${AlchemistAPI.getRankDisplay(tag.executor)}"))
        desc.add(Chat.format("&eAdded Reason: &f${tag.reason}"))
        if (!tag.expirable.isActive())
        {
            desc.add(Chat.format("&6&m-------------------------------------"))
            desc.add(Chat.format("&eRemoved Reason: &f" + (tag.removedReason ?: "Unknown")))
            desc.add(Chat.format("&eRemoved By: &f" + AlchemistAPI.getRankDisplay(tag.removedBy!!)))
            desc.add(Chat.format("&eRemoved At: &f${Date(tag.expirable.removedAt)}"))
        }
        desc.add(Chat.format("&6&m-------------------------------------"))
        return desc
    }

    override fun getDisplayName(player: Player): String
    {
        return Chat.format((if (tag.expirable.isActive()) "&a&l(Active) " else "&c&l(Inactive) ") + Date(tag.expirable.addedAt))
    }

    override fun getData(player: Player): Short
    {
        return (if (tag.expirable.isActive()) DyeColor.GREEN.woolData.toShort() else DyeColor.RED.woolData.toShort())
    }

    override fun onClick(player: Player, slot: Int, type: ClickType)
    {
    }
}
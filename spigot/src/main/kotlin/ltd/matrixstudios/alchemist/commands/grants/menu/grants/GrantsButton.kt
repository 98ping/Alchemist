package ltd.matrixstudios.alchemist.commands.grants.menu.grants

import ltd.matrixstudios.alchemist.api.AlchemistAPI
import ltd.matrixstudios.alchemist.models.grant.types.RankGrant
import ltd.matrixstudios.alchemist.util.Chat
import ltd.matrixstudios.alchemist.util.TimeUtil
import ltd.matrixstudios.alchemist.util.menu.Button
import org.bukkit.DyeColor
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import java.util.*

class GrantsButton(var rankGrant: RankGrant) : Button() {


    override fun getMaterial(player: Player): Material {
        return Material.WOOL
    }

    override fun getDescription(player: Player): MutableList<String>? {
        val desc = arrayListOf<String>()

        desc.add(Chat.format("&7&m--------------------"))
        desc.add(Chat.format("&eGrant Properties:"))
        desc.add(Chat.format("&7* &eRank: ${rankGrant.getGrantable()!!.color}${rankGrant.getGrantable()!!.displayName}"))
        desc.add(Chat.format("&7* &eExecutor: ${AlchemistAPI.getRankDisplay(rankGrant.executor)}"))
        desc.add(Chat.format("&7* &eDuration: &c${TimeUtil.formatDuration(rankGrant.expirable.duration)}"))
        desc.add(" ")
        desc.add(Chat.format("&eActor:"))
        desc.add(Chat.format("&7* &eExecuted On: &c" + rankGrant.internalActor.name))
        desc.add(Chat.format("&7* &eType: &c" + rankGrant.internalActor.actorType.name))
        desc.add(Chat.format("&7* &eExecuted From: &c" + rankGrant.internalActor.executor.name))
        desc.add(Chat.format("&7&m--------------------"))


        return desc
    }

    override fun getDisplayName(player: Player): String? {
        return Chat.format((if (rankGrant.expirable.isActive()) "&a" else "&c") + Date(rankGrant.expirable.addedAt))
    }

    override fun getData(player: Player): Short {
        return (if (rankGrant.expirable.isActive()) DyeColor.GREEN.woolData.toShort() else DyeColor.RED.woolData.toShort())
    }

    override fun onClick(player: Player, slot: Int, type: ClickType) {
    }
}
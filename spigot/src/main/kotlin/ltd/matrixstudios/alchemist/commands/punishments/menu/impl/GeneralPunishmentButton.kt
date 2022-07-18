package ltd.matrixstudios.alchemist.commands.punishments.menu.impl

import ltd.matrixstudios.alchemist.api.AlchemistAPI
import ltd.matrixstudios.alchemist.models.grant.types.Punishment
import ltd.matrixstudios.alchemist.util.Chat
import ltd.matrixstudios.alchemist.util.TimeUtil
import ltd.matrixstudios.alchemist.util.menu.Button
import org.bukkit.DyeColor
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import org.bukkit.material.Wool
import java.util.*

class GeneralPunishmentButton(var punishment: Punishment) : Button() {

    override fun getMaterial(player: Player): Material {
        return Material.WOOL
    }

    override fun getDescription(player: Player): MutableList<String>? {
        val desc = arrayListOf<String>()
        desc.add(Chat.format("&6&m--------------------"))
        desc.add(Chat.format("&eTarget: &r" + AlchemistAPI.getRankDisplay(punishment.executor)))
        desc.add(Chat.format("&eDuration: &f" + TimeUtil.formatDuration(punishment.expirable.duration)))
        if (punishment.expirable.duration != Long.MAX_VALUE && punishment.expirable.isActive())
        {
            desc.add(Chat.format("&eRemaining: &f" + TimeUtil.formatDuration((punishment.expirable.addedAt + punishment.expirable.duration) - System.currentTimeMillis())))
        }
        desc.add(Chat.format("&6&m--------------------"))
        desc.add(Chat.format("&eActor:"))
        desc.add(Chat.format("&7- &eType: &c" + punishment.actor.actorType.name))
        desc.add(Chat.format("&7- &eExecuted From: &c" + punishment.actor.executor.name))
        desc.add(Chat.format("&6&m--------------------"))
        desc.add(Chat.format("&eIssued By: &f" + AlchemistAPI.getRankDisplay(punishment.executor)))
        desc.add(Chat.format("&eIssued Reason: &f" + punishment.reason))
        desc.add(Chat.format("&6&m--------------------"))
        if (!punishment.expirable.isActive())
        {
            desc.add(Chat.format("&eRemoved By: &f" + AlchemistAPI.getRankDisplay(punishment.removedBy!!)))
            desc.add(Chat.format("&eRemoved Reason: &f" + punishment.removedReason!!))
            desc.add(Chat.format("&6&m--------------------"))
        }
        return desc
    }

    override fun getDisplayName(player: Player): String? {
        return Chat.format((if (punishment.expirable.isActive()) "&a" else "&c") + Date(punishment.expirable.addedAt).toString())
    }

    override fun getData(player: Player): Short {
        return (if (punishment.expirable.isActive()) DyeColor.GREEN.woolData.toShort() else DyeColor.RED.woolData.toShort())
    }

    override fun onClick(player: Player, slot: Int, type: ClickType) {

    }
}
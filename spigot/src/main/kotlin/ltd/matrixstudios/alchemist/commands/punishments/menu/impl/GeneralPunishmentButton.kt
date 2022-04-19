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
        desc.add(Chat.format("&7&m--------------------"))
        desc.add(Chat.format("&ePunishment Info:"))
        desc.add(Chat.format("&7* &eTarget: &r" + AlchemistAPI.getRankDisplay(punishment.target)))
        desc.add(Chat.format("&7* &eExecutor: &r" + AlchemistAPI.getRankDisplay(punishment.executor)))
        desc.add(Chat.format("&7* &eDuration: &c" + TimeUtil.formatDuration(punishment.expirable.duration)))
        desc.add(" ")
        desc.add(Chat.format("&eActor:"))
        desc.add(Chat.format("&7* &eType: &c" + punishment.actor.actorType.name))
        desc.add(Chat.format("&7* &eExecuted From: &c" + punishment.actor.executor.name))
        desc.add(Chat.format("&7&m--------------------"))
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
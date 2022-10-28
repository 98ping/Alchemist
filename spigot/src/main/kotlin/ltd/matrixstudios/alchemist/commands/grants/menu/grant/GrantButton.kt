package ltd.matrixstudios.alchemist.commands.grants.menu.grant

import ltd.matrixstudios.alchemist.AlchemistSpigotPlugin
import ltd.matrixstudios.alchemist.api.AlchemistAPI
import ltd.matrixstudios.alchemist.models.grant.types.RankGrant
import ltd.matrixstudios.alchemist.models.profile.GameProfile
import ltd.matrixstudios.alchemist.models.ranks.Rank
import ltd.matrixstudios.alchemist.permissions.packet.PermissionUpdatePacket
import ltd.matrixstudios.alchemist.punishments.actor.ActorType
import ltd.matrixstudios.alchemist.punishments.actor.DefaultActor
import ltd.matrixstudios.alchemist.punishments.actor.executor.Executor
import ltd.matrixstudios.alchemist.redis.AsynchronousRedisSender
import ltd.matrixstudios.alchemist.redis.RedisPacketManager
import ltd.matrixstudios.alchemist.service.expirable.RankGrantService
import ltd.matrixstudios.alchemist.staff.packets.StaffAuditPacket
import ltd.matrixstudios.alchemist.util.Chat
import ltd.matrixstudios.alchemist.util.TimeUtil
import ltd.matrixstudios.alchemist.util.menu.Button
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.conversations.*
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType


class GrantButton(var rank: Rank, var gameProfile: GameProfile) : Button() {


    override fun getMaterial(player: Player): Material {
        return Material.WOOL
    }

    override fun getDescription(player: Player): MutableList<String>? {
        val desc = arrayListOf<String>()

        desc.add(Chat.format("&6&m---------------------"))
        desc.add(Chat.format("&ePriority: &6${rank.weight}"))
        desc.add(Chat.format("&ePrefix:  ${rank.prefix}"))
        desc.add(Chat.format("&eColor: ${rank.color}This"))
        desc.add(Chat.format("&eDefault: &6${rank.default}"))
        desc.add(Chat.format("&eStaff Rank: &6${rank.staff}"))
        desc.add(Chat.format("&6&m---------------------"))
        desc.add(Chat.format("&a&lLeft click to grant ${rank.color}${rank.displayName} &a&lto ${AlchemistAPI.getRankDisplay(gameProfile.uuid)}"))
        desc.add(Chat.format("&6&m---------------------"))


        return desc
    }

    override fun getDisplayName(player: Player): String? {
        return Chat.format("${rank.color}${rank.displayName}")
    }

    override fun getData(player: Player): Short {
        return AlchemistAPI.getWoolColor(rank.color).woolData.toShort()
    }

    override fun onClick(player: Player, slot: Int, type: ClickType) {
        DurationMenu(player, rank, gameProfile).updateMenu()
    }
}
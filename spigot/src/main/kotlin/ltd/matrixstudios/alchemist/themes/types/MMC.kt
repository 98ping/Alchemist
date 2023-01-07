package ltd.matrixstudios.alchemist.themes.types

import ltd.matrixstudios.alchemist.api.AlchemistAPI
import ltd.matrixstudios.alchemist.models.grant.types.Punishment
import ltd.matrixstudios.alchemist.models.grant.types.RankGrant
import ltd.matrixstudios.alchemist.models.profile.GameProfile
import ltd.matrixstudios.alchemist.models.ranks.Rank
import ltd.matrixstudios.alchemist.models.server.UniqueServer
import ltd.matrixstudios.alchemist.punishments.PunishmentType
import ltd.matrixstudios.alchemist.service.profiles.ProfileGameService
import ltd.matrixstudios.alchemist.themes.Theme
import ltd.matrixstudios.alchemist.util.Chat
import ltd.matrixstudios.alchemist.util.TimeUtil
import org.bukkit.DyeColor
import org.bukkit.Material
import org.bukkit.entity.Player
import java.util.*

class MMC : Theme(
    "MMC",
    "&dMMC &7/ &6&lInvadedLands",
    mutableListOf(" ", "&eSelect the &dMMC &7/ &6&lInvadedlands &etheme."),
    Material.IRON_SWORD,
    0
) {

    override fun getGrantsLore(player: Player, rankGrant: RankGrant): MutableList<String> {
        val desc = arrayListOf<String>()

        desc.add(Chat.format("&6&m--------------------"))
        desc.add(Chat.format("&eTarget: &r" + AlchemistAPI.getRankDisplay(rankGrant.target)))
        desc.add(Chat.format("&eRank: &r" + rankGrant.getGrantable()!!.color + rankGrant.getGrantable()!!.displayName))
        desc.add(Chat.format("&eDuration: &f" + TimeUtil.formatDuration(rankGrant.expirable.duration)))
        if (rankGrant.expirable.duration != Long.MAX_VALUE && rankGrant.expirable.isActive())
        {
            desc.add(Chat.format("&eRemaining: &f" + TimeUtil.formatDuration((rankGrant.expirable.addedAt + rankGrant.expirable.duration) - System.currentTimeMillis())))
        }
        desc.add(Chat.format("&6&m--------------------"))
        desc.add(Chat.format("&eActor:"))
        desc.add(Chat.format("&7- &eType: &c" + rankGrant.internalActor.actorType.name))
        desc.add(Chat.format("&7- &eExecuted From: &c" + rankGrant.internalActor.executor.name))
        desc.add(Chat.format("&6&m--------------------"))
        desc.add(Chat.format("&eIssued By: &f" + AlchemistAPI.getRankDisplay(rankGrant.executor)))
        desc.add(Chat.format("&eIssued Reason: &f" + rankGrant.reason))
        desc.add(Chat.format("&6&m--------------------"))
        if (!rankGrant.expirable.isActive())
        {
            desc.add(Chat.format("&eRemoved By: &f" + AlchemistAPI.getRankDisplay(rankGrant.removedBy!!)))
            desc.add(Chat.format("&eRemoved Reason: &f" + rankGrant.removedReason!!))
            desc.add(Chat.format("&6&m--------------------"))
        }


        return desc
    }

    override fun getGrantsDisplayName(player: Player, rankGrant: RankGrant): String {
        return Chat.format((if (rankGrant.expirable.isActive()) "&a&l(Active) " else "&c&l(Inactive) ") + Date(rankGrant.expirable.addedAt))
    }

    override fun getGrantsData(player: Player, rankGrant: RankGrant): Short {
        return (if (rankGrant.expirable.isActive()) DyeColor.GREEN.woolData.toShort() else DyeColor.RED.woolData.toShort())
    }


    override fun getGrantLore(player: Player, gameProfile: GameProfile, rank: Rank): MutableList<String> {
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

    override fun getGrantDisplayName(player: Player, rank: Rank): String {
        return Chat.format("${rank.color}${rank.displayName}")
    }

    override fun getGrantData(player: Player, rank: Rank): Short {
        return AlchemistAPI.getWoolColor(rank.color).woolData.toShort()
    }



    override fun getHistoryLore(player: Player, punishment: Punishment): MutableList<String> {
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
        desc.add(Chat.format("&aLeft-Click to view Proof Menu"))
        desc.add(Chat.format("&6&m--------------------"))
        return desc
    }

    override fun getHistoryDisplayName(player: Player, punishment: Punishment): String {
        return Chat.format((if (punishment.expirable.isActive()) "&a" else "&c") + Date(punishment.expirable.addedAt).toString())
    }

    override fun getHistoryData(player: Player, punishment: Punishment): Short {
        return (if (punishment.expirable.isActive()) DyeColor.GREEN.woolData.toShort() else DyeColor.RED.woolData.toShort())
    }

    override fun getHistoryPlaceholderLore(
        player: Player,
        profile: GameProfile,
        punishment: PunishmentType
    ): MutableList<String> {
        val desc = arrayListOf<String>()
        val punishments = profile.getPunishments().filter { it.getGrantable() == punishment }
        desc.addAll(
            listOf(
                "&7Viewing statistics for the",
                "&7${punishment.niceName} category:",
                "",
                " &7Total: &f${punishments.size}",
                " &7Active: &a${punishments.filter { it.expirable.isActive() }.size}",
                " &7Inactive: &c${
                    punishments.filter { p ->
                        !p.expirable.isActive()
                    }.size
                }",
                "",
                "&eClick to view more!"
            )
        )
        return desc
    }

    override fun getHistoryPlaceholderName(player: Player, profile: GameProfile, punishment: PunishmentType): String {
        return Chat.format(punishment.color + punishment.id.replaceFirstChar { it.uppercase()}) + "s"
    }

    override fun getHistoryPlaceholderData(player: Player, profile: GameProfile, punishment: PunishmentType): Short {
        return AlchemistAPI.getWoolColor(punishment.color).woolData.toShort()
    }
}
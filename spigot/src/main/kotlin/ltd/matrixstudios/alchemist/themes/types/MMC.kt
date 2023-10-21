package ltd.matrixstudios.alchemist.themes.types

import ltd.matrixstudios.alchemist.api.AlchemistAPI
import ltd.matrixstudios.alchemist.models.grant.types.Punishment
import ltd.matrixstudios.alchemist.models.grant.types.RankGrant
import ltd.matrixstudios.alchemist.models.profile.GameProfile
import ltd.matrixstudios.alchemist.models.ranks.Rank
import ltd.matrixstudios.alchemist.punishments.PunishmentType
import ltd.matrixstudios.alchemist.themes.Theme
import ltd.matrixstudios.alchemist.util.Chat
import ltd.matrixstudios.alchemist.util.TimeUtil
import org.bukkit.ChatColor
import org.bukkit.DyeColor
import org.bukkit.Material
import org.bukkit.entity.Player
import java.util.*

class MMC : Theme(
    "MMC",
    "&dXeNitrogen",
    mutableListOf(
        " ",
        "&7XeNitrogen is the core behind some of",
        "&7the biggest servers in the PotPvP/KitPvP",
        "&7scene. These being &dMinemenClub &7and",
        "&6InvadedLands&7. These 2 servers use XeNitrogen",
        "&7as their reliable backbone and it has a very unique",
        "&7design style which gets along very well with staff members",
        " ",
        "&eClick to select the &dXeNitrogen &etheme.",
        " "
    ),
    Material.IRON_SWORD,
    0
)
{

    override fun getGrantsLore(player: Player, rankGrant: RankGrant): MutableList<String>
    {
        val desc = arrayListOf<String>()

        if (!rankGrant.expirable.isActive())
        {
            desc.add(Chat.format("&c- ${Date(rankGrant.expirable.removedAt)}"))
        }
        desc.add(Chat.format("&6&m-------------------------------------"))
        desc.add(Chat.format("&eTarget: &r" + AlchemistAPI.getRankDisplay(rankGrant.target)))
        desc.add(Chat.format("&eRank: &r" + rankGrant.getGrantable().color + rankGrant.getGrantable().displayName))
        desc.add(Chat.format("&eDuration: &f" + TimeUtil.formatDuration(rankGrant.expirable.duration)))
        if (rankGrant.expirable.duration != Long.MAX_VALUE && rankGrant.expirable.isActive())
        {
            desc.add(Chat.format("&eRemaining: &f" + TimeUtil.formatDuration((rankGrant.expirable.addedAt + rankGrant.expirable.duration) - System.currentTimeMillis())))
        }
        desc.add(Chat.format("&6&m-------------------------------------"))
        desc.add(Chat.format("&eScopes:"))
        if (rankGrant.verifyGrantScope().global)
        {
            desc.add(Chat.format("&7- &aglobal"))
        } else
        {
            for (server in rankGrant.verifyGrantScope().servers)
            {
                desc.add(Chat.format("&7- &a$server"))
            }
        }
        desc.add(Chat.format("&6&m-------------------------------------"))
        desc.add(Chat.format("&eIssued By: &f" + AlchemistAPI.getRankDisplay(rankGrant.executor)))
        desc.add(Chat.format("&eIssued Reason: &f" + rankGrant.reason))
        desc.add(Chat.format("&6&m-------------------------------------"))
        if (!rankGrant.expirable.isActive())
        {
            if (rankGrant.removedBy == null)
            {
                desc.add(Chat.format("&eRemoved By: &fUnknown"))
            } else
            {
                desc.add(Chat.format("&eRemoved By: &f" + AlchemistAPI.getRankDisplay(rankGrant.removedBy!!)))
            }

            if (rankGrant.removedReason == null)
            {
                desc.add(Chat.format("&eRemoved Reason: &fUnknown"))
            } else
            {
                desc.add(Chat.format("&eRemoved Reason: &f" + rankGrant.removedReason!!))
            }
            desc.add(Chat.format("&6&m-------------------------------------"))
        }
        if (!player.hasPermission("alchemist.grants.remove") && rankGrant.getGrantable().weight >= ((AlchemistAPI.syncFindProfile(
                player.uniqueId
            )?.getCurrentRank()?.weight) ?: 0)
        )
        {
            desc.add(Chat.format("&cYou don't have permission to remove this grant"))
        } else if (player.hasPermission("alchemist.grants.remove"))
        {
            desc.add(Chat.format("&aRight-Click to remove this grant from &r" + AlchemistAPI.getRankDisplay(rankGrant.target)))
        }

        if (player.hasPermission("alchemist.grants.scopes.audit"))
        {
            desc.add(Chat.format("&aLeft-Click to edit the scopes of this grant"))
        }
        desc.add(Chat.format("&6&m-------------------------------------"))


        return desc
    }

    override fun getGrantsDisplayName(player: Player, rankGrant: RankGrant): String
    {
        return Chat.format(
            (if (rankGrant.expirable.isActive()) "&a&l(Active) &a+ " else "&c&l(Inactive) &c") + Date(
                rankGrant.expirable.addedAt
            )
        )
    }

    override fun getGrantsData(player: Player, rankGrant: RankGrant): Short
    {
        if (rankGrant.expirable.isActive())
        {
            return DyeColor.GREEN.woolData.toShort()
        }

        if (!rankGrant.expirable.isActive())
        {
            if (rankGrant.expirable.duration != Long.MAX_VALUE && (rankGrant.removedReason != null && rankGrant.removedReason.equals(
                    "Expired",
                    ignoreCase = true
                ))
            )
            {
                return DyeColor.GRAY.woolData.toShort()
            }
        }

        return DyeColor.RED.woolData.toShort()
    }


    override fun getGrantLore(player: Player, gameProfile: GameProfile, rank: Rank): MutableList<String>
    {
        val desc = arrayListOf<String>()

        desc.add(Chat.format("&6&m-----------------------------"))
        desc.add(Chat.format("&ePriority: &6${rank.weight}"))
        desc.add(Chat.format("&ePrefix:  ${rank.prefix}"))
        desc.add(Chat.format("&eColor: ${rank.color}This"))
        desc.add(Chat.format("&eDefault: &6${rank.default}"))
        desc.add(Chat.format("&eStaff Rank: &6${rank.staff}"))
        desc.add(Chat.format("&6&m-----------------------------"))
        desc.add(Chat.format("&6Scopes"))
        if (rank.getRankScope().global)
        {
            desc.add(Chat.format("&7- &aglobal"))
        } else
        {
            for (server in rank.getRankScope().servers)
            {
                desc.add(Chat.format("&7- &a$server"))
            }
        }
        desc.add(Chat.format("&6&m-----------------------------"))
        desc.add(
            Chat.format(
                "&a&lLeft click to grant ${rank.color}${rank.displayName} &a&lto ${
                    AlchemistAPI.getRankDisplay(
                        gameProfile.uuid
                    )
                }"
            )
        )
        desc.add(Chat.format("&6&m-----------------------------"))


        return desc
    }

    override fun getGrantDisplayName(player: Player, rank: Rank): String
    {
        return Chat.format("${rank.color}${rank.displayName}")
    }

    override fun getGrantData(player: Player, rank: Rank): Short
    {
        if (rank.woolColor != null)
        {
            return AlchemistAPI.getWoolColor(rank.woolColor!!).woolData.toShort()
        }

        return AlchemistAPI.getWoolColor(rank.color).woolData.toShort()
    }


    override fun getHistoryLore(player: Player, punishment: Punishment): MutableList<String>
    {
        val desc = arrayListOf<String>()
        if (!punishment.expirable.isActive())
        {
            desc.add(Chat.format("&c- ${Date(punishment.expirable.removedAt)}"))
        }
        desc.add(Chat.format("&8Known as #${punishment.easyFindId}"))
        desc.add(Chat.format("&6&m-------------------------------------"))
        desc.add(Chat.format("&eTarget: &r" + AlchemistAPI.getRankDisplay(punishment.target)))
        desc.add(Chat.format("&eDuration: &f" + TimeUtil.formatDuration(punishment.expirable.duration)))
        if (punishment.expirable.duration != Long.MAX_VALUE && punishment.expirable.isActive())
        {
            desc.add(Chat.format("&eRemaining: &f" + TimeUtil.formatDuration((punishment.expirable.addedAt + punishment.expirable.duration) - System.currentTimeMillis())))
        }
        desc.add(Chat.format("&6&m-------------------------------------"))
        desc.add(Chat.format("&eType: &f" + Chat.enumToDisplay(punishment.actor.actorType.name)))
        desc.add(Chat.format("&eExecuted From: &f" + Chat.enumToDisplay(punishment.actor.executor.name)))
        desc.add(Chat.format("&6&m-------------------------------------"))
        desc.add(Chat.format("&eIssued By: &f" + AlchemistAPI.getRankDisplay(punishment.executor)))
        desc.add(Chat.format("&eIssued Reason: &f" + punishment.reason))
        desc.add(Chat.format("&6&m-------------------------------------"))
        if (!punishment.expirable.isActive())
        {
            desc.add(Chat.format("&eRemoved By: &f" + AlchemistAPI.getRankDisplay(punishment.removedBy!!)))
            desc.add(Chat.format("&eRemoved Reason: &f" + punishment.removedReason!!))
            desc.add(Chat.format("&6&m-------------------------------------"))
        }
        desc.add(Chat.format("&aLeft-Click to view Proof Menu"))
        desc.add(Chat.format("&6&m-------------------------------------"))
        return desc
    }

    override fun getHistoryDisplayName(player: Player, punishment: Punishment): String
    {
        return Chat.format(
            (if (punishment.expirable.isActive()) "&a&l(Active) &a+ " else "&c&l(Inactive) &c") + Date(
                punishment.expirable.addedAt
            ).toString()
        )
    }

    override fun getHistoryData(player: Player, punishment: Punishment): Short
    {
        return (if (punishment.expirable.isActive()) DyeColor.GREEN.woolData.toShort() else DyeColor.RED.woolData.toShort())
    }

    override fun getHistoryPlaceholderLore(
        player: Player,
        profile: GameProfile,
        punishment: PunishmentType
    ): MutableList<String>
    {
        val desc = arrayListOf<String>()
        val punishments = profile.getPunishments(punishment)
        desc.addAll(
            listOf(
                "&6Viewing statistics for the",
                "&f${punishment.niceName} &6category:",
                "",
                " &eTotal: &f${punishments.size}",
                " &aActive: &f${punishments.filter { it.expirable.isActive() }.size}",
                " &cInactive: &f${
                    punishments.filter { p ->
                        !p.expirable.isActive()
                    }.size
                }",
                "",
                "&a&lClick to view more!"
            )
        )
        return desc
    }

    override fun getHistoryPlaceholderName(player: Player, profile: GameProfile, punishment: PunishmentType): String
    {
        return Chat.format(punishment.color + ChatColor.BOLD + punishment.niceName) + "s"
    }

    override fun getHistoryPlaceholderData(player: Player, profile: GameProfile, punishment: PunishmentType): Short
    {
        return AlchemistAPI.getWoolColor(punishment.color).woolData.toShort()
    }
}
package ltd.matrixstudios.alchemist.themes.types

import ltd.matrixstudios.alchemist.AlchemistSpigotPlugin
import ltd.matrixstudios.alchemist.api.AlchemistAPI
import ltd.matrixstudios.alchemist.models.grant.types.Punishment
import ltd.matrixstudios.alchemist.models.grant.types.RankGrant
import ltd.matrixstudios.alchemist.models.profile.GameProfile
import ltd.matrixstudios.alchemist.models.ranks.Rank
import ltd.matrixstudios.alchemist.punishments.PunishmentType
import ltd.matrixstudios.alchemist.themes.Theme
import ltd.matrixstudios.alchemist.util.Chat
import ltd.matrixstudios.alchemist.util.TimeUtil
import org.bukkit.Material
import org.bukkit.entity.Player
import java.util.Date

class Custom : Theme(
    "custom",
    "&eCustom",
    mutableListOf(
        "&7Use theme messages as set in the config."
    ),
    Material.SLIME_BALL,
    0
) {

    override fun getGrantsLore(player: Player, rankGrant: RankGrant): MutableList<String> {
        val key: String
        if (rankGrant.expirable.isActive()) {
            if (rankGrant.duration == Long.MAX_VALUE) {
                key = "custom-theme.grants.lore.active.permanent"
            } else {
                key = "custom-theme.grants.lore.active.temporary"
            }
        } else {
            if (rankGrant.expirable.expired && rankGrant.removedReason!! == "Expired") {
                if (rankGrant.duration == Long.MAX_VALUE) {
                    key = "custom-theme.grants.lore.expired.permanent"
                } else {
                    key = "custom-theme.grants.lore.expired.temporary"
                }
            } else {
                if (rankGrant.duration == Long.MAX_VALUE) {
                    key = "custom-theme.grants.lore.inactive.permanent"
                } else {
                    key = "custom-theme.grants.lore.inactive.temporary"
                }
            }
        }
        val scope = rankGrant.verifyGrantScope()
        val servers = mutableListOf<String>()
        val lore = AlchemistSpigotPlugin.instance.config.getStringList(key)
        if (scope.global) {
            servers.add("global")
        } else {
            servers.addAll(scope.servers)
        }
        for (i in 0 until lore.size) {
            lore[i] = Chat.format(lore[i]
                .replace("<date>", TimeUtil.dateToString(Date(rankGrant.expirable.addedAt)))
                .replace("<expires_at>", TimeUtil.dateToString(Date(rankGrant.expirable.getActiveUntil())))
                .replace("<removal_date>", TimeUtil.dateToString(Date(rankGrant.expirable.removedAt)))
                .replace("<target>", AlchemistAPI.getRankDisplay(rankGrant.target))
                .replace("<rank>", rankGrant.getGrantable().displayName)
                .replace("<scopes>", servers
                    .map {
                        AlchemistSpigotPlugin.instance.config.getString(
                            "custom-theme.grants.lore.scope-display"
                        ).replace(
                            "<scope>",
                            it
                        )
                    }
                    .joinToString("\n"))
                .replace("<issued_by>", AlchemistAPI.getRankDisplay(rankGrant.executor))
                .replace("<issued_reason>", rankGrant.addedReason)
                .replace("<duration>", TimeUtil.formatDuration(rankGrant.duration))
                .replace(
                    "<removed_by>",
                    if (rankGrant.removedBy == null) "Unknown" else AlchemistAPI.getRankDisplay(rankGrant.removedBy!!)
                )
                .replace(
                    "<removal_reason>",
                    if (rankGrant.removedReason == null) "Unknown" else rankGrant.removedReason!!
                )
            )
        }
        return lore
    }

    override fun getGrantsDisplayName(player: Player, rankGrant: RankGrant): String {
        val key: String
        if (rankGrant.expirable.isActive()) {
            key = "custom-theme.grants.active-item.name"
        } else {
            if (rankGrant.expirable.expired && rankGrant.removedReason!! == "Expired") {
                key = "custom-theme.grants.expired-item.name"
            } else {
                key = "custom-theme.grants.inactive-item.name"
            }
        }
        return Chat.format(AlchemistSpigotPlugin.instance.config.getString(key)
            .replace("<issued_date>", TimeUtil.dateToString(Date(rankGrant.expirable.addedAt)))
        )
    }

    override fun getGrantsData(player: Player, rankGrant: RankGrant): Short {
        val key: String
        if (rankGrant.expirable.isActive()) {
            key = "custom-theme.grants.active-item.data"
        } else {
            if (rankGrant.expirable.expired && rankGrant.removedReason!! == "Expired") {
                key = "custom-theme.grants.expired-item.data"
            } else {
                key = "custom-theme.grants.inactive-item.data"
            }
        }
        return AlchemistSpigotPlugin.instance.config.getInt(key).toShort()
    }

    override fun getGrantLore(player: Player, gameProfile: GameProfile, rank: Rank): MutableList<String> {
        val lore = mutableListOf<String>()
        for(i in 0 until lore.size) {
            lore[i] = Chat.format(lore[i])
        }
        return lore
    }

    override fun getGrantDisplayName(player: Player, rank: Rank): String {
        return ""
    }

    override fun getGrantData(player: Player, rank: Rank): Short {
        return 0
    }

    override fun getHistoryLore(player: Player, punishment: Punishment): MutableList<String> {
        return mutableListOf("Not implemented yet")
    }

    override fun getHistoryDisplayName(player: Player, punishment: Punishment): String {
        return "Not yet implemented"
    }

    override fun getHistoryData(player: Player, punishment: Punishment): Short {
        return 0
    }

    override fun getHistoryPlaceholderLore(
        player: Player,
        profile: GameProfile,
        punishment: PunishmentType
    ): MutableList<String> {
        return mutableListOf("Not yet implemented")
    }

    override fun getHistoryPlaceholderName(player: Player, profile: GameProfile, punishment: PunishmentType): String {
        return "Not yet implemented"
    }

    override fun getHistoryPlaceholderData(player: Player, profile: GameProfile, punishment: PunishmentType): Short {
        return 0
    }

}
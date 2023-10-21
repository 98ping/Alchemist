package ltd.matrixstudios.alchemist.placeholder

import ltd.matrixstudios.alchemist.models.ranks.Rank
import ltd.matrixstudios.alchemist.service.profiles.ProfileGameService
import ltd.matrixstudios.alchemist.service.ranks.RankService
import ltd.matrixstudios.alchemist.staff.mode.StaffSuiteManager
import ltd.matrixstudios.alchemist.util.Chat
import ltd.matrixstudios.alchemist.util.TimeUtil
import me.clip.placeholderapi.expansion.PlaceholderExpansion
import org.bukkit.entity.Player

class AlchemistExpansion : PlaceholderExpansion()
{
    override fun getIdentifier(): String
    {
        return "alchemist"
    }

    override fun getAuthor(): String
    {
        return "Matrix Studios"
    }

    override fun getVersion(): String
    {
        return "1.0.0"
    }

    override fun onPlaceholderRequest(player: Player, params: String): String
    {
        val profile = ProfileGameService.byId(player.uniqueId) ?: return ""

        when (params)
        {
            "rankDisplay" ->
            {
                val rank = RankService.FALLBACK_RANK

                val disguiseProfile = profile.rankDisguiseAttribute

                if (disguiseProfile != null)
                {
                    val disguiseRank = RankService.byId(disguiseProfile.rank)

                    if (disguiseRank != null)
                    {
                        return Chat.format(disguiseRank.color + disguiseRank.displayName)
                    }
                }

                val curr = profile.getCurrentRank()

                if (curr != null)
                {
                    return Chat.format(curr.color + curr.displayName)
                }


                return Chat.format(rank.color + rank.displayName)
            }

            "vanishStatus" ->
            {
                if (player.hasMetadata("vanish")) return "&aYes"

                return "&cNo"
            }


            "modModeStatus" ->
            {
                if (StaffSuiteManager.isModMode(player)) return "&aYes"

                return "&cNo"
            }

            "nametagPrefix" ->
            {
                val rank: Rank

                if (profile.rankDisguiseAttribute != null && RankService.byId(profile.rankDisguiseAttribute!!.rank) != null)
                {
                    val disguiseRank = profile.rankDisguiseAttribute!!
                    rank = RankService.byId(disguiseRank.rank)!!
                } else
                {
                    rank = profile.getCurrentRank()
                }

                if (player.hasMetadata("vanish"))
                {
                    return Chat.format("&7[V] ")
                }


                return Chat.format(rank.color)
            }

            "rankExpiresAt" ->
            {
                val current = profile.getCurrentGrant()

                if (current.expirable.duration == Long.MAX_VALUE)
                {
                    return "Never"
                }

                return TimeUtil.formatDuration((current.expirable.addedAt + current.expirable.duration) - System.currentTimeMillis())
            }

            "rankPrefix" ->
            {
                var rank = RankService.FALLBACK_RANK

                if (profile.rankDisguiseAttribute != null && RankService.byId(profile.rankDisguiseAttribute!!.rank) != null)
                {
                    val disguiseRank = profile.rankDisguiseAttribute!!
                    rank = RankService.byId(disguiseRank.rank)!!
                } else
                {
                    rank = profile.getCurrentRank()
                }

                return Chat.format(rank.prefix)
            }

            "activeTag" ->
            {
                return Chat.format(profile.getActivePrefix()?.prefix ?: "")
            }
        }

        return ""
    }
}
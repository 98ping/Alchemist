package ltd.matrixstudios.alchemist.api

import ltd.matrixstudios.alchemist.AlchemistSpigotPlugin
import ltd.matrixstudios.alchemist.models.profile.GameProfile
import ltd.matrixstudios.alchemist.models.ranks.Rank
import ltd.matrixstudios.alchemist.profiles.permissions.AccessiblePermissionHandler
import ltd.matrixstudios.alchemist.service.profiles.ProfileGameService
import ltd.matrixstudios.alchemist.service.ranks.RankService
import org.bukkit.Bukkit
import org.bukkit.DyeColor
import org.bukkit.entity.Player
import java.util.*
import java.util.concurrent.CompletableFuture


object AlchemistAPI
{

    var SERVER_NAME = AlchemistSpigotPlugin.instance.config.getString("details.ip")
    var GENERIC_NAME = AlchemistSpigotPlugin.instance.config.getString("details.genericName")
    var SC_FORMAT = AlchemistSpigotPlugin.instance.config.getString("channels.staffChat")
    var AC_FORMAT = AlchemistSpigotPlugin.instance.config.getString("channels.adminChat")

    var CONSOLE_COLOR = AlchemistSpigotPlugin.instance.config.getString("consoleColor") ?: "&c&l"

    fun getRankDisplay(uuid: UUID): String
    {
        var finalString = "${CONSOLE_COLOR}Console"

        val profile = quickFindProfile(uuid).get()

        if (profile != null)
        {
            finalString = profile.getCurrentRank().color + profile.username
        }

        return finalString
    }


    fun getPlayerRankString(uuid: UUID): String
    {
        val current = RankService.FALLBACK_RANK

        val profile = syncFindProfile(uuid) ?: return (current.color + current.displayName)
        val rank = profile.getCurrentRank()

        return rank.color + rank.displayName
    }

    fun getRankWeight(uuid: UUID): Int
    {
        val profile = syncFindProfile(uuid) ?: return 1
        val currentRank = profile.getCurrentRank()

        return currentRank.weight
    }

    fun getRankWithPrefix(uuid: UUID): String
    {
        var finalString = "${CONSOLE_COLOR}Console"

        val profile = quickFindProfile(uuid).get()

        if (profile != null)
        {
            val rank = profile.getCurrentRank()
            finalString = rank.prefix + rank.color + profile.username
        }

        return finalString
    }

    fun quickFindProfile(uuid: UUID): CompletableFuture<GameProfile?>
    {
        return CompletableFuture.supplyAsync { ProfileGameService.byId(uuid) }
    }

    fun syncFindProfile(uuid: UUID): GameProfile?
    {
        return ProfileGameService.byId(uuid)
    }

    fun findRank(uuid: UUID): Rank
    {
        val profile = ProfileGameService.byId(uuid) ?: return RankService.FALLBACK_RANK

        return profile.getCurrentRank()
    }

    fun supplyColoredNames(): CompletableFuture<List<Player>>
    {
        return CompletableFuture.supplyAsync {
            Bukkit.getOnlinePlayers()
                .filter { !it.hasMetadata("vanish") }
                .sortedBy {
                    AccessiblePermissionHandler.findRankWeight(it)
                }.reversed()
        }
    }

    fun getWoolColor(color: String): DyeColor
    {
        return when {
            color.contains("&1") || color.contains("&9") -> DyeColor.BLUE
            color.contains("&2") -> DyeColor.GREEN
            color.contains("&3") -> DyeColor.CYAN
            color.contains("&4") || color.contains("&c") -> DyeColor.RED
            color.contains("&5") -> DyeColor.PURPLE
            color.contains("&6") -> DyeColor.ORANGE
            color.contains("&7") -> DyeColor.SILVER
            color.contains("&8") -> DyeColor.GRAY
            color.contains("&a") -> DyeColor.LIME
            color.contains("&b") -> DyeColor.LIGHT_BLUE
            color.contains("&d") -> DyeColor.PINK
            color.contains("&e") -> DyeColor.YELLOW
            else -> DyeColor.WHITE
        }
    }

    fun getWoolColorStrict(color: String): DyeColor?
    {
        return when {
            color.contains("&1") || color.contains("&9") -> DyeColor.BLUE
            color.contains("&2") -> DyeColor.GREEN
            color.contains("&3") -> DyeColor.CYAN
            color.contains("&4") || color.contains("&c") -> DyeColor.RED
            color.contains("&5") -> DyeColor.PURPLE
            color.contains("&6") -> DyeColor.ORANGE
            color.contains("&7") -> DyeColor.SILVER
            color.contains("&8") -> DyeColor.GRAY
            color.contains("&a") -> DyeColor.LIME
            color.contains("&b") -> DyeColor.LIGHT_BLUE
            color.contains("&d") -> DyeColor.PINK
            color.contains("&e") -> DyeColor.YELLOW
            color.contains("&f") -> DyeColor.WHITE
            else -> null
        }
    }

}
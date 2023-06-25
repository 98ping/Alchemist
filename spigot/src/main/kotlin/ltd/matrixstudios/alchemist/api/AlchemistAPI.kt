package ltd.matrixstudios.alchemist.api

import ltd.matrixstudios.alchemist.AlchemistSpigotPlugin
import ltd.matrixstudios.alchemist.models.profile.GameProfile
import ltd.matrixstudios.alchemist.models.ranks.Rank
import ltd.matrixstudios.alchemist.service.profiles.ProfileGameService
import org.bukkit.Bukkit
import org.bukkit.DyeColor
import org.bukkit.entity.Player
import java.util.*
import java.util.concurrent.CompletableFuture


object AlchemistAPI {

    var SERVER_NAME = AlchemistSpigotPlugin.instance.config.getString("details.ip")
    var SC_FORMAT = AlchemistSpigotPlugin.instance.config.getString("channels.staffChat")
    var AC_FORMAT = AlchemistSpigotPlugin.instance.config.getString("channels.adminChat")

    fun getRankDisplay(uuid: UUID) : String {
        var finalString = "&cConsole"

        val profile = quickFindProfile(uuid).get()

        if (profile != null) {
            finalString = profile.getCurrentRank()!!.color + profile.username
        }

        return finalString
    }

    fun getRankWithPrefix(uuid: UUID) : String {
        var finalString = "&cConsole"

        val profile = quickFindProfile(uuid).get()

        if (profile != null) {
            val rank = profile.getCurrentRank()!!
            finalString = rank.prefix + rank.color + profile.username
        }

        return finalString
    }

    fun quickFindProfile(uuid: UUID) : CompletableFuture<GameProfile?> {
        return CompletableFuture.supplyAsync { ProfileGameService.byId(uuid) }
    }

    fun syncFindProfile(uuid: UUID) : GameProfile? {
        return ProfileGameService.byId(uuid)
    }

    fun findRank(uuid: UUID) : Rank {
        val profile = ProfileGameService.byId(uuid) ?: return Rank("unknown", "Unknown", "Unknown", 1, arrayListOf(), arrayListOf(), "&f", "&f")

        return profile.getCurrentRank()!!
    }

    fun supplyColoredNames() : CompletableFuture<List<Player>> {
        return CompletableFuture.supplyAsync {
            Bukkit.getOnlinePlayers()
                .sortedBy {
                        syncFindProfile(it.uniqueId)?.getCurrentRank()!!.weight
                }.reversed()
        }
    }

    fun getWoolColor(color: String): DyeColor {
        if (color.contains("&1")) return DyeColor.BLUE
        if (color.contains("&2")) return DyeColor.GREEN
        if (color.contains("&3")) return DyeColor.CYAN
        if (color.contains("&4")) return DyeColor.RED
        if (color.contains("&5")) return DyeColor.PURPLE
        if (color.contains("&6")) return DyeColor.ORANGE
        if (color.contains("&7")) return DyeColor.SILVER
        if (color.contains("&8")) return DyeColor.GREEN
        if (color.contains("&9")) return DyeColor.BLUE
        if (color.contains("&a")) return DyeColor.LIME
        if (color.contains("&b")) return DyeColor.LIGHT_BLUE
        if (color.contains("&c")) return DyeColor.RED
        if (color.contains("&d")) return DyeColor.PINK
        return if (color.contains("&e")) DyeColor.YELLOW else DyeColor.WHITE
    }
}
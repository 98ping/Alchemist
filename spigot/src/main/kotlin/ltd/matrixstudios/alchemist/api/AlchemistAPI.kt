package ltd.matrixstudios.alchemist.api

import ltd.matrixstudios.alchemist.models.profile.GameProfile
import ltd.matrixstudios.alchemist.models.tags.Tag
import ltd.matrixstudios.alchemist.service.profiles.ProfileGameService
import ltd.matrixstudios.alchemist.service.profiles.ProfileSearchService
import ltd.matrixstudios.alchemist.service.tags.TagService
import org.bukkit.Bukkit
import org.bukkit.DyeColor
import java.util.*
import java.util.concurrent.CompletableFuture
import java.util.concurrent.Executors
import java.util.stream.Collectors


object AlchemistAPI {

    fun getRankDisplay(uuid: UUID) : String {
        var finalString = "&cNot Found"

        quickFindProfile(uuid).thenApply {
            finalString = it!!.getCurrentRank()!!.prefix + it.username
        }

        return finalString


    }

    fun quickFindProfile(uuid: UUID) : CompletableFuture<GameProfile?> {
        return ProfileSearchService.getAsync(uuid)
    }


    fun supplyColoredNames() : CompletableFuture<String> {
        return CompletableFuture.supplyAsync {
            Bukkit.getOnlinePlayers()
                .sortedBy {
                        quickFindProfile(it.uniqueId).get()?.getCurrentRank()!!.weight
                }.reversed()
                .joinToString(", ") {
                    getRankDisplay(it.uniqueId)
                }
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
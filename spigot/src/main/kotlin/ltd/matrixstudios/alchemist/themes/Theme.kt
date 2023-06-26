package ltd.matrixstudios.alchemist.themes

import ltd.matrixstudios.alchemist.models.grant.types.Punishment
import ltd.matrixstudios.alchemist.models.grant.types.RankGrant
import ltd.matrixstudios.alchemist.models.profile.GameProfile
import ltd.matrixstudios.alchemist.models.ranks.Rank
import ltd.matrixstudios.alchemist.punishments.PunishmentType
import org.bukkit.Material
import org.bukkit.entity.Player

abstract class Theme(
    var id: String,
    var displayName: String,
    var lore: MutableList<String>,
    var material: Material,
    var data: Short
) {

    //Grants Menu
    abstract fun getGrantsLore(player: Player, rankGrant: RankGrant) : MutableList<String>
    abstract fun getGrantsDisplayName(player: Player, rankGrant: RankGrant) : String
    abstract fun getGrantsData(player: Player, rankGrant: RankGrant) : Short

    //Grant Menu
    abstract fun getGrantLore(player: Player, gameProfile: GameProfile, rank: Rank) : MutableList<String>
    abstract fun getGrantDisplayName(player: Player, rank: Rank) : String
    abstract fun getGrantData(player: Player, rank: Rank) : Short

    //Punishment History
    abstract fun getHistoryLore(player: Player, punishment: Punishment) : MutableList<String>
    abstract fun getHistoryDisplayName(player: Player, punishment: Punishment) : String
    abstract fun getHistoryData(player: Player, punishment: Punishment) : Short

    //History Placeholder Button
    abstract fun getHistoryPlaceholderLore(player: Player, profile: GameProfile, punishment: PunishmentType) : MutableList<String>
    abstract fun getHistoryPlaceholderName(player: Player, profile: GameProfile, punishment: PunishmentType) : String
    abstract fun getHistoryPlaceholderData(player: Player, profile: GameProfile, punishment: PunishmentType) : Short
}
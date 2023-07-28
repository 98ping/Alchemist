package ltd.matrixstudios.alchemist.commands.rank.menu.filter

/**
 * Class created on 6/28/2023

 * @author 98ping
 * @project Alchemist
 * @website https://solo.to/redis
 */
enum class RankListFilter(
    val displayName: String
) {
    DEFAULT("Default Ranks"), STAFF("Staff Ranks"), ALL("Every Rank"), HAS_PARENTS("Ranks That Have Parents")


}
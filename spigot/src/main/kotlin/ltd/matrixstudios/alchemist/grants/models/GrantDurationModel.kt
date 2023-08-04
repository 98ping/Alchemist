package ltd.matrixstudios.alchemist.grants.models

/**
 * Class created on 8/3/2023

 * @author 98ping
 * @project Alchemist
 * @website https://solo.to/redis
 */
data class GrantDurationModel(
    val item: String,
    val data: Int,
    val menuSlot: Int,
    val duration: String,
    val displayName: String
)
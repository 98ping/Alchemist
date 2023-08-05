package ltd.matrixstudios.alchemist.grants.models

/**
 * Class created on 8/4/2023

 * @author 98ping
 * @project Alchemist
 * @website https://solo.to/redis
 */
data class GrantReasonModel(
    var id: String,
    var item: String,
    var data: Int,
    var menuSlot: Int,
    var reason: String,
    var displayName: String
)
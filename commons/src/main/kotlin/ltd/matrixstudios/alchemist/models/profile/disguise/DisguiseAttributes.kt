package ltd.matrixstudios.alchemist.models.profile.disguise

/**
 * Class created on 6/4/2023

 * @author 98ping
 * @project Alchemist
 * @website https://solo.to/redis
 */
data class DisguiseAttributes(
    var texture: String,
    var signature: String,
    var fakeName: String,
    var rank: String,
    var crossServer: Boolean
) {
}
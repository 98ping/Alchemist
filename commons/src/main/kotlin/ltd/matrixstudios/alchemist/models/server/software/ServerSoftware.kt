package ltd.matrixstudios.alchemist.models.server.software

/**
 * Class created on 7/29/2023

 * @author 98ping
 * @project Alchemist
 * @website https://solo.to/redis
 */
class ServerSoftware(
    var serverVersion: String,
    val plugins: MutableList<ServerPlugin>
) {
}
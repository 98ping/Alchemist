package ltd.matrixstudios.alchemist.models.ranks.scope

import ltd.matrixstudios.alchemist.models.server.UniqueServer

/**
 * Class created on 7/31/2023

 * @author 98ping
 * @project Alchemist
 * @website https://solo.to/redis
 */
data class RankScope(
    var servers: MutableList<String>,
    var global: Boolean
) {

    fun appliesOn(server: UniqueServer) : Boolean = servers.contains(server.id)
}
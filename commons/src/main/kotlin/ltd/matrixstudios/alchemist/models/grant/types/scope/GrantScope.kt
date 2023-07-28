package ltd.matrixstudios.alchemist.models.grant.types.scope

import ltd.matrixstudios.alchemist.models.server.UniqueServer

/**
 * Class created on 6/20/2023

 * @author 98ping
 * @project Alchemist
 * @website https://solo.to/redis
 */
data class GrantScope(
    var scopeReason: String,
    var servers: MutableList<String>,
    var global: Boolean
) {

    fun appliesOn(server: UniqueServer) : Boolean = servers.contains(server.id)

}
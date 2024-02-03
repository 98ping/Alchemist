package ltd.matrixstudios.alchemist.packets

import ltd.matrixstudios.alchemist.AlchemistVelocity
import ltd.matrixstudios.alchemist.redis.RedisPacket
import ltd.matrixstudios.alchemist.service.profiles.ProfileGameService

class StaffMessagePacket(val message: String) : RedisPacket("staff-message-bungee") {

    override fun action() {
        AlchemistVelocity.instance.server.allPlayers.filter {
            ProfileGameService.byId(it.uniqueId)?.getHighestGlobalRank()!!.staff
        }.forEach {
            it.sendMessage(AlchemistVelocity.color(message))
        }
    }
}
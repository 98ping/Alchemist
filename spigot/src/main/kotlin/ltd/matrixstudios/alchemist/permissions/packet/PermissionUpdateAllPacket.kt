package ltd.matrixstudios.alchemist.permissions.packet

import ltd.matrixstudios.alchemist.api.AlchemistAPI
import ltd.matrixstudios.alchemist.permissions.AccessiblePermissionHandler
import ltd.matrixstudios.alchemist.redis.RedisPacket
import ltd.matrixstudios.alchemist.service.expirable.RankGrantService
import org.bukkit.Bukkit
import java.util.*

class PermissionUpdateAllPacket : RedisPacket("permission-update-all") {

    override fun action() {
        for (player in Bukkit.getOnlinePlayers()) {
            val gameProfile = AlchemistAPI.quickFindProfile(player.uniqueId).get() ?: continue

            RankGrantService.recalculatePlayer(gameProfile)
            AccessiblePermissionHandler.update(player, gameProfile.getPermissions())
        }
    }
}
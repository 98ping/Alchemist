package ltd.matrixstudios.alchemist.permissions.packet

import ltd.matrixstudios.alchemist.api.AlchemistAPI
import ltd.matrixstudios.alchemist.permissions.AccessiblePermissionHandler
import ltd.matrixstudios.alchemist.redis.RedisPacket
import org.bukkit.Bukkit
import java.util.*

class PermissionUpdateAllPacket : RedisPacket("permission-update-all") {

    override fun action() {
        for (player in Bukkit.getOnlinePlayers()) {
            val gameProfile = AlchemistAPI.quickFindProfile(player.uniqueId) ?: continue

            AccessiblePermissionHandler.update(player, gameProfile.getPermissions())
        }
    }
}
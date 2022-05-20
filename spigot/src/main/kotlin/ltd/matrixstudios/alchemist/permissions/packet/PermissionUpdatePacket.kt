package ltd.matrixstudios.alchemist.permissions.packet

import ltd.matrixstudios.alchemist.api.AlchemistAPI
import ltd.matrixstudios.alchemist.permissions.AccessiblePermissionHandler
import ltd.matrixstudios.alchemist.redis.RedisPacket
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import java.util.*

class PermissionUpdatePacket(var player: UUID) : RedisPacket("permission-update") {

    override fun action() {
        val gameProfile = AlchemistAPI.quickFindProfile(player) ?: return

        val player = Bukkit.getPlayer(player) ?: return

        AccessiblePermissionHandler.update(player, gameProfile.getPermissions())
     }
}
package ltd.matrixstudios.alchemist.profiles.permissions.packet

import ltd.matrixstudios.alchemist.api.AlchemistAPI
import ltd.matrixstudios.alchemist.profiles.permissions.AccessiblePermissionHandler
import ltd.matrixstudios.alchemist.redis.RedisPacket
import org.bukkit.Bukkit
import java.util.*

class PermissionUpdatePacket(var player: UUID) : RedisPacket("permission-update")
{

    override fun action()
    {
        AlchemistAPI.quickFindProfile(player).thenAcceptAsync {
            if (it == null) return@thenAcceptAsync

            val player = Bukkit.getPlayer(player) ?: return@thenAcceptAsync

            AccessiblePermissionHandler.update(player, it.getPermissions())
        }
    }
}
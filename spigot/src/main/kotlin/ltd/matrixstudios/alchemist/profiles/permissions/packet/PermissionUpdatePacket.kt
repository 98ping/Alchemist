package ltd.matrixstudios.alchemist.profiles.permissions.packet

import ltd.matrixstudios.alchemist.api.AlchemistAPI
import ltd.matrixstudios.alchemist.profiles.permissions.AccessiblePermissionHandler
import ltd.matrixstudios.alchemist.redis.RedisPacket
import ltd.matrixstudios.alchemist.service.expirable.RankGrantService
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import java.util.*

class PermissionUpdatePacket(var player: UUID) : RedisPacket("permission-update") {

    override fun action() {
        val gameProfile = AlchemistAPI.quickFindProfile(player).get() ?: return

        val player = Bukkit.getPlayer(player) ?: return

        val grants = RankGrantService.findByTarget(player.uniqueId).join()
        RankGrantService.playerGrants[player.uniqueId] = grants

        AccessiblePermissionHandler.update(player, gameProfile.getPermissions())
     }
}
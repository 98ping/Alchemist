package ltd.matrixstudios.alchemist.packets

import ltd.matrixstudios.alchemist.AlchemistSpigotPlugin
import ltd.matrixstudios.alchemist.models.grant.types.RankGrant
import ltd.matrixstudios.alchemist.models.ranks.Rank
import ltd.matrixstudios.alchemist.redis.RedisPacket
import ltd.matrixstudios.alchemist.util.Chat
import ltd.matrixstudios.alchemist.util.TimeUtil
import org.bukkit.Bukkit
import java.util.UUID

/**
 * Class created on 7/29/2023

 * @author 98ping
 * @project Alchemist
 * @website https://solo.to/redis
 */
class GrantMessageTargetPacket(val target: UUID, val rank: Rank, val duration: Long) : RedisPacket("grant-message-target") {
    override fun action() {
        val player = Bukkit.getPlayer(target)

        if (player != null && player.isOnline) {
            val message = AlchemistSpigotPlugin.instance.config.getString("grant-message")

            player.sendMessage(Chat.format(message
                .replace("<rank>", rank.color + rank.displayName)
                .replace("<duration>", TimeUtil.formatDuration(duration)))
            )
        }
    }
}
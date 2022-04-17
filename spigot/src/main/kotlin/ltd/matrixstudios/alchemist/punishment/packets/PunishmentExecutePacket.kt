package ltd.matrixstudios.alchemist.punishment.packets

import ltd.matrixstudios.alchemist.punishments.PunishmentType
import ltd.matrixstudios.alchemist.redis.RedisPacket
import ltd.matrixstudios.alchemist.util.Chat
import org.bukkit.Bukkit
import java.util.*

class PunishmentExecutePacket(
    var punishmentType: PunishmentType,
    var target: UUID,
    var reason: String
) : RedisPacket("punishment-execute") {

    override fun action() {
        val player = Bukkit.getPlayer(target)

        if (player != null) {
            if (punishmentType == PunishmentType.BLACKLIST) {
                player.kickPlayer(Chat.format("&cYou have been blacklisted from the network for $reason"))
            } else if (punishmentType == PunishmentType.BAN) {
                player.kickPlayer(Chat.format("&cYou have been banned from the network for $reason"))
            }

            if (punishmentType == PunishmentType.WARN) {
                player.sendMessage(Chat.format("&c&lYou have been warned for: &e&l$reason"))
            }

            if (punishmentType == PunishmentType.MUTE) {
                player.sendMessage(Chat.format("&c&lYou have been muted for: &e&l$reason"))
            }
        }

    }
}
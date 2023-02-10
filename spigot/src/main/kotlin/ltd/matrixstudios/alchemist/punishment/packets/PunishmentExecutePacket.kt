package ltd.matrixstudios.alchemist.punishment.packets

import ltd.matrixstudios.alchemist.AlchemistSpigotPlugin
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
                AlchemistSpigotPlugin.instance.config.getStringList("blacklist-message").map { it.replace("<reason>", reason)}.forEach { player.sendMessage(Chat.format(it)) }
            } else if (punishmentType == PunishmentType.BAN) {
                AlchemistSpigotPlugin.instance.config.getStringList("ban-message").map { it.replace("<reason>", reason)}.forEach { player.sendMessage(Chat.format(it)) }
            } else if (punishmentType == PunishmentType.MUTE) {
                AlchemistSpigotPlugin.instance.config.getStringList("mute-message").map { it.replace("<reason>", reason)}.forEach { player.sendMessage(Chat.format(it)) }
            } else if (punishmentType == PunishmentType.WARN) {
                AlchemistSpigotPlugin.instance.config.getStringList("warn-message").map { it.replace("<reason>", reason)}.forEach { player.sendMessage(Chat.format(it)) }
            }
        }
    }
}
package ltd.matrixstudios.alchemist.punishment.packets

import ltd.matrixstudios.alchemist.api.AlchemistAPI
import ltd.matrixstudios.alchemist.models.grant.types.Punishment
import ltd.matrixstudios.alchemist.punishments.PunishmentType
import ltd.matrixstudios.alchemist.redis.RedisPacket
import ltd.matrixstudios.alchemist.util.Chat
import org.bukkit.Bukkit
import java.util.*

class PunishmentDispatchPacket(
    var punishmentType: PunishmentType,
    var target: UUID,
    var executor: UUID,
    var silent: Boolean
) : RedisPacket(
    "dispatch-punishment"
) {

    override fun action() {
        val message = (if (silent) "&7[Silent]" else "") + " &r" +
                AlchemistAPI.getRankDisplay(executor) +
                " &ahas " +
                punishmentType.added +
                " &r" +
                AlchemistAPI.getRankDisplay(target) + "&a."

        Bukkit.getOnlinePlayers().filter { it.hasPermission("alchemist.positions.staff") }.forEach { it.sendMessage(Chat.format(message)) }
    }
}
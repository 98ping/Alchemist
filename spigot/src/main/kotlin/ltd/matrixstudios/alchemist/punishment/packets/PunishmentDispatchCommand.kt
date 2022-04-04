package ltd.matrixstudios.alchemist.punishment.packets

import ltd.matrixstudios.alchemist.api.AlchemistAPI
import ltd.matrixstudios.alchemist.models.grant.types.Punishment
import ltd.matrixstudios.alchemist.redis.RedisPacket
import ltd.matrixstudios.alchemist.util.Chat
import org.bukkit.Bukkit

class PunishmentDispatchCommand(
    var punishment: Punishment,
    var silent: Boolean
) : RedisPacket(
    "dispatch-punishment"
) {

    override fun action() {
        val message = (if (silent) "&7[Silent]" else "") + " &r" +
                AlchemistAPI.getRankDisplay(punishment.executor) +
                " &ahas " +
                punishment.getGrantable().added +
                " &r" +
                AlchemistAPI.getRankDisplay(punishment.target)

        Bukkit.getOnlinePlayers().filter { it.hasPermission("alchemist.positions.staff") }.forEach { it.sendMessage(Chat.format(message)) }
    }
}
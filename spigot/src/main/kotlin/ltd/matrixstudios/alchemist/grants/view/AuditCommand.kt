package ltd.matrixstudios.alchemist.grants.view

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandPermission
import ltd.matrixstudios.alchemist.util.Chat
import org.bukkit.entity.Player
import java.util.*

object AuditCommand : BaseCommand()
{

    var auditedPlayers = mutableListOf<UUID>()

    @CommandAlias("audits|auditlog|watchgrants|watchactivity|audit|watchpunishments")
    @CommandPermission("alchemist.audit")
    fun audit(player: Player)
    {
        if (!auditedPlayers.contains(player.uniqueId))
        {
            auditedPlayers.add(player.uniqueId)
            player.sendMessage(Chat.format("&aSubscribed to all audit messages"))
        } else if (auditedPlayers.contains(player.uniqueId))
        {
            auditedPlayers.remove(player.uniqueId)
            player.sendMessage(Chat.format("&cUnsubscribed from all audit messages"))
        }
    }
}
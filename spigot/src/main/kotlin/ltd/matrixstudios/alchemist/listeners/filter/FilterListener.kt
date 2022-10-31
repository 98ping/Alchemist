package ltd.matrixstudios.alchemist.listeners.filter

import ltd.matrixstudios.alchemist.AlchemistSpigotPlugin
import ltd.matrixstudios.alchemist.api.AlchemistAPI
import ltd.matrixstudios.alchemist.models.grant.types.Punishment
import ltd.matrixstudios.alchemist.punishment.BukkitPunishmentFunctions
import ltd.matrixstudios.alchemist.punishments.actor.ActorType
import ltd.matrixstudios.alchemist.punishments.actor.DefaultActor
import ltd.matrixstudios.alchemist.punishments.actor.executor.Executor
import ltd.matrixstudios.alchemist.redis.AsynchronousRedisSender
import ltd.matrixstudios.alchemist.service.filter.FilterService
import ltd.matrixstudios.alchemist.staff.packets.StaffAuditPacket
import ltd.matrixstudios.alchemist.staff.packets.StaffGeneralMessagePacket
import ltd.matrixstudios.alchemist.staff.packets.StaffMessagePacket
import ltd.matrixstudios.alchemist.util.Chat
import ltd.matrixstudios.alchemist.util.TimeUtil
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.TextComponent
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerChatEvent
import java.util.UUID

object FilterListener : Listener {

    @EventHandler
    fun chat(event: AsyncPlayerChatEvent) {
        val filter = FilterService.findInMessage(event.message)
        val player = event.player

        if (filter == null) return

        event.isCancelled = true

        if (filter.staffExempt)
        {
            val perm = filter.exemptPermission

            if (event.player.hasPermission(perm))
            {
                 return
            }
        }

        if (filter.shouldPunish)
        {
            val type = filter.punishmentType

            val punishment = Punishment(type.name
                , UUID.randomUUID().toString().substring(0, 4),
                mutableListOf(),
                player.uniqueId,
                UUID.fromString("00000000-0000-0000-0000-000000000000"),
                "Automated Filter Punishment",
                TimeUtil.parseTime(filter.duration) * 1000L,
                DefaultActor(Executor.CONSOLE, ActorType.GAME)
            )

            BukkitPunishmentFunctions.dispatch(punishment, true)
        }

        val uniqueServer = AlchemistSpigotPlugin.instance.globalServer

        AsynchronousRedisSender.send(StaffGeneralMessagePacket("&e[Filter] &7[${uniqueServer.displayName}] &c(${AlchemistAPI.getRankDisplay(player.uniqueId)} &e-> " + event.message + "&c)"))
    }
}
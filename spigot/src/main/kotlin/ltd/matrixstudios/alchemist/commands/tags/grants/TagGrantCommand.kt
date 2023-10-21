package ltd.matrixstudios.alchemist.commands.tags.grants

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandCompletion
import co.aikar.commands.annotation.CommandPermission
import co.aikar.commands.annotation.Name
import ltd.matrixstudios.alchemist.commands.tags.grants.menu.grant.TagGrantMenu
import ltd.matrixstudios.alchemist.models.grant.types.TagGrant
import ltd.matrixstudios.alchemist.models.profile.GameProfile
import ltd.matrixstudios.alchemist.packets.StaffAuditPacket
import ltd.matrixstudios.alchemist.punishment.BukkitPunishmentFunctions
import ltd.matrixstudios.alchemist.punishments.actor.ActorType
import ltd.matrixstudios.alchemist.punishments.actor.DefaultActor
import ltd.matrixstudios.alchemist.punishments.actor.executor.Executor
import ltd.matrixstudios.alchemist.redis.AsynchronousRedisSender
import ltd.matrixstudios.alchemist.service.expirable.TagGrantService
import ltd.matrixstudios.alchemist.service.tags.TagService
import ltd.matrixstudios.alchemist.util.Chat
import ltd.matrixstudios.alchemist.util.TimeUtil
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import java.util.*

class TagGrantCommand : BaseCommand()
{

    @CommandAlias("taggrant|prefixgrant")
    @CommandPermission("alchemist.tags.admin")
    @CommandCompletion("@gameprofile")
    fun tagGrant(player: Player, @Name("target") gameProfile: GameProfile)
    {
        TagGrantMenu(player, gameProfile).updateMenu()
    }

    @CommandAlias("manualtaggrant|manaulprefixgrant")
    @CommandPermission("alchemist.tags.admin")
    @CommandCompletion("@gameprofile")
    fun manual(
        sender: CommandSender,
        @Name("target") gameProfile: GameProfile,
        @Name("tag") tag: String,
        @Name("duration") duration: String,
        @Name("reason") reason: String
    )
    {
        val time: Long
        try
        {
            val parsed = TimeUtil.parseTime(duration)
            time = if (parsed == Long.MAX_VALUE.toInt())
            {
                Long.MAX_VALUE
            } else parsed * 1000L
        } catch (e: Exception)
        {
            sender.sendMessage(Chat.format("&cInvalid time!"))
            return
        }

        val found = TagService.byId(tag.lowercase(Locale.getDefault()))

        if (found == null)
        {
            sender.sendMessage(Chat.format("&cThis tag does not exist!"))
            return
        }


        val taggrant = TagGrant(
            found.id,
            gameProfile.uuid,
            BukkitPunishmentFunctions.getSenderUUID(sender),
            reason,
            time,
            DefaultActor(Executor.PLAYER, ActorType.GAME)
        )

        TagGrantService.save(taggrant)
        sender.sendMessage(
            Chat.format(
                "&aGranted &f" + gameProfile.username + " &athe " + found.menuName + " &atag"
            )
        )

        AsynchronousRedisSender.send(StaffAuditPacket("&b[Audit] &b" + gameProfile.username + " &3was granted the " + found.menuName + " &3prefix for &b" + reason))
    }
}
package ltd.matrixstudios.alchemist.commands.grants

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandCompletion
import co.aikar.commands.annotation.CommandPermission
import co.aikar.commands.annotation.Name
import ltd.matrixstudios.alchemist.AlchemistSpigotPlugin
import ltd.matrixstudios.alchemist.redis.cache.mutate.UpdateGrantCacheRequest
import ltd.matrixstudios.alchemist.models.grant.types.RankGrant
import ltd.matrixstudios.alchemist.models.grant.types.scope.GrantScope
import ltd.matrixstudios.alchemist.models.profile.GameProfile
import ltd.matrixstudios.alchemist.models.ranks.Rank
import ltd.matrixstudios.alchemist.profiles.permissions.packet.PermissionUpdatePacket
import ltd.matrixstudios.alchemist.punishment.BukkitPunishmentFunctions
import ltd.matrixstudios.alchemist.punishments.actor.ActorType
import ltd.matrixstudios.alchemist.punishments.actor.DefaultActor
import ltd.matrixstudios.alchemist.punishments.actor.executor.Executor
import ltd.matrixstudios.alchemist.redis.AsynchronousRedisSender
import ltd.matrixstudios.alchemist.service.expirable.RankGrantService
import ltd.matrixstudios.alchemist.packets.StaffAuditPacket
import ltd.matrixstudios.alchemist.util.Chat
import ltd.matrixstudios.alchemist.util.TimeUtil
import ltd.matrixstudios.alchemist.webhook.types.grants.GrantsNotification
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class CGrantCommand : BaseCommand() {

    @CommandAlias("cgrant")
    @CommandPermission("alchemist.grants.admin")
    @CommandCompletion("@gameprofile")
    fun ogrant(sender: CommandSender, @Name("target")gameProfile: GameProfile, @Name("rank")rank: Rank, @Name("duration")duration: String, @Name("scope")scope: GrantScope, @Name("reason")reason: String) {
        val rankGrant = RankGrant(
            rank.id,
            gameProfile.uuid,
            BukkitPunishmentFunctions.getSenderUUID(sender),
            reason, (if (duration == "perm") Long.MAX_VALUE else TimeUtil.parseTime(duration) * 1000L),

            DefaultActor(
                if (sender !is Player) Executor.CONSOLE else Executor.PLAYER,
                ActorType.GAME
            ),
            scope
        )

        RankGrantService.save(rankGrant)

        //give current grant a little bit of a buffer
        AlchemistSpigotPlugin.instance.server.scheduler.runTaskLater(AlchemistSpigotPlugin.instance, {
            AsynchronousRedisSender.send(PermissionUpdatePacket(gameProfile.uuid))
            AsynchronousRedisSender.send(UpdateGrantCacheRequest(gameProfile.uuid))
        }, 5L)

        AsynchronousRedisSender.send(StaffAuditPacket("&b[Audit] &b" + gameProfile.username + " &3was granted " + rank.color + rank.displayName + " &3for &b" + reason))
        GrantsNotification(rankGrant).send()

        sender.sendMessage(Chat.format("&aGranted " + gameProfile.username + " the rank "  + rank.color + rank.displayName))
    }
}
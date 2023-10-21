package ltd.matrixstudios.alchemist.punishment.commands.create

/**
 * Class created on 8/24/2023

 * @author 98ping
 * @project Alchemist
 * @website https://solo.to/redis
 */
import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.*
import ltd.matrixstudios.alchemist.Alchemist
import ltd.matrixstudios.alchemist.api.AlchemistAPI
import ltd.matrixstudios.alchemist.models.grant.types.Punishment
import ltd.matrixstudios.alchemist.models.profile.GameProfile
import ltd.matrixstudios.alchemist.packets.OwnershipMessagePacket
import ltd.matrixstudios.alchemist.punishment.BukkitPunishmentFunctions
import ltd.matrixstudios.alchemist.punishment.limitation.PunishmentLimitationUnderstander
import ltd.matrixstudios.alchemist.punishments.PunishmentType
import ltd.matrixstudios.alchemist.punishments.actor.ActorType
import ltd.matrixstudios.alchemist.punishments.actor.DefaultActor
import ltd.matrixstudios.alchemist.redis.AsynchronousRedisSender
import ltd.matrixstudios.alchemist.util.Chat
import ltd.matrixstudios.alchemist.util.TimeUtil
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import java.util.*

class TempGhostMuteCommand : BaseCommand()
{

    @CommandAlias("tempghostmute|tempgmute")
    @CommandPermission("alchemist.punishments.tempghostmute")
    @CommandCompletion("@gameprofile")
    @Syntax("<target> <duration> [-a] <reason>")
    fun tempGhostMute(
        sender: CommandSender,
        @Name("target") gameProfile: GameProfile,
        @Name("duration") duration: String,
        @Name("reason") reason: String
    )
    {
        val punishment = Punishment(
            PunishmentType.GHOST_MUTE.name,
            UUID.randomUUID().toString().substring(0, 4),
            mutableListOf(),
            gameProfile.uuid,
            BukkitPunishmentFunctions.getSenderUUID(sender),
            BukkitPunishmentFunctions.parseReason(reason, "Unspecified"), TimeUtil.parseTime(duration).toLong() * 1000L,

            DefaultActor(
                BukkitPunishmentFunctions.getExecutorFromSender(sender),
                ActorType.GAME
            )

        )

        val hasPunishment = gameProfile.hasActivePunishment(PunishmentType.GHOST_MUTE)

        if (hasPunishment)
        {
            sender.sendMessage(Chat.format("&cPlayer is already ghost muted!"))
            return
        }

        if (sender is Player)
        {

            val profile = AlchemistAPI.syncFindProfile(sender.uniqueId)!!
            val canExecute =
                PunishmentLimitationUnderstander.canApplyPunishment(sender.uniqueId)

            if (!canExecute)
            {
                sender.sendMessage(Chat.format("&cYou are currently on punishment cooldown."))
                sender.sendMessage(
                    Chat.format(
                        "&cPlease wait &e" + PunishmentLimitationUnderstander.getDurationString(
                            sender.uniqueId
                        )
                    )
                )

                return
            }

            if (!BukkitPunishmentFunctions.playerCanPunishOther(profile, gameProfile))
            {
                sender.sendMessage(Chat.format("&cYou are not eligible to punish this player!"))
                AsynchronousRedisSender.send(OwnershipMessagePacket("&b[S] &3[${Alchemist.globalServer.displayName}] ${profile.getRankDisplay()} &3tried punishing a player with a rank weight higher than theirs"))
                return
            }

            PunishmentLimitationUnderstander.equipCooldown(sender.uniqueId)
        }

        sender.sendMessage(
            Chat.format(
                (if (BukkitPunishmentFunctions.isSilent(reason)) "&7(Silent) " else "")
                        + "&aYou've temporarily ghost muted " + gameProfile.username + " for &f"
                        + BukkitPunishmentFunctions.parseReason(reason) + " &afor "
                        + TimeUtil.formatDuration(punishment.expirable.duration)
            )
        )
        sender.sendMessage(Chat.format("&7This type of mute does not send a dispatch message to the player"))
        BukkitPunishmentFunctions.dispatch(punishment, BukkitPunishmentFunctions.isSilent(reason))

    }
}
package ltd.matrixstudios.alchemist.commands.punishments.create

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.*
import co.aikar.commands.annotation.Optional
import ltd.matrixstudios.alchemist.models.grant.types.Punishment
import ltd.matrixstudios.alchemist.models.profile.GameProfile
import ltd.matrixstudios.alchemist.punishment.BukkitPunishmentFunctions
import ltd.matrixstudios.alchemist.punishments.PunishmentType
import ltd.matrixstudios.alchemist.punishments.actor.ActorType
import ltd.matrixstudios.alchemist.punishments.actor.DefaultActor
import ltd.matrixstudios.alchemist.punishments.actor.executor.Executor
import ltd.matrixstudios.alchemist.util.TimeUtil
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import java.util.*

class TempBanCommand : BaseCommand() {

    @CommandAlias("tempban|tb")
    @CommandPermission("alchemist.punishments.tempban")
    @CommandCompletion("@gameprofile")
    fun ban(sender: CommandSender, @Name("target") gameProfile: GameProfile, @Name("duration")time: String, @Name("reason") reason: String) {
        val punishment = Punishment(
            PunishmentType.BAN.name,
            UUID.randomUUID().toString().substring(0, 4),
            gameProfile.uuid,
            BukkitPunishmentFunctions.getSenderUUID(sender),
            reason, TimeUtil.parseTime(time) * 1000L,

            DefaultActor(
                BukkitPunishmentFunctions.getExecutorFromSender(sender),
                ActorType.GAME)

        )

        BukkitPunishmentFunctions.dispatch(punishment, true)

    }

}
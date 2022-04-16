package ltd.matrixstudios.alchemist.commands.punishments.create

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.*
import ltd.matrixstudios.alchemist.models.grant.types.Punishment
import ltd.matrixstudios.alchemist.models.profile.GameProfile
import ltd.matrixstudios.alchemist.punishment.BukkitPunishmentFunctions
import ltd.matrixstudios.alchemist.punishments.PunishmentType
import ltd.matrixstudios.alchemist.punishments.actor.ActorType
import ltd.matrixstudios.alchemist.punishments.actor.DefaultActor
import ltd.matrixstudios.alchemist.util.TimeUtil
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender

class TempMuteCommand : BaseCommand() {

    @CommandAlias("tempmute|tmute")
    @CommandPermission("alchemist.punishments.tempmute")
    fun ban(sender: CommandSender, @Name("target") gameProfile: GameProfile, @Name("duration")duration: String, @Name("reason") reason: String) {
        val punishment = Punishment(
            PunishmentType.MUTE.name,
            gameProfile.uuid,
            BukkitPunishmentFunctions.getSenderUUID(sender),
            reason, TimeUtil.parseTime(duration),

            DefaultActor(
                BukkitPunishmentFunctions.getExecutorFromSender(sender),
                Bukkit.getServerName(),
                ActorType.GAME)

        )

        BukkitPunishmentFunctions.dispatch(punishment, true)

    }

}
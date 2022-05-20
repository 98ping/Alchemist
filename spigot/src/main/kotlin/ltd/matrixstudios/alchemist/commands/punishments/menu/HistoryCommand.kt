package ltd.matrixstudios.alchemist.commands.punishments.menu

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.*
import ltd.matrixstudios.alchemist.models.profile.GameProfile
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class HistoryCommand : BaseCommand() {

    @CommandAlias("c|history|checkpunishments")
    @CommandPermission("alchemist.punishments.check")
    @CommandCompletion("@gameprofile")
    fun ban(sender: Player, @Name("target") gameProfile: GameProfile) {
        HistoryMenu(gameProfile, sender).openMenu()
    }
}
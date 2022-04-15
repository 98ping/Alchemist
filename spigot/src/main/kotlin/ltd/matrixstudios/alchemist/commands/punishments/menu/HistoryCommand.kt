package ltd.matrixstudios.alchemist.commands.punishments.menu

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandPermission
import co.aikar.commands.annotation.Name
import co.aikar.commands.annotation.Optional
import ltd.matrixstudios.alchemist.models.profile.GameProfile
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class HistoryCommand : BaseCommand() {

    @CommandAlias("c|history|checkpunishments")
    @CommandPermission("alchemist.punishments.check")
    fun ban(sender: Player, @Name("target") gameProfile: GameProfile) {
        HistoryMenu(gameProfile, sender).openMenu()
    }
}
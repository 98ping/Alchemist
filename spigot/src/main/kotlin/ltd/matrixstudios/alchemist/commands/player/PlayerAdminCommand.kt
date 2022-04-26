package ltd.matrixstudios.alchemist.commands.player

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandPermission
import co.aikar.commands.annotation.Name
import co.aikar.commands.annotation.Subcommand
import ltd.matrixstudios.alchemist.api.AlchemistAPI
import ltd.matrixstudios.alchemist.models.profile.GameProfile
import ltd.matrixstudios.alchemist.util.Chat
import org.bukkit.command.CommandSender

@CommandAlias("useradmin|user|player|playeradmin")
class PlayerAdminCommand : BaseCommand() {

    @Subcommand("info")
    @CommandPermission("alchemist.profiles.admin")
    fun info(sender: CommandSender, @Name("target")gameProfile: GameProfile) {
        sender.sendMessage(Chat.format("&7&m--------------------------"))
        sender.sendMessage(AlchemistAPI.getRankDisplay(gameProfile.uuid))
        sender.sendMessage(" ")
        sender.sendMessage(Chat.format("&eHighest Rank: &c" + gameProfile.getCurrentRank()!!.color + gameProfile.getCurrentRank()!!.displayName))
        sender.sendMessage(Chat.format("&eFriends: &c" + gameProfile.friends.size))
        sender.sendMessage(Chat.format("&ePunishments: &c" + gameProfile.getPunishments().size))
        if (gameProfile.hasActivePrefix()) {
            sender.sendMessage(Chat.format("&eActive Prefix: &c" + gameProfile.getActivePrefix()!!.menuName))
        }
        sender.sendMessage(Chat.format("&7&m--------------------------"))
    }
}
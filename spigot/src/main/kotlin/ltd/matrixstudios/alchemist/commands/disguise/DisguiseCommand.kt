package ltd.matrixstudios.alchemist.commands.disguise


import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandPermission
import co.aikar.commands.annotation.Default
import ltd.matrixstudios.alchemist.profiles.getProfile
import ltd.matrixstudios.alchemist.util.Chat
import net.pinger.disguise.DisguiseAPI
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

object DisguiseCommand : BaseCommand() {

    @CommandAlias("reveal|realname|disguiseinfo")
    @CommandPermission("alchemist.disguise.reveal")
    fun reveal(sender: CommandSender, target: Player) {
        sender.sendMessage(" ")
        sender.sendMessage(Chat.format("&ePlayers that disguised as &6${target.name}&e:"))
    }

    @CommandAlias("undisguise|unnick")
    @CommandPermission("alchemist.disguise")
    fun unDisguise(player: Player) {

    }

    @CommandAlias("disguise|dis|nick|nickname")
    @CommandPermission("alchemist.disguise")
    fun disguise(player: Player, @Default("") name: String?) {
        if ((name ?: "").isEmpty() || name?.isNotEmpty() == true) {
            // menu
            return
        }
    }
}
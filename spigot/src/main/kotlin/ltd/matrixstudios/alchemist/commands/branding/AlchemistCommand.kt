package ltd.matrixstudios.alchemist.commands.branding

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import ltd.matrixstudios.alchemist.util.Chat
import org.bukkit.entity.Player

class AlchemistCommand : BaseCommand() {

    @CommandAlias("alchemist")
    fun alchemist(player: Player) {
        player.sendMessage(Chat.format("&7&m----------------------------------------"))
        player.sendMessage(Chat.format("&6&lAlchemist Rank Core"))
        player.sendMessage(Chat.format(" "))
        player.sendMessage(Chat.format("&eMade By&7: &f98ping"))
        player.sendMessage(Chat.format("&eGitHub&7: &fhttps://github.com/98ping/Alchemist"))
        player.sendMessage(Chat.format("&7&m----------------------------------------"))
    }
}
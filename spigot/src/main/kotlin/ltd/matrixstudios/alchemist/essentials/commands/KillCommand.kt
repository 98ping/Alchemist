package ltd.matrixstudios.alchemist.essentials.commands

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandPermission
import co.aikar.commands.annotation.Name
import co.aikar.commands.annotation.Optional
import co.aikar.commands.bukkit.contexts.OnlinePlayer
import ltd.matrixstudios.alchemist.util.Chat
import org.bukkit.entity.Player

class KillCommand : BaseCommand()
{

    @CommandAlias("kill")
    @CommandPermission("alchemist.essentials.kill")
    fun kill(player: Player, @Name("target") @Optional target: OnlinePlayer?)
    {
        if (target != null)
        {
            target.player.health = 0.0
            target.player.sendMessage(Chat.format("&6You have been &fkilled!"))
            player.sendMessage(Chat.format("&6You have killed &r" + target.player.displayName))

            return
        }

        player.health = 0.0
        player.sendMessage(Chat.format("&6You have been &fkilled!"))
    }
}
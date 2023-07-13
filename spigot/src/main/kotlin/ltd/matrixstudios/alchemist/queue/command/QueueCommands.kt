package ltd.matrixstudios.alchemist.queue.command

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandPermission
import co.aikar.commands.annotation.Subcommand
import ltd.matrixstudios.alchemist.queue.command.menu.QueueEditorMenu
import org.bukkit.entity.Player

/**
 * Class created on 7/12/2023

 * @author 98ping
 * @project Alchemist
 * @website https://solo.to/redis
 */
@CommandAlias("queue")
@CommandPermission("alchemist.queues.admin")
class QueueCommands : BaseCommand() {

    @Subcommand("editor")
    fun editor(player: Player) {
        QueueEditorMenu(player).updateMenu()
    }
}
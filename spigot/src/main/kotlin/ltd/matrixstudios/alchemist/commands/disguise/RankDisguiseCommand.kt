package ltd.matrixstudios.alchemist.commands.disguise

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandPermission
import ltd.matrixstudios.alchemist.commands.disguise.menu.RankDisguiseMenu
import org.bukkit.entity.Player
import java.util.*
import java.util.concurrent.CompletableFuture


class RankDisguiseCommand : BaseCommand() {

    @CommandAlias("rankdisguise")
    @CommandPermission("alchemist.disguise.rank")
    fun rankDisguise(player: Player) {
        RankDisguiseMenu(player).updateMenu()
    }
}
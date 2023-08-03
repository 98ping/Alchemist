package ltd.matrixstudios.alchemist.commands.grants.configure

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandPermission
import ltd.matrixstudios.alchemist.commands.grants.configure.menu.GrantConfigCategory
import ltd.matrixstudios.alchemist.commands.grants.configure.menu.GrantConfigureMenu
import org.bukkit.entity.Player

/**
 * Class created on 8/3/2023

 * @author 98ping
 * @project Alchemist
 * @website https://solo.to/redis
 */
class GrantConfigureCommand : BaseCommand() {

    @CommandAlias("configuregrants|grantconfig")
    @CommandPermission("alchemist.grants.config")
    fun grantConfig(player: Player) {
        GrantConfigureMenu(player, GrantConfigCategory.DURATIONS).updateMenu()
    }
}
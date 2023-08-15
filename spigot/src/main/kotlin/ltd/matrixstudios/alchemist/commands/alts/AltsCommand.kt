package ltd.matrixstudios.alchemist.commands.alts

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandCompletion
import co.aikar.commands.annotation.CommandPermission
import co.aikar.commands.annotation.Name
import co.aikar.commands.annotation.Subcommand
import ltd.matrixstudios.alchemist.AlchemistSpigotPlugin
import ltd.matrixstudios.alchemist.commands.alts.menu.AltsMenu
import ltd.matrixstudios.alchemist.models.profile.GameProfile
import ltd.matrixstudios.alchemist.service.profiles.ProfileGameService
import ltd.matrixstudios.alchemist.util.Chat
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitRunnable
import java.util.concurrent.ForkJoinPool

class AltsCommand : BaseCommand() {

    @CommandAlias("alts")
    @CommandPermission("alchemist.alts")
    @CommandCompletion("@gameprofile")
    fun listAll(player: Player, @Name("target") profile: GameProfile) =
        profile.getAltAccounts()
            .thenAccept { alts ->
                AltsMenu(player, profile, alts).updateMenu()
            }
}
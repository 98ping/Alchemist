package ltd.matrixstudios.alchemist.profiles.commands.player

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandCompletion
import co.aikar.commands.annotation.CommandPermission
import co.aikar.commands.annotation.Name
import ltd.matrixstudios.alchemist.models.profile.GameProfile
import ltd.matrixstudios.alchemist.service.expirable.RankGrantService
import ltd.matrixstudios.alchemist.util.Chat
import org.bukkit.command.CommandSender

/**
 * Class created on 6/13/2023

 * @author 98ping
 * @project Alchemist
 * @website https://solo.to/redis
 */
object WipeGrantsCommand : BaseCommand() {

    @CommandAlias("wipegrants")
    @CommandCompletion("@gameprofile")
    @CommandPermission("alchemist.profiles.admin")
    fun wipeGrants(sender: CommandSender, @Name("target") gameProfile: GameProfile)
    {
        val ms = System.currentTimeMillis()
        RankGrantService.findByTarget(gameProfile.uuid).thenAcceptAsync { collection ->
            for (found in collection) RankGrantService.remove(found)

            RankGrantService.recalculatePlayer(gameProfile).also {
                sender.sendMessage(Chat.format("&aYou manually deleted &f${collection.size} &agrants from this player in &f${System.currentTimeMillis().minus(ms)}ms"))
            }
        }
    }
}
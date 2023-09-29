package ltd.matrixstudios.alchemist.commands.party

import co.aikar.commands.BaseCommand
import co.aikar.commands.CommandHelp
import co.aikar.commands.ConditionFailedException
import co.aikar.commands.annotation.*
import ltd.matrixstudios.alchemist.api.AlchemistAPI
import ltd.matrixstudios.alchemist.models.party.Party
import ltd.matrixstudios.alchemist.models.party.PartyElevation
import ltd.matrixstudios.alchemist.redis.AsynchronousRedisSender
import ltd.matrixstudios.alchemist.packets.NetworkMessagePacket
import ltd.matrixstudios.alchemist.service.party.PartyService
import ltd.matrixstudios.alchemist.service.profiles.ProfileGameService
import ltd.matrixstudios.alchemist.util.Chat
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import java.util.*
import java.util.concurrent.CompletableFuture

@CommandAlias("p|party")
class PartyCommands : BaseCommand() {

    @Subcommand("create")
    fun create(player: Player) : CompletableFuture<Void>
    {
       return PartyService.getParty(player.uniqueId).thenAcceptAsync {
           if (it != null)
           {
               throw ConditionFailedException(
                   "You are currently in a party!"
               )
           }

           val toInsert = Party(
               UUID.randomUUID(),
               player.uniqueId,
               mutableListOf(),
               mutableMapOf(),
               System.currentTimeMillis(),
               true
           )

           PartyService.handler.insertSynchronously(
               toInsert.id, toInsert
           ).also {
               player.sendMessage(Chat.format("&aYou have just created a new &eparty&a!"))
               player.sendMessage(Chat.format("&7To see detailed information, execute /party info"))
           }
       }
    }

    @HelpCommand
    fun help(help: CommandHelp) {
        help.showHelp()
    }

}
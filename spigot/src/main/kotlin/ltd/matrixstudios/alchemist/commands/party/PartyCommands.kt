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
import ltd.matrixstudios.alchemist.profiles.AsyncGameProfile
import ltd.matrixstudios.alchemist.service.party.PartyService
import ltd.matrixstudios.alchemist.service.profiles.ProfileGameService
import ltd.matrixstudios.alchemist.util.Chat
import ltd.matrixstudios.alchemist.util.TimeUtil
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import java.util.*
import java.util.concurrent.CompletableFuture

@CommandAlias("p|party")
class PartyCommands : BaseCommand() {

    @Subcommand("create")
    fun create(player: Player) : CompletableFuture<Void>
    {
        return PartyService.getParty(player.uniqueId).thenAccept {
            if (it != null)
            {
                throw ConditionFailedException(
                    "You are already in a party!"
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

            PartyService.handler.asynchronouslyInsert(
                toInsert.id, toInsert
            )
            PartyService.backingPartyCache[toInsert.id] = toInsert

            player.sendMessage(Chat.format("&aYou have just created a new &eparty&a!"))
            player.sendMessage(Chat.format("&7To view detailed information, execute /party info"))
        }
    }

    @Subcommand("invite")
    fun onInvite(
        player: Player,
        @Name("target") target: AsyncGameProfile
    ) : CompletableFuture<Void>
    {
        return target.use(player) { gameProfile ->
            if (!gameProfile.isOnline())
            {
                throw ConditionFailedException(
                    Chat.format("&cThe user &e${gameProfile.username} &cis not currently on the network")
                )
            }

            val currentParty = PartyService.getParty(player.uniqueId).get()
                ?: throw ConditionFailedException(
                    "You are not currently in a party!"
                )

            if (currentParty.invited.containsKey(gameProfile.uuid) || currentParty.isMember(gameProfile.uuid))
            {
                throw ConditionFailedException(
                    "Unable to send invite. Player is either in your party or already invited."
                )
            }

            val hasEligibility = if (currentParty.isLeader(player.uniqueId))
                true
            else currentParty.members.firstOrNull {
                it.first == player.uniqueId && it.second == PartyElevation.OFFICER
            } != null

            if (!hasEligibility)
            {
                throw ConditionFailedException(
                    "You do not have the sufficient permissions to do this!"
                )
            }

            currentParty.invited[gameProfile.uuid] = System.currentTimeMillis()
            player.sendMessage(Chat.format("&aYou have just sent a party invitation to ${gameProfile.getRankDisplay()}"))

            with (PartyService.handler) {
                this.insert(currentParty.id, currentParty)
                PartyService.backingPartyCache[currentParty.id] = currentParty
            }
        }
    }

    @Subcommand("info")
    fun onInfo(player: Player) : CompletableFuture<Void>
    {
        return PartyService.getParty(player.uniqueId).thenAccept { party ->
            if (party == null)
            {
                throw ConditionFailedException(
                    "You are not currently in a party!"
                )
            }

            player.sendMessage(Chat.format("&aInformation for &eYour Party"))
            player.sendMessage(Chat.format("&7Created: &f${TimeUtil.formatDuration(System.currentTimeMillis().minus(party.createdAt))} ago"))
            player.sendMessage(Chat.format("&7Short Id: &f${party.id.toString().substring(0, 8)}"))
            player.sendMessage(" ")
            player.sendMessage(Chat.format("&7Leader: &f${AlchemistAPI.getRankDisplay(player.uniqueId)}"))
            player.sendMessage(Chat.format("&7Members: &f${party.getPartyMembersString()}"))
            player.sendMessage(Chat.format("&7Invited Members: &f${party.invited.size}"))
            player.sendMessage(" ")
        }
    }

    @HelpCommand
    fun help(help: CommandHelp) {
        help.showHelp()
    }

}
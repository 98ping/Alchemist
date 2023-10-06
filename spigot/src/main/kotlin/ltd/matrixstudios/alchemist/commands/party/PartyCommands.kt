package ltd.matrixstudios.alchemist.commands.party

import co.aikar.commands.BaseCommand
import co.aikar.commands.CommandHelp
import co.aikar.commands.ConditionFailedException
import co.aikar.commands.annotation.*
import ltd.matrixstudios.alchemist.Alchemist
import ltd.matrixstudios.alchemist.AlchemistSpigotPlugin
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
import org.jetbrains.annotations.Async
import java.util.*
import java.util.concurrent.CompletableFuture

@CommandAlias("party|p")
class PartyCommands : BaseCommand() {

    @Subcommand("create")
    @Description("Creates a new party.")
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
                mutableMapOf(),
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

    @Subcommand("disband")
    @Description("Disbands your current party. Only works if you are a leader.")
    fun onDisband(
        player: Player
    ) : CompletableFuture<Void>
    {
        return PartyService.getParty(player.uniqueId).thenAccept { party ->
            if (party == null)
            {
                throw ConditionFailedException(
                    "You are not currently in a party!"
                )
            }

            val leader = party.leader

            if (leader != player.uniqueId)
            {
                throw ConditionFailedException(
                    Chat.format("&cOnly the party &eLeader &cis able to disband the party.")
                )
            }

            PartyService.handler.deleteAsynchronously(
                party.id
            )
            PartyService.backingPartyCache.remove(party.id)
            player.sendMessage(Chat.format("&7[&dParties&7] &aYour current &eparty &ahas just been disbanded!"))
        }
    }

    @Subcommand("invite")
    @Description("Invites a given player to your party.")
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
            else currentParty.members.entries.firstOrNull {
                it.key == player.uniqueId && it.value == PartyElevation.OFFICER
            } != null

            if (!hasEligibility)
            {
                throw ConditionFailedException(
                    "You do not have the sufficient permissions to do this!"
                )
            }

            currentParty.invited[gameProfile.uuid] = System.currentTimeMillis()
            player.sendMessage(Chat.format("&7[&dParties&7] &aYou have just sent a party invitation to ${gameProfile.getRankDisplay()}"))

            with (PartyService.handler) {
                this.insert(currentParty.id, currentParty)
                PartyService.backingPartyCache[currentParty.id] = currentParty
            }
        }
    }

    @Subcommand("promote")
    @Description("Promotes a member of your party to a higher role.")
    fun onPromote(
        player: Player,
        @Name("target") target: AsyncGameProfile
    ) : CompletableFuture<Void>
    {
        return target.use(player) {
            val myParty = PartyService.getParty(player.uniqueId).get()
                ?: throw ConditionFailedException(
                    "You are not currently in a party"
                )

            val otherPlayerParty = PartyService.getParty(it.uuid).get()
                ?: throw ConditionFailedException(
                    Chat.format("&e${it.username} &cis not currently in a party!")
                )

            if (otherPlayerParty.leader != myParty.leader)
            {
                throw ConditionFailedException(
                    "This player must be in the same party as you to promote them!"
                )
            }

            val roleInThisParty = myParty.members[it.uuid]
                ?: throw ConditionFailedException(
                    "Unable to verify that this player is in your party!"
                )

            if (roleInThisParty == PartyElevation.OFFICER || roleInThisParty == PartyElevation.LEADER)
            {
                throw ConditionFailedException(
                    "This player has already been promoted to the highest rank!"
                )
            }

            myParty.members[it.uuid] = PartyElevation.OFFICER

            with (PartyService.handler) {
                this.insert(myParty.id, myParty)
                PartyService.backingPartyCache[myParty.id] = myParty
            }

            player.sendMessage(Chat.format("&7[&dParties&7] &aYou have just promoted ${it.getRankDisplay()} &ato an &eOfficer&a!"))
        }
    }

    @Subcommand("info")
    @Description("View detailed information about your party.")
    fun onInfo(player: Player) : CompletableFuture<Void>
    {
        return PartyService.getParty(player.uniqueId).thenAccept { party ->
            if (party == null)
            {
                throw ConditionFailedException(
                    "You are not currently in a party!"
                )
            }

            player.sendMessage(" ")
            player.sendMessage(Chat.format("&aInformation for &eYour Party"))
            player.sendMessage(" ")
            player.sendMessage(Chat.format("&7Created: &f${TimeUtil.formatDuration(System.currentTimeMillis().minus(party.createdAt))} ago"))
            player.sendMessage(Chat.format("&7Short Id: &f${party.id.toString().substring(0, 8)}"))
            player.sendMessage(" ")
            val leaderProfile = ProfileGameService.byId(party.leader) ?: return@thenAccept
            player.sendMessage(Chat.format("&7Leader: &f${leaderProfile.getRankDisplay()} ${if (leaderProfile.isOnline()) "&a■" else "&c■"}"))
            player.sendMessage(Chat.format("&7Members: &f${party.getPartyMembersString()}"))
            player.sendMessage(Chat.format("&7Invited Members: &f${party.invited.size}"))
            player.sendMessage(" ")
        }
    }

    @HelpCommand
    @Description("Displays the command help.")
    fun help(help: CommandHelp) {
        help.showHelp()
    }

}
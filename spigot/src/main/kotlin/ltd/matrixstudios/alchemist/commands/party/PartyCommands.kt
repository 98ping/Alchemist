package ltd.matrixstudios.alchemist.commands.party

import co.aikar.commands.BaseCommand
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

@CommandAlias("p|party")
class PartyCommands : BaseCommand() {

    @Subcommand("create")
    fun create(player: Player) {
        if (PartyService.getParty(player.uniqueId) != null) {
            player.sendMessage(Chat.format("&cYou are already in a party!"))
            return
        }

        val party = Party(UUID.randomUUID(), player.uniqueId, mutableListOf(Pair(player.uniqueId, PartyElevation.LEADER)), mutableMapOf(), System.currentTimeMillis(), true)
        PartyService.handler.storeAsync(party.id, party)

        player.sendMessage(Chat.format("&8[&dParties&8] &fYou have &acreated &fa party!"))
    }

    @HelpCommand
    fun help(sender: CommandSender) {
        sender.sendMessage(Chat.format("&7&m-------------------------"))
        sender.sendMessage(Chat.format("&6&lParty Help"))
        sender.sendMessage(" ")
        sender.sendMessage(Chat.format("&e/party create"))
        sender.sendMessage(Chat.format("&e/party invite <target>"))
        sender.sendMessage(Chat.format("&e/party disband"))
        sender.sendMessage(Chat.format("&e/party accept <target>"))
        sender.sendMessage(Chat.format("&e/party kick <target>"))
        sender.sendMessage(Chat.format("&e/party leave"))
        sender.sendMessage(Chat.format("&7&m-------------------------"))
    }

    @Subcommand("kick")
    fun kick(player: Player, @Name("target")target: String) {
        if (PartyService.getParty(player.uniqueId) == null) {
            player.sendMessage(Chat.format("&cYou are not in a party!"))
            return
        }

        val party = PartyService.getParty(player.uniqueId)!!

        val targetProfile = ProfileGameService.byUsername(target)

        if (targetProfile == null) {
            player.sendMessage(Chat.format("&cPlayer '$target' does not exist!"))
            return
        }

        if (PartyService.getParty(targetProfile.uuid) == null) {
            player.sendMessage(Chat.format("&cPlayer '$target' is not in a party!"))
            return
        }

        if (!party.isLeader(player.uniqueId) && party.members.firstOrNull { it.first.toString() == player.uniqueId.toString() && it.second == PartyElevation.OFFICER} == null) {
            player.sendMessage(Chat.format("&cYou must be a leader or an officer to do this!"))
            return
        }

        val targetParty = PartyService.getParty(targetProfile.uuid)

        if (targetParty!!.id.toString() != party.id.toString()) {
            player.sendMessage(Chat.format("&cThis player is not in your party!"))
            return
        }

        party.members.forEach {
            AsynchronousRedisSender.send(NetworkMessagePacket(it.first, Chat.format("&8[&dParties&8] ${AlchemistAPI.getRankDisplay(targetProfile.uuid)} &fhas been &ckicked &ffrom your party!")))
        }
        party.removeMember(targetProfile.uuid)
        PartyService.handler.storeAsync(party.id, party)
    }

    @Subcommand("invite")
    fun invite(player: Player, @Name("target")target: String) {
        if (PartyService.getParty(player.uniqueId) == null) {
            create(player)
        }

        val party = PartyService.getParty(player.uniqueId)!!

        val targetProfile = ProfileGameService.byUsername(target)

        if (targetProfile == null) {
            player.sendMessage(Chat.format("&cPlayer '$target' does not exist!"))
            return
        }

        if (!targetProfile.isOnline()) {
            player.sendMessage(Chat.format("&cPlayer '$target' is not online!"))
            return
        }

        if (PartyService.getParty(targetProfile.uuid) != null) {
            player.sendMessage(Chat.format("&cPlayer '$target' is already in a party!"))
            return
        }

        if (party.invited.containsKey(targetProfile.uuid)) {
            player.sendMessage(Chat.format("&cPlayer '$target' has already been invited to your party!"))
            return
        }

        if (!party.isLeader(player.uniqueId) && party.members.firstOrNull { it.first.toString() == player.uniqueId.toString() && it.second == PartyElevation.OFFICER} == null) {
            player.sendMessage(Chat.format("&cYou must be a leader or an officer to do this!"))
            return
        }

        party.invited[targetProfile.uuid] = System.currentTimeMillis()
        PartyService.handler.storeAsync(party.id, party)
        AsynchronousRedisSender.send(NetworkMessagePacket(targetProfile.uuid, Chat.format("&8[&dParties&8] &fYou have been invited to join &a${player.displayName}'s &fparty!")))
    }

    @Subcommand("disband")
    fun disband(player: Player) {
        if (PartyService.getParty(player.uniqueId) == null) {
            player.sendMessage(Chat.format("&cYou are not in a party!"))
            return
        }

        val partyByPlayer = PartyService.getParty(player.uniqueId)!!

        if (partyByPlayer.leader != player.uniqueId) {
            player.sendMessage(Chat.format("&cYou are not the leader of this party so you can't disband it!"))
            return
        }

        partyByPlayer.members.forEach {
            AsynchronousRedisSender.send(NetworkMessagePacket(it.first, Chat.format("&8[&dParties&8] &fYour party has been &cdisbanded")))
        }
        PartyService.handler.delete(partyByPlayer.id)
    }

    @Subcommand("leave")
    fun leave(player: Player) {
        if (PartyService.getParty(player.uniqueId) == null) {
            player.sendMessage(Chat.format("&cYou are not in a party!"))
            return
        }


        val party = PartyService.getParty(player.uniqueId)!!

        if (party.leader.toString() == player.uniqueId.toString()) {
            player.sendMessage(Chat.format("&cYou are the leader of this party, use /party disband instead!"))
            return
        }

        party.removeMember(player.uniqueId)
        player.sendMessage(Chat.format("&cYou have left your party!"))
        party.members.forEach {
            AsynchronousRedisSender.send(NetworkMessagePacket(it.first, Chat.format("&8[&dParties&8] ${AlchemistAPI.getRankDisplay(player.uniqueId)} &fhas &cleft &fyour party!")))
        }

        PartyService.handler.storeAsync(party.id, party)
    }

    @Subcommand("accept|join")
    fun accept(player: Player, @Name("target")target: String) {
        if (PartyService.getParty(player.uniqueId) != null) {
            player.sendMessage(Chat.format("&cYou are already in a party!"))
            return
        }

        val targetProfile = ProfileGameService.byUsername(target)

        if (PartyService.getParty(targetProfile!!.uuid) == null) {
            player.sendMessage(Chat.format("&cPlayer '$target' is not in a party!"))
            return
        }

        val party = PartyService.getParty(targetProfile.uuid)!!

        if (!party.invited.containsKey(player.uniqueId)) {
            player.sendMessage(Chat.format("&cYou have not been invited to join ${targetProfile.username}'s party!"))
            return
        }
    
        party.members.add(Pair(player.uniqueId, PartyElevation.MEMBER))
        party.members.forEach {
            AsynchronousRedisSender.send(NetworkMessagePacket(it.first, Chat.format("&8[&dParties&8] ${AlchemistAPI.getRankDisplay(player.uniqueId)} &fhas joined your party!")))
        }

        PartyService.handler.storeAsync(party.id, party)
    }

}
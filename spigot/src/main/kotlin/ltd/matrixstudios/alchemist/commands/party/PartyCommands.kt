package ltd.matrixstudios.alchemist.commands.party

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.*
import com.sun.xml.internal.ws.wsdl.writer.document.Part
import ltd.matrixstudios.alchemist.Alchemist
import ltd.matrixstudios.alchemist.AlchemistSpigotPlugin
import ltd.matrixstudios.alchemist.api.AlchemistAPI
import ltd.matrixstudios.alchemist.models.party.Party
import ltd.matrixstudios.alchemist.models.party.PartyElevation
import ltd.matrixstudios.alchemist.party.PartyInformationSuppplier
import ltd.matrixstudios.alchemist.redis.AsynchronousRedisSender
import ltd.matrixstudios.alchemist.redis.impl.NetworkMessagePacket
import ltd.matrixstudios.alchemist.service.party.PartyService
import ltd.matrixstudios.alchemist.service.profiles.ProfileGameService
import ltd.matrixstudios.alchemist.util.Chat
import ltd.matrixstudios.alchemist.util.TimeUtil
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitRunnable
import java.util.*

@CommandAlias("p|party")
class PartyCommands : BaseCommand() {

    @CommandAlias("create")
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
        sender.sendMessage(Chat.format("&7&m-------------------------"))
    }

    @CommandAlias("info")
    fun info(sender: Player) {
        if (PartyService.getParty(sender.uniqueId) == null) {
            sender.sendMessage(Chat.format("&cYou are not in a party!"))
            return
        }

        val party = PartyService.getParty(sender.uniqueId)

        sender.sendMessage(Chat.format("&7&m-------------------------"))
        sender.sendMessage(Chat.format("&r" + PartyInformationSuppplier.getLeaderFancyName(party!!.leader) + "&6's Party"))
        sender.sendMessage(" ")
        sender.sendMessage(Chat.format("&eMembers: &7" + PartyInformationSuppplier.formatMembersString(party).toString()))
        sender.sendMessage(Chat.format("&eUptime: &f" + TimeUtil.formatDuration((System.currentTimeMillis() - party.createdAt))))
        sender.sendMessage(Chat.format("&7&m-------------------------"))
    }

    @CommandAlias("invite")
    fun invite(player: Player, @Flags("other") @Single target: String) {
        if (PartyService.getParty(player.uniqueId) == null) {
            create(player)
        }

        val party = PartyService.getParty(player.uniqueId)!!

        if (party.members.map { it.first }.contains(player.uniqueId)
            && party.members.first { it.first.toString() == player.uniqueId.toString() }.second == PartyElevation.MEMBER) {
            player.sendMessage(Chat.format("&cYou are not high enough rank to invite people to your party!"))
            return
        }

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

        party.invited[targetProfile.uuid] = System.currentTimeMillis()
        PartyService.handler.storeAsync(party.id, party)
        AsynchronousRedisSender.send(NetworkMessagePacket(targetProfile.uuid, Chat.format("&8[&dParties&8] &fYou have been invited to join &a${player.displayName}'s &fparty!")))
    }

    @CommandAlias("disband")
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

    @CommandAlias("accept|join")
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
            AsynchronousRedisSender.send(NetworkMessagePacket(it.first, Chat.format("&8[&dParties&8] ${AlchemistAPI.getRankDisplay(targetProfile.uuid)} &fhas joined your party!")))
        }

        PartyService.handler.storeAsync(party.id, party)
    }

}
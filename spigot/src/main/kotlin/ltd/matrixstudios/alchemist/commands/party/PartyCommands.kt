package ltd.matrixstudios.alchemist.commands.party

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.Flags
import co.aikar.commands.annotation.Single
import com.sun.xml.internal.ws.wsdl.writer.document.Part
import ltd.matrixstudios.alchemist.Alchemist
import ltd.matrixstudios.alchemist.AlchemistSpigotPlugin
import ltd.matrixstudios.alchemist.models.party.Party
import ltd.matrixstudios.alchemist.redis.AsynchronousRedisSender
import ltd.matrixstudios.alchemist.redis.impl.NetworkMessagePacket
import ltd.matrixstudios.alchemist.service.party.PartyService
import ltd.matrixstudios.alchemist.service.profiles.ProfileGameService
import ltd.matrixstudios.alchemist.util.Chat
import org.bukkit.Bukkit
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

        val party = Party(UUID.randomUUID(), player.uniqueId, mutableListOf(), mutableListOf(), mutableMapOf(), System.currentTimeMillis(), true)
        PartyService.handler.storeAsync(party.id, party)

        player.sendMessage(Chat.format("&aYou have created a party!"))
    }

    @CommandAlias("invite")
    fun invite(player: Player, @Flags("other") @Single target: String) {
        if (PartyService.getParty(player.uniqueId) == null) {
            create(player)
        }

        val party = PartyService.getParty(player.uniqueId)!!

        if (party.members.contains(player.uniqueId)) {
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
        AsynchronousRedisSender.send(NetworkMessagePacket(targetProfile.uuid, Chat.format("&aYou have been invited to join ${player.displayName}'s party!")))
    }

    @CommandAlias("accept|join")
    fun accept(player: Player, target: String) {
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
    
        TODO("Finish")
    }

}
package ltd.matrixstudios.alchemist.commands.friends

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.*
import ltd.matrixstudios.alchemist.api.AlchemistAPI
import ltd.matrixstudios.alchemist.models.profile.GameProfile
import ltd.matrixstudios.alchemist.redis.AsynchronousRedisSender
import ltd.matrixstudios.alchemist.redis.impl.NetworkMessagePacket
import ltd.matrixstudios.alchemist.service.profiles.ProfileGameService
import ltd.matrixstudios.alchemist.util.Chat
import org.bukkit.command.CommandSender
import org.bukkit.Bukkit
import org.bukkit.entity.Player

@CommandAlias("friend")
class FriendCommands : BaseCommand() {

    @HelpCommand
    fun help(sender: CommandSender) {
        sender.sendMessage(Chat.format("&7&m-------------------------"))
        sender.sendMessage(Chat.format("&6&lFriend Help"))
        sender.sendMessage(" ")
        sender.sendMessage(Chat.format("&e/friend add &f<target>"))
        sender.sendMessage(Chat.format("&e/friend accept &f<target>"))
        sender.sendMessage(Chat.format("&e/friend list"))
        sender.sendMessage(Chat.format("&7&m-------------------------"))
    }

    @Subcommand("add")
    @CommandCompletion("@gameprofile")
    fun add(player: Player, @Name("target") gameProfile: GameProfile) {
        val playerProfile = AlchemistAPI.quickFindProfile(player.uniqueId).get() ?: return
        val bukkitPlayer = Bukkit.getOfflinePlayer(gameProfile.uuid)

        if (gameProfile.friends.contains(player.uniqueId)) {
            player.sendMessage(Chat.format("&cThis player is already friends with you"))
            return
        }

        if (playerProfile.friendInvites.contains(gameProfile.uuid)) {
            player.sendMessage(Chat.format("&cAlready sent an invite to this player"))
            return
        }

        if (gameProfile.uuid == player.uniqueId) {
            player.sendMessage(Chat.format("&cCannot friend yourself!"))
            return
        }

        gameProfile.friendInvites.add(player.uniqueId)
        player.sendMessage(Chat.format("&e&l[Friends] &aYou have send a friend request to &f" + gameProfile.username))

        AsynchronousRedisSender.send(NetworkMessagePacket(gameProfile.uuid, Chat.format("&e&l[Friends] &aYou have received a friend request from &f" + playerProfile.username)))
        AsynchronousRedisSender.send(NetworkMessagePacket(gameProfile.uuid, Chat.format("&e&l[Friends] &aType &f/friend accept &ato accept the request")))

        ProfileGameService.save(gameProfile)
    }

    @Subcommand("list")
    fun list(player: Player) {
        val gameProfile = AlchemistAPI.quickFindProfile(player.uniqueId).get()!!
        if (gameProfile.friends.isEmpty()) {
            player.sendMessage(Chat.format("&e&l[Friends] &cYou have no friends"))
            return
        }
        player.sendMessage(Chat.format("&7&m------------------------"))
        player.sendMessage(Chat.format("&e&lFriends:"))
        player.sendMessage(" ")
        for (profile in gameProfile.supplyFriendsAsProfiles().get()) {
            player.sendMessage(Chat.format(profile!!.getCurrentRank()!!.color + profile.username + " &7[" + (if (profile.isOnline()) "&aOnline" else "&cOffline") + "&7]"))
        }
        player.sendMessage(Chat.format("&7&m------------------------"))
    }

    @Subcommand("accept")
    @CommandCompletion("@gameprofile")
    fun accept(player: Player, @Name("target") gameProfile: GameProfile) {
        val it = ProfileGameService.byId(player.uniqueId)
        if (!it?.friendInvites!!.contains(gameProfile.uuid)) {
            player.sendMessage(Chat.format("&cThis player has never tried friending you!"))
            return
        }

        it.friendInvites.remove(gameProfile.uuid)
        it.friends.add(gameProfile.uuid)

        gameProfile.friends.add(it.uuid)

        ProfileGameService.save(it)
        ProfileGameService.save(gameProfile)

        player.sendMessage(Chat.format("&e&l[Friends] &aYou have accepted ${gameProfile.username}'s &fFriend Request"))

        AsynchronousRedisSender.send(NetworkMessagePacket(gameProfile.uuid, Chat.format("&e&l[Friends] &f" + player.name + " &ahas accepted your friend request!")))

    }
}
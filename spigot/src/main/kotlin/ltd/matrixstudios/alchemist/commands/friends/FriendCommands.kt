package ltd.matrixstudios.alchemist.commands.friends

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.Name
import co.aikar.commands.annotation.Subcommand
import ltd.matrixstudios.alchemist.api.AlchemistAPI
import ltd.matrixstudios.alchemist.models.profile.GameProfile
import ltd.matrixstudios.alchemist.service.profiles.ProfileGameService
import ltd.matrixstudios.alchemist.util.Chat
import org.bukkit.entity.Player
import sun.java2d.cmm.Profile

@CommandAlias("friend")
class FriendCommands : BaseCommand() {

    @Subcommand("add")
    fun add(player: Player, @Name("target")gameProfile: GameProfile) {
        if (gameProfile.friends.contains(player.uniqueId)) {
            player.sendMessage(Chat.format("&cThis player is already friends with you"))
            return
        }

        if (gameProfile.uuid == player.uniqueId) {
            player.sendMessage(Chat.format("&cCannot friend yourself!"))
            return
        }

        gameProfile.friendInvites.add(player.uniqueId)
        player.sendMessage(Chat.format("&e&l[Friends] &aYou have send a friend request to &f" + gameProfile.username))

        ProfileGameService.save(gameProfile)
    }

    @Subcommand("list")
    fun list(player: Player) {
        val gameProfile = AlchemistAPI.quickFindProfile(player.uniqueId)!!
        player.sendMessage(Chat.format("&7&m------------------------"))
        player.sendMessage(Chat.format("&e&lFriends:"))
        player.sendMessage(" ")
        for (profile in gameProfile.supplyFriendsAsProfiles().get()) {
            player.sendMessage(Chat.format(profile!!.getCurrentRank()!!.color + profile.username + " &7[" + (if (profile.isOnline()) "&aOnline" else "&cOffline") + "&7]"))
        }
        player.sendMessage(Chat.format("&7&m------------------------"))
    }

    @Subcommand("accept")
    fun accept(player: Player, @Name("target")gameProfile: GameProfile) {
        val playerGameProfile = ProfileGameService.byId(player.uniqueId)

        if (!playerGameProfile?.friendInvites!!.contains(gameProfile.uuid)) {
            player.sendMessage(Chat.format("&cThis player has never tried friending you!"))
            return
        }

        playerGameProfile.friendInvites.remove(gameProfile.uuid)
        playerGameProfile.friends.add(gameProfile.uuid)

        gameProfile.friends.add(playerGameProfile.uuid)

        player.sendMessage(Chat.format("&e&l[Friends] &aYou have accepted ${gameProfile.username}'s &fFriend Request"))

        ProfileGameService.save(playerGameProfile)
        ProfileGameService.save(gameProfile)
    }
}
package ltd.matrixstudios.alchemist.profiles.commands.player

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandCompletion
import co.aikar.commands.annotation.CommandPermission
import co.aikar.commands.annotation.Name
import ltd.matrixstudios.alchemist.models.profile.GameProfile
import ltd.matrixstudios.alchemist.packets.PlayerKickPacket
import ltd.matrixstudios.alchemist.redis.AsynchronousRedisSender
import ltd.matrixstudios.alchemist.redis.cache.mutate.RemoveProfileCachePacket
import ltd.matrixstudios.alchemist.service.profiles.ProfileGameService
import ltd.matrixstudios.alchemist.util.Chat
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import java.util.*

class WipeProfileCommand : BaseCommand()
{

    @CommandAlias("wipeprofile")
    @CommandPermission("alchemist.profiles.admin")
    @CommandCompletion("@gameprofile")
    fun wipe(player: Player, @Name("target") profile: GameProfile)
    {
        AsynchronousRedisSender.send(PlayerKickPacket(profile.uuid, "&cYour profile is being wiped!"))

        ProfileGameService.handler.deleteAsync(profile.uuid)
        ProfileGameService.cache.remove(profile.uuid)
        AsynchronousRedisSender.send(RemoveProfileCachePacket(profile))

        player.sendMessage(Chat.format("&aProfile has been fully wiped!"))
    }


    @CommandAlias("wipeprofileuuid")
    @CommandPermission("alchemist.profiles.admin")
    fun wipeUUID(player: CommandSender, @Name("uuid")uuidString: String) {
        val uuid = UUID.fromString(uuidString)

        AsynchronousRedisSender.send(PlayerKickPacket(uuid, "&cYour profile is being wiped!"))
        val profile = ProfileGameService.cache[uuid]

        ProfileGameService.handler.deleteAsync(uuid)
        ProfileGameService.cache.remove(uuid)

        if (profile != null)
        {
            AsynchronousRedisSender.send(RemoveProfileCachePacket(profile))
        }

        player.sendMessage(Chat.format("&aProfile has been fully wiped!"))
    }
}
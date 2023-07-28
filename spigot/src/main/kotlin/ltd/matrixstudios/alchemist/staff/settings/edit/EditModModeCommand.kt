package ltd.matrixstudios.alchemist.staff.settings.edit

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandPermission
import ltd.matrixstudios.alchemist.redis.AsynchronousRedisSender
import ltd.matrixstudios.alchemist.redis.RedisPacket
import ltd.matrixstudios.alchemist.redis.RedisPacketManager
import ltd.matrixstudios.alchemist.serialize.Serializers
import ltd.matrixstudios.alchemist.serialize.type.ItemStackAdapter
import ltd.matrixstudios.alchemist.staff.settings.edit.menu.EditModModeMenu
import ltd.matrixstudios.alchemist.util.Chat
import org.bukkit.entity.Player
import java.util.concurrent.CompletableFuture

class EditModModeCommand : BaseCommand() {

    @CommandAlias("editmodmode")
    @CommandPermission("alchemist.staffmode")
    fun editmodMode(player: Player)
    {
        EditModModeMenu(player).openMenu()
        player.sendMessage(Chat.format("&eYou are now editing your &amod mode"))
        player.sendMessage(Chat.format("&7&oTo save any changes, execute /savemodmode"))
    }

    @CommandAlias("savemodmode")
    @CommandPermission("alchemist.staffmode")
    fun savemodmode(player: Player)
    {
        RedisPacketManager.pool.resource.use {
            it.hset("Alchemist:ModMode:", player.uniqueId.toString(), ItemStackAdapter.itemStackArrayToBase64(player.inventory.contents))
        }

        player.sendMessage(Chat.format("&eYou have updated your &amod mode"))
    }
}
package ltd.matrixstudios.alchemist.staff.settings.edit

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandCompletion
import co.aikar.commands.annotation.CommandPermission
import co.aikar.commands.annotation.Name
import co.aikar.commands.bukkit.contexts.OnlinePlayer
import ltd.matrixstudios.alchemist.redis.RedisPacketManager
import ltd.matrixstudios.alchemist.serialize.type.ItemStackAdapter
import ltd.matrixstudios.alchemist.staff.settings.edit.menu.EditModModeMenu
import ltd.matrixstudios.alchemist.util.Chat
import org.bukkit.entity.Player

class EditModModeCommand : BaseCommand()
{

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
            it.hset(
                "Alchemist:ModMode:",
                player.uniqueId.toString(),
                ItemStackAdapter.itemStackArrayToBase64(player.inventory.contents)
            )
        }

        player.sendMessage(Chat.format("&eYou have updated your &amod mode"))
    }

    @CommandAlias("wipemodmode")
    @CommandPermission("alchemist.staffmode.admin")
    @CommandCompletion("@players")
    fun wipeModMode(player: Player, @Name("target") target: OnlinePlayer)
    {
        RedisPacketManager.pool.resource.use {
            it.hdel("Alchemist:ModMode:", target.player.uniqueId.toString())
        }

        player.sendMessage(Chat.format("&eYou have wiped ${target.player.displayName}&e's &amod mode"))
    }
}
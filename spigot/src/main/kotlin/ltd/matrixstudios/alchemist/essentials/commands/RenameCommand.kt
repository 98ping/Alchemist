package ltd.matrixstudios.alchemist.essentials.commands

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandPermission
import co.aikar.commands.annotation.Name
import ltd.matrixstudios.alchemist.util.Chat
import org.bukkit.Material
import org.bukkit.entity.Player

class RenameCommand : BaseCommand()
{

    @CommandAlias("rename")
    @CommandPermission("alchemist.essentials.rename")
    fun rename(player: Player, @Name("name...") name: String)
    {
        val inHand = player.inventory.itemInHand

        if (inHand == null || inHand.type == Material.AIR)
        {
            player.sendMessage(Chat.format("&cYou must be holding an item to rename it!"))
            return
        }

        if (!player.hasPermission("alchemist.essentials.rename") && name.contains("&"))
        {
            player.sendMessage(Chat.format("&cYou do not have permissions to use color codes in your renames!"))
            return
        }

        val meta = inHand.itemMeta

        meta.displayName = Chat.format(name)
        inHand.itemMeta = meta

        player.updateInventory()

        player.sendMessage(Chat.format("&aYou have renamed your item to say $name"))
    }

}
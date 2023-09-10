package ltd.matrixstudios.alchemist.essentials.commands

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandPermission
import co.aikar.commands.annotation.Name
import ltd.matrixstudios.alchemist.util.Chat
import ltd.matrixstudios.alchemist.util.items.ItemBuilder
import org.bukkit.Material
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player

object EntityCommands : BaseCommand()
{
    val item = ItemBuilder.of(Material.BOOK)
        .name(Chat.format("&aEdit Entity &7(Left Click)"))
        .setLore(mutableListOf(
            " ",
            "&7Left-Click on any entity to",
            "&7bring up an editor menu where",
            "&7you can change attributes about an entity.",
            " "
        )).build()

    @CommandAlias("spawnentity")
    @CommandPermission("alchemist.essentials.spawnentity")
    fun spawnEntity(sender: Player, @Name("entity") typeString: EntityType, @Name("name")name: String)
    {
        if (!typeString.isAlive || !typeString.isSpawnable || typeString == EntityType.PLAYER)
        {
            sender.sendMessage(Chat.format("&cYou cannot spawn an entity that is not alive or not spawnable."))
            return
        }

        val entity = sender.world.spawnEntity(sender.location, typeString)
        entity.customName = Chat.format(name)

        sender.sendMessage(Chat.format("&eYou spawned an entity with type &b${typeString.name} &eand the name &r${name}"))
    }

    @CommandAlias("editentity")
    @CommandPermission("alchemist.essentials.editentity")
    fun editEntity(player: Player)
    {
        player.inventory.addItem(item)
        player.sendMessage(Chat.format("&eYou have been given an &aentity editor&e."))
    }
}
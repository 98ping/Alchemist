package ltd.matrixstudios.alchemist.commands.tags

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandPermission
import co.aikar.commands.annotation.Name
import co.aikar.commands.annotation.Subcommand
import ltd.matrixstudios.alchemist.commands.tags.menu.TagCustomizationMenu
import ltd.matrixstudios.alchemist.models.tags.Tag
import ltd.matrixstudios.alchemist.service.tags.TagService
import ltd.matrixstudios.alchemist.util.Chat
import org.bukkit.Material
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

@CommandAlias("tagadmin")
class TagAdminCommand : BaseCommand() {

    @Subcommand("create")
    @CommandPermission("alchemist.tags.admin")
    fun create(sender: CommandSender, @Name("name")name: String) {
        val tag = TagService.byId(name)

        if (tag != null) {
            sender.sendMessage(Chat.format("&cTag already exists"))
            return
        }

        TagService.save(Tag(name, "&7$name", true, "", "Text", Material.NAME_TAG.name))
        sender.sendMessage(Chat.format("&aCreated a new tag"))
    }

    @Subcommand("delete")
    @CommandPermission("alchemist.tags.admin")
    fun delete(sender: CommandSender, @Name("name")name: String)
    {
        val tag = TagService.byId(name)

        if (tag == null) {
            sender.sendMessage(Chat.format("&cTag does not exist"))
            return
        }

        TagService.handler.delete(tag.id)
        sender.sendMessage(Chat.format("&cDeleted this tag!"))
    }

    @Subcommand("edit")
    @CommandPermission("alchemist.tags.admin")
    fun edit(player: Player) {
        TagCustomizationMenu(player).updateMenu()
    }
}
package ltd.matrixstudios.alchemist.commands.tags

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.Name
import co.aikar.commands.annotation.Subcommand
import ltd.matrixstudios.alchemist.models.tags.Tag
import ltd.matrixstudios.alchemist.service.tags.TagService
import ltd.matrixstudios.alchemist.util.Chat
import org.bukkit.command.CommandSender

@CommandAlias("tagadmin")
class TagAdminCommand : BaseCommand() {

    @Subcommand("create")
    fun create(sender: CommandSender, @Name("name")name: String) {
        val tag = TagService.byId(name)

        if (tag != null) {
            sender.sendMessage(Chat.format("&cTag already exists"))
            return
        }

        TagService.save(Tag(name, name, true, ""))
        sender.sendMessage(Chat.format("&aCreated a new tag"))
    }
}
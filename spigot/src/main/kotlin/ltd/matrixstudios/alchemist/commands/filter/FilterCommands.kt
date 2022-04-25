package ltd.matrixstudios.alchemist.commands.filter

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandPermission
import co.aikar.commands.annotation.Name
import co.aikar.commands.annotation.Subcommand
import ltd.matrixstudios.alchemist.models.filter.Filter
import ltd.matrixstudios.alchemist.service.filter.FilterService
import ltd.matrixstudios.alchemist.util.Chat
import org.bukkit.entity.Player
import java.util.*

@CommandAlias("filters|filter")
class FilterCommands : BaseCommand() {

    @Subcommand("create")
    @CommandPermission("alchemist.filters.admin")
    fun create(player: Player, @Name("word")word: String) {
        if (FilterService.byWord(word) != null) {
            player.sendMessage(Chat.format("&cFilter for word already exists"))
            return
        }
        val filter = Filter(UUID.randomUUID(), word, "")

        FilterService.save(filter)
        player.sendMessage(Chat.format("&aCreated a new filter with the word $word"))
    }

    @Subcommand("setcommand")
    @CommandPermission("alchemist.filters.admin")
    fun setcommand(player: Player, @Name("word")word: String, @Name("command")command: String) {
        if (FilterService.byWord(word) == null) {
            player.sendMessage(Chat.format("&cNo filter for word exists"))
            return
        }

        val filter = FilterService.byWord(word) ?: return

        filter.command = command

        FilterService.save(filter)
        player.sendMessage(Chat.format("&aUpdated the command for the filter $word"))
    }

}
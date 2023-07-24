package ltd.matrixstudios.alchemist.commands.filter

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandPermission
import co.aikar.commands.annotation.Default
import co.aikar.commands.annotation.Name
import co.aikar.commands.annotation.Subcommand
import ltd.matrixstudios.alchemist.redis.cache.RefreshFiltersPacket
import ltd.matrixstudios.alchemist.commands.filter.menu.FilterEditorMenu
import ltd.matrixstudios.alchemist.redis.AsynchronousRedisSender
import ltd.matrixstudios.alchemist.service.filter.FilterService
import ltd.matrixstudios.alchemist.util.Chat
import org.bukkit.entity.Player
import java.util.*
@CommandAlias("filters|filter")
@CommandPermission("alchemist.filters.admin")
class FilterCommands : BaseCommand() {

    @Default
    fun create(player: Player) {
        FilterEditorMenu(player).updateMenu()
    }

    @Subcommand("delete")
    fun delete(player: Player, @Name("word") word: String) {
        val filter = FilterService.byWord(word.lowercase())

        if (filter == null) {
            player.sendMessage(Chat.format("&cThis is not a filter!"))
            return
        }

        FilterService.handler.delete(filter.id)
        AsynchronousRedisSender.send(RefreshFiltersPacket())
        player.sendMessage(Chat.format("&aDeleted the &f$word &afilter"))
    }


}
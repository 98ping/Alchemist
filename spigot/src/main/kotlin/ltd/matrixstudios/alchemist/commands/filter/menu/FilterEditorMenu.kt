package ltd.matrixstudios.alchemist.commands.filter.menu

import ltd.matrixstudios.alchemist.commands.filter.menu.editor.FilterSubEditorMenu
import ltd.matrixstudios.alchemist.models.filter.Filter
import ltd.matrixstudios.alchemist.punishments.PunishmentType
import ltd.matrixstudios.alchemist.redis.AsynchronousRedisSender
import ltd.matrixstudios.alchemist.redis.cache.refresh.RefreshFiltersPacket
import ltd.matrixstudios.alchemist.service.filter.FilterService
import ltd.matrixstudios.alchemist.util.Chat
import ltd.matrixstudios.alchemist.util.InputPrompt
import ltd.matrixstudios.alchemist.util.menu.Button
import ltd.matrixstudios.alchemist.util.menu.buttons.SkullButton
import ltd.matrixstudios.alchemist.util.menu.pagination.PaginatedMenu
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import java.util.*

class FilterEditorMenu(val player: Player) : PaginatedMenu(27, player) {

    override fun getHeaderItems(player: Player): MutableMap<Int, Button> {
        val buttons = mutableMapOf<Int, Button>()

        buttons[4] = SkullButton(
            "ewogICJ0aW1lc3RhbXAiIDogMTY1NjAyNzIyNzA4NiwKICAicHJvZmlsZUlkIiA6ICI4N2RiMmNjNWY4Y2I0MjI4YTU0OGRiMzJlM2Y0NmFmNiIsCiAgInByb2ZpbGVOYW1lIiA6ICJZVG1hdGlhczEzbG9sIiwKICAic2lnbmF0dXJlUmVxdWlyZWQiIDogdHJ1ZSwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlLzJhYjc1YzRhZTBmNmFmYTNkZmUyYmExODJlMTA5MzVmMDAwYmEzNTQ5YzUzMjI5OWY5YjUwMjUxM2U3Zjk5Y2UiCiAgICB9CiAgfQp9",
            listOf(" ", Chat.format("&7Create a new filter")).toMutableList(),
            "&aNew Filter").setBody {
                player, i, clickType ->


            InputPrompt()
                .withText(Chat.format("&aType in the word you want to use for this filter"))
                .acceptInput {
                    val filter = Filter(UUID.randomUUID(), it.lowercase(), false, PunishmentType.MUTE, "1d", false, "", false)

                    FilterService.save(filter)
                    AsynchronousRedisSender.send(RefreshFiltersPacket())
                    player.sendMessage(Chat.format("&aCreated a new filter!"))
                }.start(player)
        }

        return buttons
    }

    override fun getPagesButtons(player: Player): MutableMap<Int, Button> {
        val buttons = hashMapOf<Int, Button>()
        var index = 0

        for (filter in FilterService.cache.values)
        {
            buttons[index++] = FilterButton(filter)
        }

        return buttons
    }

    override fun getTitle(player: Player): String {
        return "Viewing All Filters"
    }

    class FilterButton(val filter: Filter) : Button()
    {
        override fun getMaterial(player: Player): Material {
            return Material.WOOL
        }

        override fun getDescription(player: Player): MutableList<String>? {
            val desc = mutableListOf<String>()

            desc.add(Chat.format("&6&m-------------------------------------"))
            desc.add(Chat.format("&eWord: &f" + filter.word))
            desc.add(Chat.format("&eSilent Filter: &f" + filter.silent))
            desc.add(Chat.format("&6&m-------------------------------------"))
            desc.add(Chat.format("&eShould Punish: &f" + if (filter.shouldPunish) "&aYes" else "&cNo"))
            desc.add(Chat.format("&eType: &f" + filter.punishmentType.color + filter.punishmentType.niceName))
            desc.add(Chat.format("&eDuration: &f" + filter.duration))
            desc.add(Chat.format("&6&m-------------------------------------"))
            desc.add(Chat.format("&eStaff Exempt: &f" + if(filter.staffExempt) "&aYes" else "&cNo"))
            desc.add(Chat.format("&ePermission: &f" + filter.exemptPermission))
            desc.add(Chat.format("&6&m-------------------------------------"))
            desc.add(Chat.format("&a&lLeft-Click to edit"))
            desc.add(Chat.format("&6&m-------------------------------------"))

            return desc
        }

        override fun getDisplayName(player: Player): String? {
            return Chat.format("&6" + filter.word)
        }

        override fun getData(player: Player): Short {
            return if (filter.silent) {
                7
            } else if (filter.shouldPunish) {
                14
            } else {
                5
            }
        }

        override fun onClick(player: Player, slot: Int, type: ClickType) {
            FilterSubEditorMenu(player, filter).openMenu()
        }
    }
}
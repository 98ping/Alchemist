package ltd.matrixstudios.alchemist.commands.permission.menu

import ltd.matrixstudios.alchemist.models.filter.Filter
import ltd.matrixstudios.alchemist.models.profile.GameProfile
import ltd.matrixstudios.alchemist.punishments.PunishmentType
import ltd.matrixstudios.alchemist.service.filter.FilterService
import ltd.matrixstudios.alchemist.service.profiles.ProfileGameService
import ltd.matrixstudios.alchemist.util.Chat
import ltd.matrixstudios.alchemist.util.InputPrompt
import ltd.matrixstudios.alchemist.util.menu.Button
import ltd.matrixstudios.alchemist.util.menu.buttons.SkullButton
import ltd.matrixstudios.alchemist.util.menu.pagination.PaginatedMenu
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import java.util.*

class PermissionEditMenu(val player: Player, val gameProfile: GameProfile) : PaginatedMenu(18, player) {

    override fun getHeaderItems(player: Player): MutableMap<Int, Button> {
        val buttons = mutableMapOf<Int, Button>()

        buttons[4] = SkullButton(
            "ewogICJ0aW1lc3RhbXAiIDogMTY1NjAyNzIyNzA4NiwKICAicHJvZmlsZUlkIiA6ICI4N2RiMmNjNWY4Y2I0MjI4YTU0OGRiMzJlM2Y0NmFmNiIsCiAgInByb2ZpbGVOYW1lIiA6ICJZVG1hdGlhczEzbG9sIiwKICAic2lnbmF0dXJlUmVxdWlyZWQiIDogdHJ1ZSwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlLzJhYjc1YzRhZTBmNmFmYTNkZmUyYmExODJlMTA5MzVmMDAwYmEzNTQ5YzUzMjI5OWY5YjUwMjUxM2U3Zjk5Y2UiCiAgICB9CiAgfQp9",
            listOf(" ", Chat.format("&7Add a new permission")).toMutableList(),
            "&aAdd Permission").setBody {
                player, i, clickType ->


            InputPrompt()
                .withText(Chat.format("&aType in the permission you want to add!"))
                .acceptInput {
                    gameProfile.permissions.add(it)

                    ProfileGameService.save(gameProfile)
                    player.sendMessage(Chat.format("&aAdded a new permission!"))

                    PermissionEditMenu(player, gameProfile).updateMenu()
                }.start(player)
        }

        return buttons
    }
    override fun getPagesButtons(player: Player): MutableMap<Int, Button> {
        val buttons = mutableMapOf<Int, Button>()
        var index = 0

        for (permission in gameProfile.permissions)
        {
            buttons[index++] = PermissionsButton(permission, gameProfile)
        }

        return buttons
    }

    override fun getTitle(player: Player): String {
        return "Editing Permissions"
    }

    class PermissionsButton(val permission: String, val profile: GameProfile) : Button()
    {
        override fun getMaterial(player: Player): Material {
            return Material.PAPER
        }

        override fun getDescription(player: Player): MutableList<String>? {
            val desc = mutableListOf<String>()
            desc.add(Chat.format("&6&m--------------------"))
            desc.add(Chat.format("&ePermission: &f$permission"))
            desc.add(" ")
            desc.add(Chat.format("&cClick to remove"))
            desc.add(Chat.format("&6&m--------------------"))

            return desc
        }

        override fun getDisplayName(player: Player): String? {
            return Chat.format("&6${permission}")
        }

        override fun getData(player: Player): Short {
            return 0
        }

        override fun onClick(player: Player, slot: Int, type: ClickType) {
            profile.permissions.remove(permission)

            ProfileGameService.save(profile)
            player.sendMessage(Chat.format("&aRemoved this permission!"))

            PermissionEditMenu(player, profile).updateMenu()
        }

    }
}
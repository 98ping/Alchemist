package ltd.matrixstudios.alchemist.commands.tags.grants.menu.grant

import ltd.matrixstudios.alchemist.models.profile.GameProfile
import ltd.matrixstudios.alchemist.service.tags.TagService
import ltd.matrixstudios.alchemist.util.menu.Button
import ltd.matrixstudios.alchemist.util.menu.pagination.PaginatedMenu
import org.bukkit.entity.Player

class TagGrantMenu(val player: Player, val gameProfile: GameProfile) : PaginatedMenu(18, player) {

    override fun getPagesButtons(player: Player): MutableMap<Int, Button> {
        val buttons = hashMapOf<Int, Button>()

        var index = 0
        for (tag in TagService.cache.values) {
            buttons[index++] = TagGrantButton(tag, gameProfile)
        }

        return buttons
    }

    override fun getTitle(player: Player): String {
        return "Tag Granting " + gameProfile.username
    }
}
package ltd.matrixstudios.alchemist.commands.tags.grants.menu.grants

import ltd.matrixstudios.alchemist.models.profile.GameProfile
import ltd.matrixstudios.alchemist.service.expirable.TagGrantService
import ltd.matrixstudios.alchemist.util.menu.Button
import ltd.matrixstudios.alchemist.util.menu.pagination.PaginatedMenu
import org.bukkit.entity.Player

class TagGrantsMenu(val player: Player, val gameProfile: GameProfile) : PaginatedMenu(18, player)
{

    override fun getPagesButtons(player: Player): MutableMap<Int, Button>
    {
        val buttons = hashMapOf<Int, Button>()


        var index = 0

        for (tag in TagGrantService.getValues().get().filter { it.target == gameProfile.uuid })
        {
            buttons[index++] = TagGrantsButton(tag)
        }


        return buttons
    }

    override fun getTitle(player: Player): String
    {
        return "Grants of " + gameProfile.username
    }
}
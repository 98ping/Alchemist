package ltd.matrixstudios.alchemist.grants.menu.history.sub


import ltd.matrixstudios.alchemist.grants.menu.grants.GrantsButton
import ltd.matrixstudios.alchemist.models.grant.types.RankGrant
import ltd.matrixstudios.alchemist.util.menu.Button
import ltd.matrixstudios.alchemist.util.menu.pagination.PaginatedMenu
import org.bukkit.entity.Player

class GrantHistoryViewGrants(val player: Player, val grants: MutableList<RankGrant>) : PaginatedMenu(18, player)
{

    override fun getPagesButtons(player: Player): MutableMap<Int, Button>
    {
        val buttons = hashMapOf<Int, Button>()

        val time = System.currentTimeMillis()

        var index = 0
        for (grant in grants)
        {
            buttons[index++] = GrantsButton(grant)
        }


        println("Menu " + this.javaClass.simpleName + " took " + System.currentTimeMillis().minus(time) + "ms to open")

        return buttons
    }

    override fun getTitle(player: Player): String
    {
        return "Checking Grant History"
    }
}
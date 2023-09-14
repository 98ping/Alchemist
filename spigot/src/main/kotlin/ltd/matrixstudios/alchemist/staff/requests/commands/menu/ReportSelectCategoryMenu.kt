package ltd.matrixstudios.alchemist.staff.requests.commands.menu

import ltd.matrixstudios.alchemist.Alchemist
import ltd.matrixstudios.alchemist.staff.requests.handlers.RequestHandler
import ltd.matrixstudios.alchemist.util.Chat
import ltd.matrixstudios.alchemist.util.InputPrompt
import ltd.matrixstudios.alchemist.util.menu.Button
import ltd.matrixstudios.alchemist.util.menu.Menu
import ltd.matrixstudios.alchemist.util.menu.buttons.SimpleActionButton
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import java.util.concurrent.TimeUnit

class ReportSelectCategoryMenu(val player: Player) : Menu(player)
{
    init
    {
        staticSize = 9
        placeholder = true
    }

    override fun getButtons(player: Player): MutableMap<Int, Button>
    {
        return mutableMapOf(
            0 to SimpleActionButton(Material.ANVIL, mutableListOf(), "&aAll Reports", 0).setBody { player, i, clickType ->
                ShowReportsMenu(player, RequestHandler.activeReports.values.toMutableList()).updateMenu()
            },
            2 to SimpleActionButton(Material.BOOK, mutableListOf(), "&eYour Server", 0).setBody { player, i, clickType ->
                ShowReportsMenu(player, RequestHandler.activeReports.values.filter {
                    it.server.equals(Alchemist.globalServer.displayName, ignoreCase = true)
                }.toMutableList()).updateMenu()
            },
            4 to SimpleActionButton(Material.NETHER_STAR, mutableListOf(), "&bPast Hour", 0).setBody { player, i, clickType ->
                ShowReportsMenu(player, RequestHandler.activeReports.values.filter {
                    System.currentTimeMillis().minus(it.issuedAt) <= TimeUnit.HOURS.toMillis(1L)
                }.toMutableList()).updateMenu()
            },
            6 to SimpleActionButton(Material.NAME_TAG, mutableListOf(), "&6Specific Server", 0).setBody { player, i, clickType ->
                InputPrompt()
                    .withText(Chat.format("&eType in the &aserver display name &ethat you want to check the reports of."))
                    .acceptInput { str ->
                        ShowReportsMenu(player, RequestHandler.activeReports.values.filter {
                            it.server.equals(str, ignoreCase = true)
                        }.toMutableList()).updateMenu()
                    }.start(player)
            },
            8 to SimpleActionButton(Material.ARROW, mutableListOf(), "&cReported Player is Online", 0).setBody { player, i, clickType ->
                ShowReportsMenu(player, RequestHandler.activeReports.values.filter {
                    Bukkit.getPlayer(it.issuedTo) != null
                }.toMutableList()).updateMenu()
            }
        )
    }

    override fun getTitle(player: Player): String
    {
        return "Select a Report Category"
    }
}
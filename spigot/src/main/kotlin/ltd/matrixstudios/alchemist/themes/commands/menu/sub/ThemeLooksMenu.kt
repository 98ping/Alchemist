package ltd.matrixstudios.alchemist.themes.commands.menu.sub

import ltd.matrixstudios.alchemist.service.profiles.ProfileGameService
import ltd.matrixstudios.alchemist.themes.Theme
import ltd.matrixstudios.alchemist.themes.ThemeLoader
import ltd.matrixstudios.alchemist.themes.commands.menu.sub.module.GrantThemeButton
import ltd.matrixstudios.alchemist.themes.commands.menu.sub.module.GrantsThemeButton
import ltd.matrixstudios.alchemist.themes.commands.menu.sub.module.HistoryPlaceholderButton
import ltd.matrixstudios.alchemist.themes.commands.menu.sub.module.PunishmentsButton
import ltd.matrixstudios.alchemist.util.Chat
import ltd.matrixstudios.alchemist.util.menu.Button
import ltd.matrixstudios.alchemist.util.menu.Menu
import ltd.matrixstudios.alchemist.util.menu.buttons.PlaceholderButton
import ltd.matrixstudios.alchemist.util.menu.buttons.SimpleActionButton
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType

class ThemeLooksMenu(val player: Player, val theme: Theme) : Menu(player) {

    init {
        staticSize = 27
        placeholder = true
    }


    override fun getButtons(player: Player): MutableMap<Int, Button> {
        val buttons = mutableMapOf<Int, Button>()

        buttons[4] = SimpleActionButton(Material.NETHER_STAR, mutableListOf(), Chat.format("&aClick to set this as your theme!"), 0).setBody {
            player, i, clickType ->

            ThemeLoader.setFallbackTheme(theme)

            player.closeInventory()
            player.sendMessage(Chat.format("&aYou have set a new theme!"))
        }

        buttons[10] = GrantsThemeButton(theme, player)
        buttons[11] = PunishmentsButton(theme, player)
        buttons[12] = HistoryPlaceholderButton(theme, player, ProfileGameService.byId(player.uniqueId)!!)
        buttons[13] = GrantThemeButton(theme, player)
        return buttons
    }

    override fun getTitle(player: Player): String {
        return "Observe Looks"
    }
}
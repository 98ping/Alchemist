package ltd.matrixstudios.alchemist.servers.menu.sub.menus

import ltd.matrixstudios.alchemist.models.server.UniqueServer
import ltd.matrixstudios.alchemist.models.server.software.ServerPlugin
import ltd.matrixstudios.alchemist.util.Chat
import ltd.matrixstudios.alchemist.util.menu.Button
import ltd.matrixstudios.alchemist.util.menu.pagination.PaginatedMenu
import ltd.matrixstudios.alchemist.util.menu.type.BorderedPaginatedMenu
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType

/**
 * Class created on 7/29/2023

 * @author 98ping
 * @project Alchemist
 * @website https://solo.to/redis
 */
class CheckPluginsMenu(val player: Player, val server: UniqueServer) : BorderedPaginatedMenu(player) {
    override fun getPagesButtons(player: Player): MutableMap<Int, Button> {
        val buttons = mutableMapOf<Int, Button>()
        var i = 0

        for (software in server.findServerSoftware().plugins) {
            buttons[i++] = PluginButton(software)
        }

        return buttons
    }

    override fun getTitle(player: Player): String {
        return "Viewing Plugins"
    }

    class PluginButton(val software: ServerPlugin) : Button() {
        override fun getMaterial(player: Player): Material {
            return Material.PAPER
        }

        override fun getDescription(player: Player): MutableList<String>? {
            val desc = mutableListOf<String>()
            desc.add(Chat.format("&6&m-------------------------------------"))
            desc.add(Chat.format("&eName: &f" + software.name))
            desc.add(Chat.format("&eVersion: &f" + software.version))
            desc.add(Chat.format("&6&m-------------------------------------"))
            desc.add(Chat.format("&eAuthors: &f" + software.author))
            desc.add(Chat.format("&eMain Class: &f" + software.mainClass))
            desc.add(Chat.format("&6&m-------------------------------------"))
            return desc
        }

        override fun getDisplayName(player: Player): String? {
            return Chat.format("&6${software.name}")
        }

        override fun getData(player: Player): Short {
            return 0
        }

        override fun onClick(player: Player, slot: Int, type: ClickType) {

        }

    }
}
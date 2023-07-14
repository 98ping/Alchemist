package ltd.matrixstudios.alchemist.servers.menu

import ltd.matrixstudios.alchemist.commands.rank.menu.sub.RankEditPropertiesMenu
import ltd.matrixstudios.alchemist.models.server.UniqueServer
import ltd.matrixstudios.alchemist.servers.menu.sub.ServerOptionsMenu
import ltd.matrixstudios.alchemist.util.Chat
import ltd.matrixstudios.alchemist.util.menu.Button
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType

class UniqueServerButton(var server: UniqueServer) : Button() {

    override fun getMaterial(player: Player): Material {
        return (if (server.online) Material.EMERALD_BLOCK else Material.REDSTONE_BLOCK)
    }

    override fun getDescription(player: Player): MutableList<String>? {
        val desc = arrayListOf<String>()
        desc.add(Chat.format("&6&m-------------------------------------"))
        desc.add(Chat.format("&eId: &f" + server.id))
        desc.add(Chat.format("&eDisplayName: &f" + server.displayName))
        desc.add(Chat.format("&eBungee Id: &f" + server.bungeeName))
        desc.add(Chat.format("&eQueue Server Name: &f" + server.queueName))
        desc.add(Chat.format("&6&m-------------------------------------"))
        desc.add(Chat.format("&eRam: &f" + server.ramAllocated))
        desc.add(Chat.format("&eOnline: &f" + server.online))
        desc.add(Chat.format("&6&m-------------------------------------"))
        desc.add(Chat.format("&eLocked: &f" + server.lockedWithRank))
        desc.add(Chat.format("&eLock Rank: &f" + server.lockRank))
        desc.add(Chat.format("&7&m--------------------"))

        return desc
    }

    override fun getDisplayName(player: Player): String? {
        return Chat.format((if (server.online) "&a" else "&c") + server.displayName)
    }

    override fun getData(player: Player): Short {
        return 0
    }

    override fun onClick(player: Player, slot: Int, type: ClickType) {
        ServerOptionsMenu(player, server).openMenu()
    }
}
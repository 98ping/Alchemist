package ltd.matrixstudios.alchemist.servers.menu

import ltd.matrixstudios.alchemist.models.server.UniqueServer
import ltd.matrixstudios.alchemist.servers.menu.sub.ServerOptionsMenu
import ltd.matrixstudios.alchemist.service.ranks.RankService
import ltd.matrixstudios.alchemist.util.Chat
import ltd.matrixstudios.alchemist.util.TimeUtil
import ltd.matrixstudios.alchemist.util.menu.Button
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType

class UniqueServerButton(var server: UniqueServer) : Button()
{

    override fun getMaterial(player: Player): Material
    {
        return (if (server.online) Material.EMERALD_BLOCK else Material.REDSTONE_BLOCK)
    }

    override fun getDescription(player: Player): MutableList<String>
    {
        val desc = arrayListOf<String>()
        desc.add(Chat.format("&6&m-------------------------------------"))
        desc.add(Chat.format("&eId: &f" + server.id))
        desc.add(Chat.format("&eDisplayName: &f" + server.displayName))
        desc.add(Chat.format("&eBungee Id: &f" + server.bungeeName))
        desc.add(Chat.format("&eQueue Server Name: &f" + server.queueName))
        desc.add(Chat.format("&6&m-------------------------------------"))
        desc.add(Chat.format("&eRam: &f" + server.ramAllocated + "mb"))
        desc.add(Chat.format("&eStatus: &f" + if (server.online) "&aOnline" else "&cOffline"))
        desc.add(
            Chat.format(
                "&eLast Heartbeat: &f" + TimeUtil.formatDuration(
                    System.currentTimeMillis().minus(server.lastHeartbeat)
                ) + " ago"
            )
        )
        desc.add(Chat.format("&eServer Version: &f" + server.findServerSoftware().serverVersion))
        desc.add(Chat.format("&6&m-------------------------------------"))
        desc.add(Chat.format("&eLocked: &f" + if (server.lockedWithRank) "&aYes" else "&cNo"))
        desc.add(Chat.format("&eLock Rank: &f" + getFormattedLockRank()))
        desc.add(Chat.format("&6&m-------------------------------------"))

        return desc
    }

    fun getFormattedLockRank(): String
    {
        if (server.lockRank == "") return "&cNone"

        val rank = RankService.byId(server.lockRank)

        if (rank != null)
        {
            return rank.color + rank.displayName
        }

        return "&cNone"
    }


    override fun getDisplayName(player: Player): String
    {
        return Chat.format((if (server.online) "&a" else "&c") + server.displayName)
    }

    override fun getData(player: Player): Short
    {
        return 0
    }

    override fun onClick(player: Player, slot: Int, type: ClickType)
    {
        ServerOptionsMenu(player, server).openMenu()
    }
}
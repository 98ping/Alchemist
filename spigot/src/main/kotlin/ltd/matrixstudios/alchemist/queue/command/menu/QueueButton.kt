package ltd.matrixstudios.alchemist.queue.command.menu

import ltd.matrixstudios.alchemist.models.queue.QueueModel
import ltd.matrixstudios.alchemist.util.Chat
import ltd.matrixstudios.alchemist.util.menu.Button
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import org.bukkit.inventory.ItemStack

/**
 * Class created on 7/13/2023

 * @author 98ping
 * @project Alchemist
 * @website https://solo.to/redis
 */
class QueueButton(val queue: QueueModel) : Button() {

    override fun getMaterial(player: Player): Material {
        return Material.DIAMOND
    }

    override fun getDescription(player: Player): MutableList<String>? {
        val desc = mutableListOf<String>()
        desc.add(Chat.format("&6&m-------------------------------------"))
        desc.add(Chat.format("&eDisplay Name: &f" + queue.displayName))
        desc.add(Chat.format("&eMaterial: &f" + queue.material))
        desc.add(Chat.format("&eMaximum Entries: &f" + queue.playersInQueue.size + "/" + queue.limit))
        desc.add(Chat.format("&eSend Delay: &f" + queue.sendDelay + " seconds"))
        desc.add(Chat.format("&6&m-------------------------------------"))
        desc.add(Chat.format("&eStatus: &f" + queue.status.displayName))
        desc.add(Chat.format("&eTarget Server: &f" + queue.uniqueServerId))
        desc.add(Chat.format("&eCurrently Eligible: &f" + queue.isAvailable(player.uniqueId)))
        desc.add(Chat.format("&6&m-------------------------------------"))
        desc.add(Chat.format("&a&lLeft-Click &ato edit this queue!"))
        desc.add(Chat.format("&c&lRight-Click &cto delete this queue!"))
        desc.add(Chat.format("&6&m-------------------------------------"))

        return desc
    }

    override fun getDisplayName(player: Player): String? {
        return Chat.format("&e" + queue.displayName)
    }

    override fun getData(player: Player): Short {
        return 0
    }

    override fun onClick(player: Player, slot: Int, type: ClickType) {
        player.sendMessage("Hi")
    }

}
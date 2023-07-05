package ltd.matrixstudios.alchemist.commands.metrics.menu.buttons

/**
 * Class created on 7/4/2023

 * @author 98ping
 * @project Alchemist
 * @website https://solo.to/redis
 */
import ltd.matrixstudios.alchemist.metric.MetricService
import ltd.matrixstudios.alchemist.util.Chat
import ltd.matrixstudios.alchemist.util.TimeUtil
import ltd.matrixstudios.alchemist.util.menu.Button
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType

class PermissionMetricButton : Button() {

    override fun getMaterial(player: Player): Material {
        return Material.REDSTONE
    }

    override fun getDescription(player: Player): MutableList<String>? {
        val desc = mutableListOf<String>()
        desc.add(Chat.format("&7&m-------------------"))
        val average = MetricService.averageMS("Permission Handler")
        if (average != Long.MAX_VALUE) {
            desc.add(Chat.format("&eAverage ms/j: &c" + average + "ms"))
        } else {
            desc.add(Chat.format("&eAverage ms/j: &cN/A"))
        }
        val all = MetricService.getLast10("Permission Handler")
        desc.add(Chat.format("&eQuartile Low: &c" + all.sortedBy { it.ms }.first().ms + "ms"))
        desc.add(Chat.format("&eQuartile High: &c" + all.sortedByDescending { it.ms }.first().ms + "ms"))
        desc.add(" ")
        desc.add(Chat.format("&eLast 10 Entries"))
        for (metric in all)
        {
            desc.add(Chat.format("&7* &e" + metric.ms + "ms"))
        }
        desc.add(" ")
        if (!all.isEmpty()) {
            desc.add(Chat.format("&eLast Requested: &c" + TimeUtil.formatDuration(System.currentTimeMillis().minus(all.first().at)) + " ago"))
        } else {
            desc.add(Chat.format("&eLast Requested: &cNever!"))
        }
        desc.add(Chat.format("&7&m-------------------"))

        return desc
    }

    override fun getDisplayName(player: Player): String? {
        return Chat.format("&6Permission Handler")
    }

    override fun getData(player: Player): Short {
        return 0
    }

    override fun onClick(player: Player, slot: Int, type: ClickType) {

    }
}
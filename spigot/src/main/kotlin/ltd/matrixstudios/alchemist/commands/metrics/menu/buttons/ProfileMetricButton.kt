package ltd.matrixstudios.alchemist.commands.metrics.menu.buttons

import ltd.matrixstudios.alchemist.metric.MetricService
import ltd.matrixstudios.alchemist.util.Chat
import ltd.matrixstudios.alchemist.util.TimeUtil
import ltd.matrixstudios.alchemist.util.menu.Button
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType

class ProfileMetricButton : Button()
{

    override fun getMaterial(player: Player): Material
    {
        return Material.SIGN
    }

    override fun getDescription(player: Player): MutableList<String>
    {
        val desc = mutableListOf<String>()
        desc.add(Chat.format("&7&m-------------------------------"))
        val average = MetricService.averageMS("Profile Service")
        if (average != Long.MAX_VALUE)
        {
            desc.add(Chat.format("&6&l｜ &fAverage ms/join: &e" + average + "ms"))
        } else
        {
            desc.add(Chat.format("&6&l｜ &fAverage ms/join: &eN/A"))
        }
        val all = MetricService.getLast10("Profile Service")
        desc.add(Chat.format("&6&l｜ &fFastest Time: &e" + all.sortedBy { it.ms }.first().ms + "ms"))
        desc.add(Chat.format("&6&l｜ &fSlowest Time: &e" + all.sortedByDescending { it.ms }.first().ms + "ms"))
        desc.add(" ")
        desc.add(Chat.format("&6Last 10 Entries&7:"))
        for (metric in all)
        {
            desc.add(Chat.format("&6&l｜ &f" + metric.ms + "ms"))
        }
        desc.add(" ")
        if (!all.isEmpty())
        {
            desc.add(
                Chat.format(
                    "&6&l｜ &fLast Requested: &e" + TimeUtil.formatDuration(
                        System.currentTimeMillis().minus(all.last().at)
                    ) + " ago"
                )
            )
        } else
        {
            desc.add(Chat.format("&6&l｜ &fLast Requested: &cNever"))
        }
        desc.add(Chat.format("&7&m-------------------------------"))

        return desc
    }

    override fun getDisplayName(player: Player): String
    {
        return Chat.format("&6Profile Metrics")
    }

    override fun getData(player: Player): Short
    {
        return 0
    }

    override fun onClick(player: Player, slot: Int, type: ClickType)
    {

    }
}
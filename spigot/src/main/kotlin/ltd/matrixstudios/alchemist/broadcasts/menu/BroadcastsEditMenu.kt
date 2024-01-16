package ltd.matrixstudios.alchemist.broadcasts.menu

import ltd.matrixstudios.alchemist.broadcasts.BroadcastService
import ltd.matrixstudios.alchemist.broadcasts.model.BroadcastMessage
import ltd.matrixstudios.alchemist.util.Chat
import ltd.matrixstudios.alchemist.util.menu.Button
import ltd.matrixstudios.alchemist.util.menu.type.BorderedPaginatedMenu
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType

class BroadcastsEditMenu(val player: Player): BorderedPaginatedMenu(player)
{
    override fun getPagesButtons(player: Player): MutableMap<Int, Button>
    {
        val buttons = mutableMapOf<Int, Button>()
        val cached = BroadcastService.cached()

        cached?.broadcasts?.values?.forEach {
            buttons[buttons.size] = BroadcastButton(it, buttons.size)
        }

        return buttons
    }

    override fun getTitle(player: Player): String
    {
        return "Viewing Broadcasts..."
    }

    class BroadcastButton(val broadcast: BroadcastMessage, val index: Int) : Button()
    {
        override fun getMaterial(player: Player): Material
        {
            return Material.PAPER
        }

        override fun getDescription(player: Player): MutableList<String>?
        {
            val desc = mutableListOf<String>()

            desc.add(Chat.format("&7Identifier: &f${broadcast.id}"))
            for (line in broadcast.lines)
            {
                desc.add(Chat.format(line))
            }
            desc.add(" ")
            desc.add(Chat.format("&6Conditions"))
            if (broadcast.conditions.isEmpty())
            {
                desc.add(Chat.format("&c&l｜ &fNone"))
            } else
            {
                for (condition in broadcast.conditions)
                {
                    desc.add(Chat.format("${condition.logicGate.chatColor}&l｜ &f${condition.logicGate.displayName}&7: ${condition.logicGate.chatColor}${condition.condition}"))
                }
            }
            desc.add(" ")
            desc.add(Chat.format("&aLeft-Click to edit this auto-broadcast!"))
            desc.add(Chat.format("&cRight-Click to delete this auto-broadcast!"))

            return desc
        }

        override fun getDisplayName(player: Player): String?
        {
            return Chat.format("&e&l#${index}")
        }

        override fun getData(player: Player): Short
        {
            return 0
        }

        override fun onClick(player: Player, slot: Int, type: ClickType)
        {
            if (type.isRightClick)
            {
                val cached = BroadcastService.cached()

                if (cached != null)
                {
                    cached.broadcasts.remove(broadcast.id)
                    BroadcastService.cache(cached)
                }

                player.sendMessage(Chat.format("&cYou have just deleted the broadcast with the id &f${broadcast.id}&c."))
                BroadcastsEditMenu(player).updateMenu()
            }
        }
    }
}
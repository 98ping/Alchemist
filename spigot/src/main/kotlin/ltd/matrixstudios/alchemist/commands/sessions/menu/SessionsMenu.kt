package ltd.matrixstudios.alchemist.commands.sessions.menu

import ltd.matrixstudios.alchemist.api.AlchemistAPI
import ltd.matrixstudios.alchemist.models.sessions.Session
import ltd.matrixstudios.alchemist.util.Chat
import ltd.matrixstudios.alchemist.util.TimeUtil
import ltd.matrixstudios.alchemist.util.menu.Button
import ltd.matrixstudios.alchemist.util.menu.pagination.PaginatedMenu
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType

class SessionsMenu(val player: Player, val sessions: List<Session>) : PaginatedMenu(18, player) {
    override fun getPagesButtons(player: Player): MutableMap<Int, Button> {
        val buttons = mutableMapOf<Int, Button>()
        var index = 0

        for (session in sessions)
        {
            buttons[index++] = SessionButton(session)
        }

        return buttons
    }

    override fun getTitle(player: Player): String {
        return "Listing All Sessions"
    }

    class SessionButton(val session: Session) : Button()
    {
        override fun getMaterial(player: Player): Material {
            return Material.ENCHANTED_BOOK
        }

        override fun getDescription(player: Player): MutableList<String>? {
            val desc = mutableListOf<String>()
            desc.add(Chat.format("&6&m---------------------"))
            desc.add(Chat.format("&eTarget: &f" + AlchemistAPI.getRankDisplay(session.player)))
            desc.add(Chat.format("&eVisited: &f" + session.serversJoined.values.size + " servers"))
            if (session.leftAt != 0L)
            {
                desc.add(Chat.format("&eOn For: &f" + TimeUtil.formatDuration(session.leftAt - session.loggedInAt)))
            } else {
                desc.add(Chat.format("&eOn For: &aOnline Currently"))
            }
            desc.add(Chat.format("&6&m---------------------"))
            desc.add(Chat.format("&6Join History"))
            val entries = session.serversJoined.entries

            if (entries.isEmpty())
            {
                desc.add(Chat.format("&7- &cHas not joined any servers!"))
            } else {
                for (entry in session.serversJoined.entries)
                {
                    desc.add(Chat.format("&7- &e" + entry.value.displayName + " &7(Joined " + TimeUtil.formatDuration(System.currentTimeMillis() - entry.key) + " ago)"))
                }
            }
            desc.add(Chat.format("&6&m---------------------"))


            return desc
        }

        override fun getDisplayName(player: Player): String? {
            return Chat.format("&6Session #" + session.randomId)
        }

        override fun getData(player: Player): Short {
            return 0
        }

        override fun onClick(player: Player, slot: Int, type: ClickType) {
            return
        }
    }

}
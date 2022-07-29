package ltd.matrixstudios.alchemist.commands.party.menu

import ltd.matrixstudios.alchemist.commands.party.menu.buttons.AddPartyMemberButton
import ltd.matrixstudios.alchemist.commands.party.menu.buttons.RemovePartyMemberButton
import ltd.matrixstudios.alchemist.models.party.Party
import ltd.matrixstudios.alchemist.party.PartyInformationSuppplier
import ltd.matrixstudios.alchemist.util.Chat
import ltd.matrixstudios.alchemist.util.TimeUtil
import ltd.matrixstudios.alchemist.util.menu.Button
import ltd.matrixstudios.alchemist.util.menu.Menu
import ltd.matrixstudios.alchemist.util.menu.buttons.PlaceholderButton
import ltd.matrixstudios.alchemist.util.menu.buttons.SimpleActionButton
import org.bukkit.Material
import org.bukkit.entity.Player

class PartyMenu(val player: Player, val party: Party) : Menu(38, player) {

    override fun getButtons(player: Player): MutableMap<Int, Button> {
        val buttons = hashMapOf<Int, Button>()
        buttons[13] = PlaceholderButton(Material.SKULL_ITEM, mutableListOf(
            Chat.format("&7&m----------------"),
            Chat.format(PartyInformationSuppplier.getLeaderFancyName(party.leader)),
            Chat.format("&7&m----------------")), Chat.format("&6Leader:"), 3)

        buttons[31] = PlaceholderButton(Material.BOOK_AND_QUILL, mutableListOf(), Chat.format("&6Uptime: &f" + TimeUtil.formatDuration(System.currentTimeMillis() - party.createdAt)), 0)
        buttons[20] = AddPartyMemberButton()
        buttons[24] = RemovePartyMemberButton()

        return buttons
    }

    override fun getTitle(player: Player): String {
        return "${PartyInformationSuppplier.getLeaderFancyName(party.leader)}&6's Party"
    }
}
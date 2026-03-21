package ltd.matrixstudios.alchemist.punishment.commands.menu.impl.proof.sub

import ltd.matrixstudios.alchemist.models.grant.types.Punishment
import ltd.matrixstudios.alchemist.models.grant.types.proof.ProofEntry
import ltd.matrixstudios.alchemist.util.Chat
import ltd.matrixstudios.alchemist.util.menu.Button
import ltd.matrixstudios.alchemist.util.menu.Menu
import ltd.matrixstudios.alchemist.util.menu.buttons.SimpleActionButton
import org.bukkit.Material
import org.bukkit.entity.Player

class ProofSelectTypeMenu(val player: Player, val punishment: Punishment) : Menu(player)
{

    init
    {
        staticSize = 9
    }

    override fun getButtons(player: Player): MutableMap<Int, Button>
    {
        val buttons = mutableMapOf<Int, Button>()

        buttons[3] = SimpleActionButton(
            Material.WATCH,
            listOf(" ", Chat.format("&eClick to set your type to &6Video")).toMutableList(),
            "&6Video", 0
        ).setBody { player, i, clickType ->
            ProofInputLinkMenu(player, punishment, ProofEntry.ProofType.VIDEO).openMenu()

        }

        buttons[4] = SimpleActionButton(
            Material.PAINTING,
            listOf(" ", Chat.format("&eClick to set your type to &6Image")).toMutableList(),
            "&6Image", 0
        ).setBody { player, i, clickType ->
            ProofInputLinkMenu(player, punishment, ProofEntry.ProofType.IMAGES).openMenu()

        }

        buttons[5] = SimpleActionButton(
            Material.PAPER,
            listOf(" ", Chat.format("&eClick to set your type to &6Classified")).toMutableList(),
            "&6Classified", 0
        ).setBody { player, i, clickType ->
            ProofInputLinkMenu(player, punishment, ProofEntry.ProofType.STAFF_SUPERVISION).openMenu()

        }

        return buttons

    }

    override fun getTitle(player: Player): String
    {
        return "Select Proof Type"
    }
}
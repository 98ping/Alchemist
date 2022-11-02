package ltd.matrixstudios.alchemist.commands.punishments.menu.impl.proof.sub

import ltd.matrixstudios.alchemist.models.grant.types.Punishment
import ltd.matrixstudios.alchemist.models.grant.types.proof.ProofEntry
import ltd.matrixstudios.alchemist.util.Chat
import ltd.matrixstudios.alchemist.util.menu.Button
import ltd.matrixstudios.alchemist.util.menu.Menu
import ltd.matrixstudios.alchemist.util.menu.buttons.SimpleActionButton
import ltd.matrixstudios.alchemist.util.menu.buttons.SkullButton
import org.bukkit.entity.Player
import java.util.*

class ProofSelectTypeMenu(val player: Player, val punishment: Punishment) : Menu(player) {

    init {
        staticSize = 9
    }
    override fun getButtons(player: Player): MutableMap<Int, Button> {
        val buttons = mutableMapOf<Int, Button>()


        buttons[2] = SkullButton(
            "ewogICJ0aW1lc3RhbXAiIDogMTU5NDY4ODM2Njc4MCwKICAicHJvZmlsZUlkIiA6ICJiNzQ3OWJhZTI5YzQ0YjIzYmE1NjI4MzM3OGYwZTNjNiIsCiAgInByb2ZpbGVOYW1lIiA6ICJTeWxlZXgiLAogICJzaWduYXR1cmVSZXF1aXJlZCIgOiB0cnVlLAogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzU2YThkYzM3OGJkMzRjODVlNzc3NTlkYTM1OWExOGMxOTUzNTAxNTJlMmY4MzI0NjYxMGFhYjhlYTRiZDg0ZSIKICAgIH0KICB9Cn0=",
            listOf(" ", Chat.format("&eClick to set your type to &6Video")).toMutableList(),
            "&6Video").setBody {
                player, i, clickType -> ProofInputLinkMenu(player, punishment, ProofEntry.ProofType.VIDEO).openMenu()

        }

        buttons[4] = SkullButton(
            "eyJ0aW1lc3RhbXAiOjE1NTA0NjU3NzQ1OTIsInByb2ZpbGVJZCI6IjkxZjA0ZmU5MGYzNjQzYjU4ZjIwZTMzNzVmODZkMzllIiwicHJvZmlsZU5hbWUiOiJTdG9ybVN0b3JteSIsInNpZ25hdHVyZVJlcXVpcmVkIjp0cnVlLCJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMmQ2MjVlOWE2MmUwNWI2ZGE0NjVlY2RjNGRjMDRiOWJmZjk0ZGNhNzVkYTA0ZTJkZjM2NzU5N2Y2ZGQ5YWZiMyJ9fX0=",
            listOf(" ", Chat.format("&eClick to set your type to &6Image")).toMutableList(),
            "&6Image").setBody {
                player, i, clickType -> ProofInputLinkMenu(player, punishment, ProofEntry.ProofType.IMAGES).openMenu()

        }

        buttons[6] = SkullButton(
            "eyJ0aW1lc3RhbXAiOjE1NzIyMjkwMDE5MjgsInByb2ZpbGVJZCI6ImZkNjBmMzZmNTg2MTRmMTJiM2NkNDdjMmQ4NTUyOTlhIiwicHJvZmlsZU5hbWUiOiJSZWFkIiwic2lnbmF0dXJlUmVxdWlyZWQiOnRydWUsInRleHR1cmVzIjp7IlNLSU4iOnsidXJsIjoiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS9kYzI4YzQ4MjcxMDU2MWIxMDdlYjgzMTc5ZGJlYjc0ZjJhMGNjNDFlZDQ5MGYzOWNkNGVkZmUwZTA0N2Q2ZjBjIn19fQ==",
            listOf(" ", Chat.format("&eClick to set your type to &6Classified")).toMutableList(),
            "&6Classified").setBody {
                player, i, clickType ->  ProofInputLinkMenu(player, punishment, ProofEntry.ProofType.STAFF_SUPERVISION).openMenu()

        }

        return buttons

    }

    override fun getTitle(player: Player): String {
        return "Select Proof Type"
    }
}
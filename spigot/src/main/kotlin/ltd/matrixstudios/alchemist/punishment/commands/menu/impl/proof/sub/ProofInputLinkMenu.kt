package ltd.matrixstudios.alchemist.punishment.commands.menu.impl.proof.sub

import ltd.matrixstudios.alchemist.api.AlchemistAPI
import ltd.matrixstudios.alchemist.models.grant.types.Punishment
import ltd.matrixstudios.alchemist.models.grant.types.proof.ProofEntry
import ltd.matrixstudios.alchemist.service.expirable.PunishmentService
import ltd.matrixstudios.alchemist.util.Chat
import ltd.matrixstudios.alchemist.util.InputPrompt
import ltd.matrixstudios.alchemist.util.menu.Button
import ltd.matrixstudios.alchemist.util.menu.Menu
import ltd.matrixstudios.alchemist.util.menu.buttons.SkullButton
import org.bukkit.entity.Player

class ProofInputLinkMenu(val player: Player, val punishment: Punishment, val proofType: ProofEntry.ProofType) :
    Menu(player)
{

    init
    {
        staticSize = 9
    }

    override fun getButtons(player: Player): MutableMap<Int, Button>
    {
        val buttons = mutableMapOf<Int, Button>()

        buttons[3] = SkullButton(
            "ewogICJ0aW1lc3RhbXAiIDogMTY1NjAyNzIyNzA4NiwKICAicHJvZmlsZUlkIiA6ICI4N2RiMmNjNWY4Y2I0MjI4YTU0OGRiMzJlM2Y0NmFmNiIsCiAgInByb2ZpbGVOYW1lIiA6ICJZVG1hdGlhczEzbG9sIiwKICAic2lnbmF0dXJlUmVxdWlyZWQiIDogdHJ1ZSwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlLzJhYjc1YzRhZTBmNmFmYTNkZmUyYmExODJlMTA5MzVmMDAwYmEzNTQ5YzUzMjI5OWY5YjUwMjUxM2U3Zjk5Y2UiCiAgICB9CiAgfQp9",
            listOf(" ", Chat.format("&eClick to finalize the process.")).toMutableList(),
            "&6Add Link"
        ).setBody { player, i, clickType ->

            val profile = AlchemistAPI.quickFindProfile(player.uniqueId).get()

            if (profile == null)
            {
                player.sendMessage(Chat.format("&cYour profile is not loaded!"))
                return@setBody
            }

            InputPrompt()
                .withText(Chat.format("&aType in any link you are going to add to the punishment proof section!"))
                .acceptInput {
                    val entry = ProofEntry(
                        it, proofType, System.currentTimeMillis(), profile.uuid,
                        proofType == ProofEntry.ProofType.STAFF_SUPERVISION
                    )

                    punishment.proof.add(entry)
                    PunishmentService.save(punishment)
                    PunishmentService.recalculateUUID(punishment.target)
                    player.sendMessage(Chat.format("&aSuccessfully added proof to this punishment!"))
                }.start(player)

        }

        buttons[5] = SkullButton(
            "eyJ0aW1lc3RhbXAiOjE1NzIyMjkwMDE5MjgsInByb2ZpbGVJZCI6ImZkNjBmMzZmNTg2MTRmMTJiM2NkNDdjMmQ4NTUyOTlhIiwicHJvZmlsZU5hbWUiOiJSZWFkIiwic2lnbmF0dXJlUmVxdWlyZWQiOnRydWUsInRleHR1cmVzIjp7IlNLSU4iOnsidXJsIjoiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS9kYzI4YzQ4MjcxMDU2MWIxMDdlYjgzMTc5ZGJlYjc0ZjJhMGNjNDFlZDQ5MGYzOWNkNGVkZmUwZTA0N2Q2ZjBjIn19fQ==",
            listOf(" ", Chat.format("&eClick to &cCancel &e this process")).toMutableList(),
            "&6Cancel Process"
        ).setBody { player, i, clickType ->
            player.closeInventory()
        }

        return buttons
    }

    override fun getTitle(player: Player): String
    {
        return "Input a Link"
    }
}
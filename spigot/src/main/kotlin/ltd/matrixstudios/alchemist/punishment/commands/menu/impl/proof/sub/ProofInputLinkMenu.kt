package ltd.matrixstudios.alchemist.punishment.commands.menu.impl.proof.sub

import ltd.matrixstudios.alchemist.api.AlchemistAPI
import ltd.matrixstudios.alchemist.models.grant.types.Punishment
import ltd.matrixstudios.alchemist.models.grant.types.proof.ProofEntry
import ltd.matrixstudios.alchemist.service.expirable.PunishmentService
import ltd.matrixstudios.alchemist.util.Chat
import ltd.matrixstudios.alchemist.util.InputPrompt
import ltd.matrixstudios.alchemist.util.menu.Button
import ltd.matrixstudios.alchemist.util.menu.Menu
import ltd.matrixstudios.alchemist.util.menu.buttons.SimpleActionButton
import org.bukkit.Material
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

        buttons[3] = SimpleActionButton(
            Material.BOOK_AND_QUILL,
            listOf(" ", Chat.format("&eClick to finalize the process.")).toMutableList(),
            "&6Add Link", 0
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
                        proofType == ProofEntry.ProofType.STAFF_SUPERVISION,
                    )

                    punishment.proof.add(entry)
                    PunishmentService.save(punishment)
                    PunishmentService.recalculateUUID(punishment.target)
                    player.sendMessage(Chat.format("&aSuccessfully added proof to this punishment!"))
                }.start(player)

        }

        buttons[5] = SimpleActionButton(
            Material.REDSTONE_BLOCK,
            listOf(" ", Chat.format("&eClick to &cCancel &e this process")).toMutableList(),
            "&6Cancel Process", 0
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
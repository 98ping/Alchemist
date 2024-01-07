package ltd.matrixstudios.alchemist.punishment.commands.menu.impl.proof.inspection

import ltd.matrixstudios.alchemist.Alchemist
import ltd.matrixstudios.alchemist.api.AlchemistAPI
import ltd.matrixstudios.alchemist.models.grant.types.Punishment
import ltd.matrixstudios.alchemist.models.grant.types.proof.ProofEntry
import ltd.matrixstudios.alchemist.punishment.commands.menu.impl.proof.ProofMenu
import ltd.matrixstudios.alchemist.punishment.commands.menu.impl.proof.sub.ProofSelectTypeMenu
import ltd.matrixstudios.alchemist.service.expirable.PunishmentService
import ltd.matrixstudios.alchemist.util.Chat
import ltd.matrixstudios.alchemist.util.menu.Button
import ltd.matrixstudios.alchemist.util.menu.Menu
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType

class ProofInspectionMenu(val player: Player, val punishment: Punishment, val proofEntry: ProofEntry) : Menu(player)
{

    init
    {
        staticSize = 27
        placeholder = true
    }

    override fun getButtons(player: Player): MutableMap<Int, Button>
    {
        val buttons = mutableMapOf<Int, Button>()

        buttons[3] = ProofInspectionButton(ProofEntry.ReviewStatus.ACCEPTED, punishment, proofEntry)
        buttons[4] = ProofInspectionButton(ProofEntry.ReviewStatus.REPUNISHED, punishment, proofEntry)
        buttons[5] = ProofInspectionButton(ProofEntry.ReviewStatus.REJECTED, punishment, proofEntry)

        return buttons
    }

    override fun getTitle(player: Player): String
    {
        return "Inspecting Proof..."
    }


    class ProofInspectionButton(private val outcome: ProofEntry.ReviewStatus, val punishment: Punishment, val proofEntry: ProofEntry) : Button()
    {
        override fun getMaterial(player: Player): Material
        {
            return Material.WOOL
        }

        override fun getDescription(player: Player): MutableList<String>?
        {
            return mutableListOf(
                Chat.format("&a&lLeft-Click &ato set the proof status to ${outcome.displayName}")
            )
        }

        override fun getDisplayName(player: Player): String?
        {
            return Chat.format(outcome.displayName)
        }

        override fun getData(player: Player): Short
        {
            return AlchemistAPI.getWoolColor(outcome.displayName).woolData.toShort()
        }

        override fun onClick(player: Player, slot: Int, type: ClickType)
        {
            punishment.proof.remove(proofEntry)

            proofEntry.reviewStatus = outcome
            proofEntry.reviewedAt = System.currentTimeMillis()
            proofEntry.reviewer = player.uniqueId

            punishment.proof.add(proofEntry)
            PunishmentService.save(punishment)

            player.sendMessage(Chat.format("&aYou have just edited the proof review status of #${punishment.easyFindId}"))
            ProofMenu(player, punishment).updateMenu()
        }

    }
}
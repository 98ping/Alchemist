package ltd.matrixstudios.alchemist.commands.punishments.menu.impl.proof

import ltd.matrixstudios.alchemist.api.AlchemistAPI
import ltd.matrixstudios.alchemist.commands.punishments.menu.impl.proof.sub.ProofSelectTypeMenu
import ltd.matrixstudios.alchemist.models.grant.types.Punishment
import ltd.matrixstudios.alchemist.models.grant.types.proof.ProofEntry
import ltd.matrixstudios.alchemist.util.Chat
import ltd.matrixstudios.alchemist.util.menu.Button
import ltd.matrixstudios.alchemist.util.menu.buttons.SimpleActionButton
import ltd.matrixstudios.alchemist.util.menu.pagination.PaginatedMenu
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import java.util.Date

class ProofMenu(val player: Player, val punishment: Punishment) : PaginatedMenu(18, player) {

    override fun getHeaderItems(player: Player): MutableMap<Int, Button> {
        val buttons = hashMapOf<Int, Button>()
        buttons[4] = SimpleActionButton(Material.NETHER_STAR, mutableListOf(), "&aClick to add Proof", 0).setBody {
            player, i, clickType -> ProofSelectTypeMenu(player, punishment).openMenu()
        }

        return buttons
    }

    override fun getPagesButtons(player: Player): MutableMap<Int, Button> {
        val buttons = mutableMapOf<Int, Button>()

        var index = 0
        for (proof in punishment.proof)
        {
            buttons[index++] = ProofButton(proof)
        }

        return buttons
    }

    override fun getTitle(player: Player): String {
        return "Proof of: ${punishment.easyFindId}"
    }

    class ProofButton(val proofEntry: ProofEntry) : Button()
    {
        override fun getMaterial(player: Player): Material {
            return Material.PAPER
        }

        override fun getDescription(player: Player): MutableList<String>? {
            val desc = mutableListOf<String>()
            if (!proofEntry.shouldBeConfidential) {
                desc.add(Chat.format("&6&m---------------------"))
                desc.add(Chat.format("&eType: " + proofEntry.type.displayName))
                desc.add(Chat.format("&eAt: &f" + Date(proofEntry.addedAt)))
                desc.add(Chat.format("&eBy: &f" + AlchemistAPI.getRankDisplay(proofEntry.whoAdded)))
                desc.add(" ")
                desc.add(Chat.format("&aClick to view link"))
                desc.add(Chat.format("&6&m---------------------"))
            } else {
                desc.add(Chat.format("&6&m---------------------"))
                desc.add(Chat.format("&eType: &f" + proofEntry.type.displayName))
                desc.add(Chat.format("&eAt: &f" + Date(proofEntry.addedAt)))
                desc.add(Chat.format("&eBy: &f" + AlchemistAPI.getRankDisplay(proofEntry.whoAdded)))
                desc.add(" ")
                if (player.hasPermission("alchemist.punishments.proof"))
                {
                    desc.add(Chat.format("&aClick to view link"))
                } else {
                    desc.add(Chat.format("&cConfidential Proof"))
                }
                desc.add(Chat.format("&6&m---------------------"))
            }

            return desc
        }

        override fun getDisplayName(player: Player): String? {
            return Chat.format(AlchemistAPI.getRankDisplay(proofEntry.whoAdded))
        }

        override fun getData(player: Player): Short {
            return 0
        }

        override fun onClick(player: Player, slot: Int, type: ClickType) {
            if (!proofEntry.shouldBeConfidential)
            {
                player.sendMessage(proofEntry.link)
            } else {
                if (player.hasPermission("alchemist.punishments.proof")) {
                    player.sendMessage(proofEntry.link)
                }
            }
        }


    }
}
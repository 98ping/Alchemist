package ltd.matrixstudios.alchemist.commands.rank.menu.sub

import ltd.matrixstudios.alchemist.commands.filter.menu.editor.punishments.PunishmentTypeSelectionMenu
import ltd.matrixstudios.alchemist.models.filter.Filter
import ltd.matrixstudios.alchemist.models.ranks.Rank
import ltd.matrixstudios.alchemist.punishments.PunishmentType
import ltd.matrixstudios.alchemist.redis.AsynchronousRedisSender
import ltd.matrixstudios.alchemist.redis.impl.caches.RefreshRankPacket
import ltd.matrixstudios.alchemist.service.filter.FilterService
import ltd.matrixstudios.alchemist.service.ranks.RankService
import ltd.matrixstudios.alchemist.util.Chat
import ltd.matrixstudios.alchemist.util.InputPrompt
import ltd.matrixstudios.alchemist.util.TimeUtil
import ltd.matrixstudios.alchemist.util.menu.Button
import ltd.matrixstudios.alchemist.util.menu.Menu
import ltd.matrixstudios.alchemist.util.menu.buttons.SimpleActionButton
import org.bukkit.Material
import org.bukkit.entity.Player
import java.util.*

class RankEditPropertiesMenu(val player: Player, val rank: Rank) : Menu(player) {

    init {
        placeholder = true
        staticSize = 27
    }

    override fun getButtons(player: Player): MutableMap<Int, Button> {
        val buttons = mutableMapOf<Int, Button>()

        buttons[10] = SimpleActionButton(
            Material.LADDER,
            mutableListOf(
                " ",
                Chat.format("&eChange the priority of the rank"),
                Chat.format("&eCurrently: &f" + rank.weight),
            ),
            "&eChange Priority", 0
        ).setBody { player, slot, clicktype ->
            InputPrompt()
                .withText(Chat.format("&aType in the new priority for this rank!"))
                .acceptInput {
                    var newPriority = 0

                    try {
                        newPriority = Integer.parseInt(it)
                    } catch (e: java.lang.NumberFormatException)
                    {
                        player.sendMessage(Chat.format("&cThis is not a number!"))
                        return@acceptInput
                    }

                    rank.weight = newPriority
                    RankService.save(rank)
                    AsynchronousRedisSender.send(RefreshRankPacket())
                    player.sendMessage(Chat.format("&aUpdated the priority of " + rank.color + rank.displayName))
                    RankEditPropertiesMenu(player, rank).openMenu()
                }.start(player)
        }

        buttons[11] = SimpleActionButton(
            Material.NETHER_STAR,
            mutableListOf(
                " ",
                Chat.format("&eChange staff status"),
                Chat.format("&eCurrently: &f" + if (rank.staff) "&aTrue" else "&cFalse")
            ),
            "&eChange Staff Status", 0
        ).setBody { player, slot, clicktype ->
            val otherBool = !rank.staff
            rank.staff = otherBool
            RankService.save(rank)
            AsynchronousRedisSender.send(RefreshRankPacket())
            player.sendMessage(Chat.format("&eUpdate the staff status of &6${rank.color}${rank.displayName} &eto " + if (otherBool) "&aTrue" else "&cFalse"))
            RankEditPropertiesMenu(player, rank).openMenu()
        }

        buttons[12] = SimpleActionButton(
            Material.BOOK,
            mutableListOf(
                " ",
                Chat.format("&eChange the prefix of the rank"),
                Chat.format("&eCurrently: &f" + rank.prefix),
            ),
            "&eChange Prefix", 0
        ).setBody { player, slot, clicktype ->
            InputPrompt()
                .withText(Chat.format("&aType in the new prefix for this rank! (Color Codes Supported)"))
                .acceptInput {
                    rank.prefix = it
                    RankService.save(rank)
                    AsynchronousRedisSender.send(RefreshRankPacket())
                    player.sendMessage(Chat.format("&aUpdated the prefix of " + rank.color + rank.displayName))
                    RankEditPropertiesMenu(player, rank).openMenu()
                }.start(player)
        }

        buttons[13] = SimpleActionButton(
            Material.EXP_BOTTLE,
            mutableListOf(
                " ",
                Chat.format("&eChange the color of the rank"),
                Chat.format("&eCurrently: &f" + rank.color + "This"),
            ),
            "&eChange Color", 0
        ).setBody { player, slot, clicktype ->
            InputPrompt()
                .withText(Chat.format("&aType in the new color for this rank!"))
                .acceptInput {
                    rank.color = it
                    RankService.save(rank)
                    AsynchronousRedisSender.send(RefreshRankPacket())
                    player.sendMessage(Chat.format("&aUpdated the color of " + rank.color + rank.displayName))
                    RankEditPropertiesMenu(player, rank).openMenu()
                }.start(player)
        }

        buttons[14] = SimpleActionButton(
            Material.NAME_TAG,
            mutableListOf(
                " ",
                Chat.format("&eChange the display name of the rank"),
                Chat.format("&eCurrently: &f" + rank.displayName),
            ),
            "&eChange Display Name", 0
        ).setBody { player, slot, clicktype ->
            InputPrompt()
                .withText(Chat.format("&aType in the new display name for this rank!"))
                .acceptInput {
                    rank.displayName = it
                    RankService.save(rank)
                    AsynchronousRedisSender.send(RefreshRankPacket())
                    player.sendMessage(Chat.format("&aUpdated the display name of " + rank.color + rank.displayName))
                    RankEditPropertiesMenu(player, rank).openMenu()
                }.start(player)
        }

        return buttons
    }

    override fun getTitle(player: Player): String {
        return "Editing: ${rank.displayName}"
    }
}
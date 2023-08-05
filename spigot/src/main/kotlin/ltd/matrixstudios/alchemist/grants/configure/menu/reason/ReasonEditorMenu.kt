package ltd.matrixstudios.alchemist.grants.configure.menu.reason

import ltd.matrixstudios.alchemist.grants.GrantConfigurationService
import ltd.matrixstudios.alchemist.grants.models.GrantDurationModel
import ltd.matrixstudios.alchemist.grants.models.GrantReasonModel
import ltd.matrixstudios.alchemist.util.Chat
import ltd.matrixstudios.alchemist.util.InputPrompt
import ltd.matrixstudios.alchemist.util.menu.Button
import ltd.matrixstudios.alchemist.util.menu.Menu
import ltd.matrixstudios.alchemist.util.menu.buttons.SimpleActionButton
import org.bukkit.Material
import org.bukkit.entity.Player


/**
 * Class created on 8/4/2023

 * @author 98ping
 * @project Alchemist
 * @website https://solo.to/redis
 */
class ReasonEditorMenu(val model: GrantReasonModel, val player: Player) : Menu(player) {

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
                Chat.format("&7Change the menu positions"),
                Chat.format("&7of this grant reason"),
                " ",
                Chat.format("&eCurrently: &f" + model.menuSlot),
                " "
            ),
            "&eChange Menu Position", 0
        ).setBody { player, slot, clicktype ->
            InputPrompt()
                .withText(Chat.format("&aType in the new position for this reason!"))
                .acceptInput {
                    var pos = 0

                    try {
                        pos = Integer.parseInt(it)
                    } catch (e: java.lang.NumberFormatException)
                    {
                        player.sendMessage(Chat.format("&cThis is not a number!"))
                        return@acceptInput
                    }

                    model.menuSlot = pos
                    GrantConfigurationService.saveReasonModel(model)
                    player.sendMessage(Chat.format("&aUpdated the menu position of this reason to &f$pos"))
                    ReasonEditorMenu(model, player).openMenu()
                }.start(player)
        }

        buttons[11] = SimpleActionButton(
            Material.DIAMOND,
            mutableListOf(
                " ",
                Chat.format("&7Change the display item of this"),
                Chat.format("&7grant reason"),
                " ",
                Chat.format("&eCurrently: &f" + model.item),
                " "
            ),
            "&eChange Display Item", 0
        ).setBody { player, slot, clicktype ->
            InputPrompt()
                .withText(Chat.format("&aType in the new display item for this reason!"))
                .acceptInput {
                    model.item = it
                    GrantConfigurationService.saveReasonModel(model)
                    player.sendMessage(Chat.format("&aUpdated the menu item of this reason to &f$it"))
                    ReasonEditorMenu(model, player).openMenu()
                }.start(player)
        }

        buttons[12] = SimpleActionButton(
            Material.PAPER,
            mutableListOf(
                " ",
                Chat.format("&7Change the display name of this"),
                Chat.format("&7grant reason"),
                " ",
                Chat.format("&eCurrently: &f" + model.displayName),
                " "
            ),
            "&eChange Display Name", 0
        ).setBody { player, slot, clicktype ->
            InputPrompt()
                .withText(Chat.format("&aType in the new display name for this reason!"))
                .acceptInput {
                    model.displayName = it
                    GrantConfigurationService.saveReasonModel(model)
                    player.sendMessage(Chat.format("&aUpdated the display name of this reason to &f$it"))
                    ReasonEditorMenu(model, player).openMenu()
                }.start(player)
        }

        buttons[13] = SimpleActionButton(
            Material.REDSTONE,
            mutableListOf(
                " ",
                Chat.format("&7Change the data of the item"),
                Chat.format("&7that shows in the grant reason menu"),
                " ",
                Chat.format("&eCurrently: &f" + model.data),
                " "
            ),
            "&eChange Item Data", 0
        ).setBody { player, slot, clicktype ->
            InputPrompt()
                .withText(Chat.format("&aType in the new item data for this reason!"))
                .acceptInput {
                    var pos = 0

                    try {
                        pos = Integer.parseInt(it)
                    } catch (e: java.lang.NumberFormatException)
                    {
                        player.sendMessage(Chat.format("&cThis is not a number!"))
                        return@acceptInput
                    }

                    model.data = pos
                    GrantConfigurationService.saveReasonModel(model)
                    player.sendMessage(Chat.format("&aUpdated the item data of this reason to &f$pos"))
                    ReasonEditorMenu(model, player).openMenu()
                }.start(player)
        }

        buttons[14] = SimpleActionButton(
            Material.COMPASS,
            mutableListOf(
                " ",
                Chat.format("&7Change the reason of this"),
                Chat.format("&7grant reason"),
                " ",
                Chat.format("&eCurrently: &f" + model.reason),
                " "
            ),
            "&eChange Reason", 0
        ).setBody { player, slot, clicktype ->
            InputPrompt()
                .withText(Chat.format("&aType in the new reason for this grant reason!"))
                .acceptInput {
                    model.reason = it
                    GrantConfigurationService.saveReasonModel(model)
                    player.sendMessage(Chat.format("&aUpdated the display name of this reason to &f$it"))
                    ReasonEditorMenu(model, player).openMenu()
                }.start(player)
        }


        return buttons
    }

    override fun getTitle(player: Player): String {
        return "Edit Reason"
    }
}
package ltd.matrixstudios.alchemist.broadcasts.menu.condition

import ltd.matrixstudios.alchemist.api.AlchemistAPI
import ltd.matrixstudios.alchemist.broadcasts.BroadcastService
import ltd.matrixstudios.alchemist.broadcasts.condition.BroadcastCondition
import ltd.matrixstudios.alchemist.broadcasts.model.BroadcastMessage
import ltd.matrixstudios.alchemist.util.Chat
import ltd.matrixstudios.alchemist.util.InputPrompt
import ltd.matrixstudios.alchemist.util.menu.Button
import ltd.matrixstudios.alchemist.util.menu.buttons.SimpleActionButton
import ltd.matrixstudios.alchemist.util.menu.pagination.PaginatedMenu
import org.bukkit.Material
import org.bukkit.entity.Player

/**
 * Class created on 1/17/2024

 * @author 98ping
 * @project Alchemist
 * @website https://solo.to/redis
 */
class ConditionEditorMenu(val player: Player, private val broadcast: BroadcastMessage) : PaginatedMenu(18, player) {
    override fun getPagesButtons(player: Player): MutableMap<Int, Button> {
        val buttons = mutableMapOf<Int, Button>()

        broadcast.conditions.forEach {
            buttons[buttons.size] = SimpleActionButton(
                Material.WOOL,
                mutableListOf(
                    Chat.format("&7Condition: &f${it.condition}"),
                    Chat.format("&7Reference: &f${it.reference ?: "&cNone"}"),
                    Chat.format("&7Logic Gate: &f${it.logicGate.chatColor}${it.logicGate.displayName}"),
                    "",
                    Chat.format("&2Left-Click to change condition"),
                    Chat.format("&aShift-Left-Click to change logic gate"),
                    Chat.format("&eShift-Right-Click to change reference condition"),
                    Chat.format("&cRight-Click to delete condition")
                ),
                Chat.format("&a&l${it.condition}"),
                AlchemistAPI.getWoolColor(it.logicGate.chatColor).woolData.toShort()
            )
        }

        return buttons
    }

    override fun getHeaderItems(player: Player): MutableMap<Int, Button> {
        return mutableMapOf(
            4 to SimpleActionButton(
                Material.PAINTING,
                mutableListOf(),
                Chat.format("&a&lCreate New Condition"),
                0
            ).setBody { _, _, _ ->
                InputPrompt()
                    .withText(Chat.format("&eEnter the condition that you want this broadcast to have..."))
                    .acceptInput { input ->
                        val cached = BroadcastService.cached()
                            ?: return@acceptInput

                        broadcast.conditions.add(BroadcastCondition(BroadcastCondition.LogicGate.And, input))
                        cached.broadcasts[broadcast.id] = broadcast

                        BroadcastService.cache(cached)
                        player.sendMessage(Chat.format("&aYou have just created a new condition for this broadcast!"))

                        ConditionEditorMenu(player, broadcast).updateMenu()
                    }.start(player)
            }
        )
    }

    override fun getTitle(player: Player): String {
        return "Editing Broadcast Conditions"
    }
}
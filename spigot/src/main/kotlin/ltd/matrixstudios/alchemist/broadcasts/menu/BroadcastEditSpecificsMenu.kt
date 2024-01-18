package ltd.matrixstudios.alchemist.broadcasts.menu

import ltd.matrixstudios.alchemist.broadcasts.menu.condition.ConditionEditorMenu
import ltd.matrixstudios.alchemist.broadcasts.menu.lines.EditBroadcastLinesMenu
import ltd.matrixstudios.alchemist.broadcasts.model.BroadcastMessage
import ltd.matrixstudios.alchemist.util.Chat
import ltd.matrixstudios.alchemist.util.menu.Button
import ltd.matrixstudios.alchemist.util.menu.Menu
import ltd.matrixstudios.alchemist.util.menu.buttons.SimpleActionButton
import org.bukkit.Material
import org.bukkit.entity.Player

/**
 * Class created on 1/16/2024

 * @author 98ping
 * @project Alchemist
 * @website https://solo.to/redis
 */
class BroadcastEditSpecificsMenu(val player: Player, private val broadcast: BroadcastMessage) : Menu(player) {
    init {
        placeholder = true
        staticSize = 27
    }

    override fun getButtons(player: Player): MutableMap<Int, Button> {
        return mutableMapOf(
            10 to SimpleActionButton(
                Material.PAPER,
                broadcast.lines.map { Chat.format(it) }.toMutableList().apply {
                    this.addAll(
                        listOf(
                            "",
                            Chat.format("&aClick to edit lines!")
                        )
                    )
                },
                Chat.format("&e&lEdit Lines"),
                0
            ).setBody { _, _, _ ->
                EditBroadcastLinesMenu(player, broadcast).updateMenu()
            },

            11 to SimpleActionButton(
                Material.REDSTONE_BLOCK,
                broadcast.conditions.map {
                    Chat.format("${it.logicGate.chatColor}&l｜ &f${it.logicGate.displayName}&7: ${it.condition}")
                }.toMutableList().apply {
                    this.addAll(
                        listOf(
                            "",
                            Chat.format("&aClick to edit conditions")
                        )
                    )
                },
                Chat.format("&e&lEdit Conditions"),
                0
            ).setBody { _, _, _ ->
                ConditionEditorMenu(player, broadcast).updateMenu()
            }
        )
    }

    override fun getTitle(player: Player): String {
        return "Editing a Broadcast..."
    }
}
package ltd.matrixstudios.alchemist.util.menu.type

import ltd.matrixstudios.alchemist.util.Chat
import ltd.matrixstudios.alchemist.util.InputPrompt
import ltd.matrixstudios.alchemist.util.menu.Button
import ltd.matrixstudios.alchemist.util.menu.pagination.PaginatedMenu
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import java.util.*

/**
 * Class created on 1/17/2024

 * @author 98ping
 * @project Alchemist
 * @website https://solo.to/redis
 */
abstract class TextEditorMenu(private val lines: LinkedList<String>, val player: Player) : PaginatedMenu(18, player) {

    override fun getPagesButtons(player: Player): MutableMap<Int, Button> {
        val buttons = hashMapOf<Int, Button>()

        var index = 0

        for (line in lines)
        {
            buttons[index++] = LineButton(line, lines, this)
        }

        return buttons
    }

    override fun getTitle(player: Player): String {
        return "Editing Lore"
    }

    abstract fun onSave(player: Player, lines: LinkedList<String>)

    override fun getHeaderItems(player: Player): MutableMap<Int, Button> {
        val buttons = hashMapOf<Int, Button>()

        buttons[4] = CreateNewLineButton(lines, this)

        return buttons
    }

    class LineButton(val line: String, val totalStrings: LinkedList<String>, val menu: TextEditorMenu) : Button()
    {
        override fun getMaterial(player: Player): Material {
            return Material.PAPER
        }

        override fun getDescription(player: Player): MutableList<String>? {
            val desc = mutableListOf<String>()
            desc.add(" ")
            desc.add(Chat.format("&cLeft-Click to move back 1 space"))
            desc.add(Chat.format("&aRight-Click to move up 1 space"))
            desc.add(Chat.format("&eQ to delete the string"))
            desc.add(" ")

            return desc
        }

        override fun getDisplayName(player: Player): String? {
            return Chat.format(line)
        }

        override fun getData(player: Player): Short {
            return 0
        }

        override fun onClick(player: Player, slot: Int, type: ClickType) {
            if (type == ClickType.DROP)
            {
                totalStrings.remove(line)

                menu.onSave(player, totalStrings)

                return
            }

            if (!type.isShiftClick) {
                if (!(type.isLeftClick || type.isRightClick)) {
                    return
                }

                val index = totalStrings.indexOf(line)

                val canShiftLeft = index > 0
                val canShiftRight = index < totalStrings.lastIndex

                val indexMod = if (canShiftLeft && type.isLeftClick) {
                    -1
                } else if (canShiftRight && type.isRightClick) {
                    1
                } else {
                    0
                }

                if (indexMod != 0) {
                    val line = totalStrings.removeAt(index)
                    totalStrings.add(index + indexMod, line)

                    menu.onSave(player, totalStrings)
                }
            }
        }

    }

    class CreateNewLineButton(val lines: LinkedList<String>, val menu: TextEditorMenu) : Button()
    {
        override fun getMaterial(player: Player): Material {
            return Material.NETHER_STAR
        }

        override fun getDescription(player: Player): MutableList<String>? {
            return mutableListOf()
        }

        override fun getDisplayName(player: Player): String? {
            return Chat.format("&aAdd a Line")
        }

        override fun getData(player: Player): Short {
            return 0
        }

        override fun onClick(player: Player, slot: Int, type: ClickType) {
            InputPrompt()
                .withText(Chat.format("&aEnter new line here:"))
                .acceptInput {
                    lines.add(it)

                    menu.onSave(player, lines)
                }.start(player)
        }
    }
}
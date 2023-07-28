package ltd.matrixstudios.alchemist.util.menu.buttons

import ltd.matrixstudios.alchemist.util.Chat
import ltd.matrixstudios.alchemist.util.menu.Button
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType

class SimpleActionButton(
    val material: Material,
    val description: MutableList<String>,
    val name: String, val data: Short,
) : Button() {

    var body: ((Player, Int, ClickType) -> Unit)? = null
    fun setBody(body: ((Player, Int, ClickType) -> Unit)?) : SimpleActionButton {
        return this.apply { this.body = body }
    }
    override fun getMaterial(player: Player): Material {
        return material
    }

    override fun getDescription(player: Player): MutableList<String>? {
        return description
    }

    override fun getDisplayName(player: Player): String? {
        return Chat.format(name)
    }

    override fun getData(player: Player): Short {
        return data
    }

    override fun onClick(player: Player, slot: Int, type: ClickType) {
        if (body != null)
        {
            body?.invoke(player, slot, type)
        }
    }
}
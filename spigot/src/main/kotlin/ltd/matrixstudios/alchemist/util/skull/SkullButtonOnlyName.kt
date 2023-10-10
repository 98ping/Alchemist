package ltd.matrixstudios.alchemist.util.skull

import ltd.matrixstudios.alchemist.util.Chat
import ltd.matrixstudios.alchemist.util.items.ItemBuilder
import ltd.matrixstudios.alchemist.util.menu.Button
import ltd.matrixstudios.alchemist.util.menu.buttons.SimpleActionButton
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import org.bukkit.inventory.ItemStack

class SkullButtonOnlyName(
    var owner: String,
    var displayName: String,
    var lore: MutableList<String>
) : Button()
{
    var body: ((Player, Int, ClickType) -> Unit)? = null

    fun setBody(body: ((Player, Int, ClickType) -> Unit)?) : SkullButtonOnlyName
    {
        return this.apply { this.body = body }
    }

    override fun getButtonItem(player: Player): ItemStack?
    {
        return ItemBuilder.copyOf(SkullUtil.generate(owner, Chat.format(displayName))).setLore(lore).build()
    }
    override fun getMaterial(player: Player): Material
    {
        return Material.DIAMOND_SWORD
    }

    override fun getDescription(player: Player): MutableList<String>?
    {
        return mutableListOf()
    }

    override fun getDisplayName(player: Player): String?
    {
        return "&cError"
    }

    override fun getData(player: Player): Short
    {
        return 0
    }

    override fun onClick(player: Player, slot: Int, type: ClickType)
    {
        TODO("Not yet implemented")
    }

}
package ltd.matrixstudios.alchemist.util.skull

import ltd.matrixstudios.alchemist.util.Chat
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.SkullMeta

object SkullUtil {

    fun generate(owner: String, displayname: String) : ItemStack {
        val itemstack = ItemStack(Material.SKULL_ITEM)

        itemstack.durability = 3

        val itemMeta = itemstack.itemMeta as SkullMeta

        itemMeta.displayName = Chat.format(displayname)
        itemMeta.owner = owner

        itemstack.itemMeta = itemMeta

        return itemstack
    }
}
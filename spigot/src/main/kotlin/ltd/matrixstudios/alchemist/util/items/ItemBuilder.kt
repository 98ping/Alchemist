package ltd.matrixstudios.alchemist.util.items

import org.bukkit.inventory.meta.LeatherArmorMeta
import java.util.stream.Collectors
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Color
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import java.util.*

class ItemBuilder(private val item: ItemStack) {

    constructor(material: Material): this(ItemStack(material))
    constructor(material: Material, amount: Int): this(ItemStack(material, amount))

    fun amount(amount: Int): ItemBuilder {
        item.amount = amount
        return this
    }

    fun data(data: Short): ItemBuilder {
        item.durability = data
        return this
    }

    fun enchant(enchantment: Enchantment, level: Int): ItemBuilder {
        item.addUnsafeEnchantment(enchantment, level)
        return this
    }

    fun unenchant(enchantment: Enchantment): ItemBuilder {
        item.removeEnchantment(enchantment)
        return this
    }

    fun name(displayName: String?): ItemBuilder {
        val meta = item.itemMeta
        meta.displayName = if (displayName == null) null else ChatColor.translateAlternateColorCodes('&', displayName)
        item.itemMeta = meta
        return this
    }

    fun addToLore(vararg parts: String): ItemBuilder {
        var meta = item.itemMeta
        if (meta == null) {
            meta = Bukkit.getItemFactory().getItemMeta(item.type)
        }

        var lore: MutableList<String>? = meta!!.lore
        if (lore == null) {
            lore = arrayListOf()
        }

        lore.addAll(Arrays.stream(parts).map { part -> ChatColor.translateAlternateColorCodes('&', part) }.collect(Collectors.toList()))

        meta.lore = lore
        item.itemMeta = meta
        return this
    }

    fun setLore(l: Collection<String>): ItemBuilder {
        val lore = ArrayList<String>()
        val meta = item.getItemMeta()
        lore.addAll(l.stream().map { part -> ChatColor.translateAlternateColorCodes('&', part) }.collect(Collectors.toList()))
        meta.lore = lore
        item.itemMeta = meta
        return this
    }

    fun color(color: Color): ItemBuilder {
        val meta = item.itemMeta as? LeatherArmorMeta ?: throw UnsupportedOperationException("Cannot set color of a non-leather armor item.")
        meta.color = color
        item.itemMeta = meta
        return this
    }

    fun addFlags(vararg flags: ItemFlag): ItemBuilder {
        val meta = item.itemMeta
        meta.addItemFlags(*flags)
        item.itemMeta = meta
        return this
    }


    fun build(): ItemStack {
        return item.clone()
    }

    companion object {
        @JvmStatic
        fun of (material: Material): ItemBuilder {
            return ItemBuilder(material, 1)
        }

        @JvmStatic
        fun of (material: Material, amount: Int): ItemBuilder {
            return ItemBuilder(material, amount)
        }

        @JvmStatic
        fun copyOf(builder: ItemBuilder): ItemBuilder {
            return ItemBuilder(builder.build())
        }

        @JvmStatic
        fun copyOf(item: ItemStack): ItemBuilder {
            return ItemBuilder(item.clone())
        }
    }

}
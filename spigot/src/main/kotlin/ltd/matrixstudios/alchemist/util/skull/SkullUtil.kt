package ltd.matrixstudios.alchemist.util.skull

import com.mojang.authlib.GameProfile
import com.mojang.authlib.properties.Property
import ltd.matrixstudios.alchemist.util.Chat
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.SkullMeta
import java.lang.reflect.Field
import java.util.*


object SkullUtil {

    fun applyCustomHead(skull: ItemStack, value: String?, tempname: String?, descrption: MutableList<String>, displayname: String): ItemStack? {
        val signature =
            "H116D5fhmj/7BVWqiRQilXmvoJO6wJzXH4Dvou6P2o9YMb+HaJT8s9+zt03GMYTipzK+NsW2D2JfzagnxLUTuiOtrCHm6V2udOM0HG0JeL4zR0Wn5oHmu+S7kUPUbt7HVlKaRXry5bobFQ06nUf7hOV3kPfpUJsfMajfabmoJ9RGMRVot3uQszjKOHQjxyAjfHP2rjeI/SktBrSscx0DzwBW9LCra7g/6Cp7/xPQTIZsqz2Otgp6i2h3YpXJPy02j4pIk0H4biR3CaU7FB0V4/D1Hvjd08giRvUpqF0a1w9rbpIWIH5GTUP8eLFdG/9SnHqMCQrTj4KkQiN0GdBO18JvJS/40LTn3ZLag5LBIa7AyyGus27N3wdIccvToQ6kHHRVpW7cUSXjircg3LOsSQbJmfLoVJ/KAF/m+de4PxIjOJIcbiOkVyQfMQltPg26VzRiu3F0qRvJNAAydH8AHdaqhkpSf6yjHqPU3p3BHFJld5o59WoD4WNkE3wOC//aTpV/f9RJ0JQko08v2mGBVKx7tpN7vHD1qD5ILzV1nDCV1/qbKgiOK9QmdXqZw9J3pM/DHtZ6eiRKni9BuGWlbWFN/qfFO2xY+J7SYFqTxBbffmvwvuF83QP5UdRTNVLYoV5S+yR5ac7fVWUZmLbq7tawyuCu0Dw24M9E1BSnpSc="

        val gameProfile = GameProfile(UUID.randomUUID(), tempname!!)
        gameProfile.properties.put("textures", Property("textures", value, signature))
        val skullMeta = skull.itemMeta as SkullMeta
        skullMeta.owner = tempname
        skullMeta.lore = descrption.map { Chat.format(it) }
        skullMeta.displayName = Chat.format(displayname)
        try {
            val profileField: Field = skullMeta.javaClass.getDeclaredField("profile")
            profileField.setAccessible(true)
            profileField.set(skullMeta, gameProfile)
        } catch (exception: NoSuchFieldException) {
            return null
        } catch (exception: IllegalArgumentException) {
            return null
        } catch (exception: IllegalAccessException) {
            return null
        }
        skull.itemMeta = skullMeta
        return skull
    }

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
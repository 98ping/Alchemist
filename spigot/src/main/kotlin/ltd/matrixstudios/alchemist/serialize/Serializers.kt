package ltd.matrixstudios.alchemist.serialize

import com.google.gson.*
import ltd.matrixstudios.alchemist.serialize.type.ItemStackAdapter
import net.minecraft.server.v1_8_R3.EntityItemFrame
import org.bukkit.inventory.ItemStack

object Serializers {
     val GSON: Gson = GsonBuilder()
         .serializeNulls()
         .setPrettyPrinting()
         .setLongSerializationPolicy(LongSerializationPolicy.STRING)
         .registerTypeAdapter(ItemStack::class.java, ItemStackAdapter())
         .setExclusionStrategies(object : ExclusionStrategy {
             override fun shouldSkipField(p0: FieldAttributes): Boolean {
                 return false
             }

             override fun shouldSkipClass(p0: Class<*>): Boolean {
                 if (p0.simpleName.equals("EntityItemFrame", ignoreCase = true))
                 {
                     return true
                 }

                 return false
             }

         })
        .create()

    fun <T> fromJson(json: String, any: Class<T>) : T
    {
        return GSON.fromJson(json, any)
    }
}
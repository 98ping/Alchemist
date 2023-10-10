package ltd.matrixstudios.alchemist.disguise

import com.google.gson.reflect.TypeToken
import ltd.matrixstudios.alchemist.Alchemist
import ltd.matrixstudios.alchemist.redis.RedisPacketManager
import ltd.matrixstudios.alchemist.util.Chat
import net.pinger.disguise.skin.Skin
import java.lang.reflect.Type
import java.util.concurrent.CompletableFuture

object DisguiseService
{
    var commonSkins = mutableMapOf<String, Skin>()
    val type: Type = object : TypeToken<MutableMap<String, Skin>>() {}.type

    fun loadAllSkins()
    {
        val start = System.currentTimeMillis()
        RedisPacketManager.pool.resource.use {
            val skins = it.get("Alchemist:Disguise:SkinCache")
            val gson = Alchemist.gson.fromJson<MutableMap<String, Skin>>(skins, type) ?: return@use

            commonSkins = gson
        }
        Chat.sendConsoleMessage("&5[Disguise] &b[Cache] &fCached all disguise skins in &5" + System.currentTimeMillis().minus(start) + "ms")
    }

    fun saveAll()
    {
        CompletableFuture.runAsync {
            RedisPacketManager.pool.resource.use {
                it.set(
                    "Alchemist:Disguise:SkinCache",
                    Alchemist.gson.toJson(commonSkins)
                )
            }
        }
    }
}
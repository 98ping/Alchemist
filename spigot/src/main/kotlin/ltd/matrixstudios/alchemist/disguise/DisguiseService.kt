package ltd.matrixstudios.alchemist.disguise

import com.google.gson.reflect.TypeToken
import ltd.matrixstudios.alchemist.Alchemist
import ltd.matrixstudios.alchemist.AlchemistSpigotPlugin
import ltd.matrixstudios.alchemist.models.profile.disguise.SkinDisguiseAttribute
import ltd.matrixstudios.alchemist.profiles.getProfile
import ltd.matrixstudios.alchemist.redis.RedisPacketManager
import ltd.matrixstudios.alchemist.service.profiles.ProfileGameService
import ltd.matrixstudios.alchemist.util.Chat
import net.pinger.disguise.DisguiseAPI
import net.pinger.disguise.skin.Skin
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.WorldCreator
import org.bukkit.entity.Player
import java.lang.reflect.Type
import java.util.concurrent.CompletableFuture

object DisguiseService
{
    var commonSkins = mutableMapOf<String, Skin>()
    var commonNames = AlchemistSpigotPlugin.instance.config.getStringList("disguise.commonNames")
    var popularNames = AlchemistSpigotPlugin.instance.config.getStringList("disguise.popularNames")
    val type: Type = object : TypeToken<MutableMap<String, Skin>>()
    {}.type

    fun loadAllSkins()
    {
        val start = System.currentTimeMillis()
        RedisPacketManager.pool.resource.use {
            val skins = it.get("Alchemist:Disguise:SkinCache")
            val gson = Alchemist.gson.fromJson<MutableMap<String, Skin>>(skins, type) ?: return@use

            commonSkins = gson
        }
        Chat.sendConsoleMessage(
            "&5[Disguise] &b[Cache] &fCached all disguise skins in &5" + System.currentTimeMillis().minus(start) + "ms"
        )

        // skin updates
        Bukkit.createWorld(WorldCreator.name("SkinUpdateWorld"))
    }

    fun setupDisguise(player: Player, name: String, skin: Skin)
    {
        player.getProfile().apply {
            if (this != null)
            {
                this.skinDisguiseAttribute = SkinDisguiseAttribute(name, System.currentTimeMillis(), name)

                player.displayName = this.skinDisguiseAttribute!!.customName
                player.playerListName = player.displayName
                player.customName = player.displayName

                DisguiseAPI.getDefaultProvider().updatePlayer(player, skin, name)

                val location = player.location

                // refresh player skin
                player.teleport(Location(Bukkit.getWorld("SkinUpdateWorld"), 0.0, 100.0, 0.0))
                player.teleport(location)

                ProfileGameService.save(this)
                player.sendMessage(Chat.format("&aSuccess! You now look like &f${name}&a."))
                player.sendMessage(Chat.format("&c${name} is an existing Minecraft player, so if they log in for the first time as you're disguised, you will be kicked!"))
            }
        }
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
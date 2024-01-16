package ltd.matrixstudios.alchemist.broadcasts

import com.google.gson.reflect.TypeToken
import ltd.matrixstudios.alchemist.AlchemistSpigotPlugin
import ltd.matrixstudios.alchemist.broadcasts.model.BroadcastMessage
import ltd.matrixstudios.alchemist.redis.data.RedisDataSync
import ltd.matrixstudios.alchemist.util.Chat
import org.bukkit.Bukkit
import org.bukkit.scheduler.BukkitRunnable

/**
 * Class created on 6/17/2023

 * @author 98ping
 * @project Alchemist
 * @website https://solo.to/redis
 */
object BroadcastService : RedisDataSync<MutableMap<String, BroadcastMessage>>("broadcast-service", object: TypeToken<MutableMap<String, BroadcastMessage>>() {}.type)
{
    override fun destination(): String = "alchemist:broadcast-service:"

    override fun key(): String = "all-broadcasts"

}
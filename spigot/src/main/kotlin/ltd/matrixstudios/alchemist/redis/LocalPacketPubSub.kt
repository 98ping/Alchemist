package ltd.matrixstudios.alchemist.redis

import ltd.matrixstudios.alchemist.AlchemistSpigotPlugin
import org.bukkit.Bukkit
import redis.clients.jedis.JedisPubSub


object LocalPacketPubSub : JedisPubSub()
{

    var received: Int = 0

    override fun onMessage(channel: String?, message: String)
    {
        val packetClass: Class<*>
        val packetMessageSplit = message.indexOf("|")
        val packetClassStr = message.substring(0, packetMessageSplit)
        val messageJson = message.substring(packetMessageSplit + "|".length)
        packetClass = try
        {
            Class.forName(packetClassStr)
        } catch (ignored: ClassNotFoundException)
        {
            return
        }
        received++
        val packet = RedisPacketManager.gson.fromJson(messageJson, packetClass) as RedisPacket
        Bukkit.getScheduler().runTask(AlchemistSpigotPlugin.instance, packet::action)
    }
}
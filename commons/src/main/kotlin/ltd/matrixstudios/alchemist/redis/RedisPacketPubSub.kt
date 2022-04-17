package ltd.matrixstudios.alchemist.redis

import redis.clients.jedis.JedisPubSub
import kotlin.concurrent.thread


class RedisPacketPubSub : JedisPubSub() {

    override fun onMessage(channel: String?, message: String?) {
        val packetClass: Class<*>

        val packetMessageSplit = message!!.indexOf("|")
        val packetClassStr = message.substring(0, packetMessageSplit)
        val messageJson = message.substring(packetMessageSplit + "|".length)

        packetClass = try {
            Class.forName(packetClassStr)
        } catch (ignored: ClassNotFoundException) {
            return
        }

        val packet = RedisPacketManager.redisGson.fromJson(messageJson, packetClass) as RedisPacket

        packet.action()
        println("[Packet] Received packet " + packet.packetId)
    }
}
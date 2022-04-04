package ltd.matrixstudios.alchemist.redis

import ltd.matrixstudios.alchemist.Alchemist
import redis.clients.jedis.JedisPubSub


class RedisPacketPubSub : JedisPubSub() {

    override fun onMessage(channel: String?, message: String?) {
        val packet = Alchemist.dataflow.mainSerializer.serialize(message!!, RedisPacket::class.java)

        packet.action()
        println("[Packet] Received packet " + packet.packetId)
    }
}
package ltd.matrixstudios.alchemist.redis

abstract class RedisPacket(
    val packetId: String
) {

    abstract fun action()
}
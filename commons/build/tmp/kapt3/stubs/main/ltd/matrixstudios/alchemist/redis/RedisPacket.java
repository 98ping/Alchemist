package ltd.matrixstudios.alchemist.redis;

import java.lang.System;

@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\b&\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\b\u0010\u0007\u001a\u00020\bH&R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\t"}, d2 = {"Lltd/matrixstudios/alchemist/redis/RedisPacket;", "", "packetId", "", "(Ljava/lang/String;)V", "getPacketId", "()Ljava/lang/String;", "action", "", "commons"})
public abstract class RedisPacket {
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String packetId = null;
    
    public RedisPacket(@org.jetbrains.annotations.NotNull()
    java.lang.String packetId) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getPacketId() {
        return null;
    }
    
    public abstract void action();
}
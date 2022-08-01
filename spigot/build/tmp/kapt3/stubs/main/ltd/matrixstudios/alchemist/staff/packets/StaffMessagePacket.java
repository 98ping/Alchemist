package ltd.matrixstudios.alchemist.staff.packets;

import java.lang.System;

@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0000\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\u0002\u0010\u0007J\b\u0010\r\u001a\u00020\u000eH\u0016R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\t\u00a8\u0006\u000f"}, d2 = {"Lltd/matrixstudios/alchemist/staff/packets/StaffMessagePacket;", "Lltd/matrixstudios/alchemist/redis/RedisPacket;", "message", "", "server", "sender", "Ljava/util/UUID;", "(Ljava/lang/String;Ljava/lang/String;Ljava/util/UUID;)V", "getMessage", "()Ljava/lang/String;", "getSender", "()Ljava/util/UUID;", "getServer", "action", "", "spigot"})
public final class StaffMessagePacket extends ltd.matrixstudios.alchemist.redis.RedisPacket {
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String message = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String server = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.UUID sender = null;
    
    public StaffMessagePacket(@org.jetbrains.annotations.NotNull()
    java.lang.String message, @org.jetbrains.annotations.NotNull()
    java.lang.String server, @org.jetbrains.annotations.NotNull()
    java.util.UUID sender) {
        super(null);
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getMessage() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getServer() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.UUID getSender() {
        return null;
    }
    
    @java.lang.Override()
    public void action() {
    }
}
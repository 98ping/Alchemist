package ltd.matrixstudios.alchemist.redis;

import java.lang.System;

@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012J\u000e\u0010\u0013\u001a\u00020\u00102\u0006\u0010\u0014\u001a\u00020\u0015R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001a\u0010\t\u001a\u00020\nX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000e\u00a8\u0006\u0016"}, d2 = {"Lltd/matrixstudios/alchemist/redis/RedisPacketManager;", "", "()V", "pool", "Lredis/clients/jedis/JedisPool;", "getPool", "()Lredis/clients/jedis/JedisPool;", "setPool", "(Lredis/clients/jedis/JedisPool;)V", "redisGson", "Lcom/google/gson/Gson;", "getRedisGson", "()Lcom/google/gson/Gson;", "setRedisGson", "(Lcom/google/gson/Gson;)V", "load", "", "host", "", "send", "redisPacket", "Lltd/matrixstudios/alchemist/redis/RedisPacket;", "commons"})
public final class RedisPacketManager {
    @org.jetbrains.annotations.NotNull()
    public static final ltd.matrixstudios.alchemist.redis.RedisPacketManager INSTANCE = null;
    public static redis.clients.jedis.JedisPool pool;
    @org.jetbrains.annotations.NotNull()
    private static com.google.gson.Gson redisGson;
    
    private RedisPacketManager() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final redis.clients.jedis.JedisPool getPool() {
        return null;
    }
    
    public final void setPool(@org.jetbrains.annotations.NotNull()
    redis.clients.jedis.JedisPool p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.google.gson.Gson getRedisGson() {
        return null;
    }
    
    public final void setRedisGson(@org.jetbrains.annotations.NotNull()
    com.google.gson.Gson p0) {
    }
    
    public final void load(@org.jetbrains.annotations.NotNull()
    java.lang.String host) {
    }
    
    public final void send(@org.jetbrains.annotations.NotNull()
    ltd.matrixstudios.alchemist.redis.RedisPacket redisPacket) {
    }
}
package ltd.matrixstudios.alchemist;

import java.lang.System;

@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J2\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00042\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001b2\b\u0010\u001c\u001a\u0004\u0018\u00010\u00192\b\u0010\u001d\u001a\u0004\u0018\u00010\u0019R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001a\u0010\t\u001a\u00020\nX\u0086.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001a\u0010\u000f\u001a\u00020\u0010X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014\u00a8\u0006\u001e"}, d2 = {"Lltd/matrixstudios/alchemist/Alchemist;", "", "()V", "MongoConnectionPool", "Lio/github/nosequel/data/connection/mongo/MongoConnectionPool;", "getMongoConnectionPool", "()Lio/github/nosequel/data/connection/mongo/MongoConnectionPool;", "setMongoConnectionPool", "(Lio/github/nosequel/data/connection/mongo/MongoConnectionPool;)V", "dataHandler", "Lio/github/nosequel/data/DataHandler;", "getDataHandler", "()Lio/github/nosequel/data/DataHandler;", "setDataHandler", "(Lio/github/nosequel/data/DataHandler;)V", "gson", "Lcom/google/gson/Gson;", "getGson", "()Lcom/google/gson/Gson;", "setGson", "(Lcom/google/gson/Gson;)V", "start", "", "mongoConnectionPool", "redisHost", "", "redisPort", "", "redisUsername", "redisPassword", "commons"})
public final class Alchemist {
    @org.jetbrains.annotations.NotNull()
    public static final ltd.matrixstudios.alchemist.Alchemist INSTANCE = null;
    public static io.github.nosequel.data.connection.mongo.MongoConnectionPool MongoConnectionPool;
    public static io.github.nosequel.data.DataHandler dataHandler;
    @org.jetbrains.annotations.NotNull()
    private static com.google.gson.Gson gson;
    
    private Alchemist() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final io.github.nosequel.data.connection.mongo.MongoConnectionPool getMongoConnectionPool() {
        return null;
    }
    
    public final void setMongoConnectionPool(@org.jetbrains.annotations.NotNull()
    io.github.nosequel.data.connection.mongo.MongoConnectionPool p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final io.github.nosequel.data.DataHandler getDataHandler() {
        return null;
    }
    
    public final void setDataHandler(@org.jetbrains.annotations.NotNull()
    io.github.nosequel.data.DataHandler p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.google.gson.Gson getGson() {
        return null;
    }
    
    public final void setGson(@org.jetbrains.annotations.NotNull()
    com.google.gson.Gson p0) {
    }
    
    public final void start(@org.jetbrains.annotations.NotNull()
    io.github.nosequel.data.connection.mongo.MongoConnectionPool mongoConnectionPool, @org.jetbrains.annotations.NotNull()
    java.lang.String redisHost, int redisPort, @org.jetbrains.annotations.Nullable()
    java.lang.String redisUsername, @org.jetbrains.annotations.Nullable()
    java.lang.String redisPassword) {
    }
}
package ltd.matrixstudios.alchemist.service.profiles;

import java.lang.System;

@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0016\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0017\u001a\u00020\u0005J\u0010\u0010\u0018\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0019\u001a\u00020\u001aJ\u0016\u0010\u001b\u001a\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u00052\u0006\u0010\u001c\u001a\u00020\u001aJ\u000e\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020\u0006R(\u0010\u0003\u001a\u0010\u0012\u0004\u0012\u00020\u0005\u0012\u0006\u0012\u0004\u0018\u00010\u00060\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\u0017\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR&\u0010\u0010\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0011X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015\u00a8\u0006 "}, d2 = {"Lltd/matrixstudios/alchemist/service/profiles/ProfileGameService;", "", "()V", "cache", "Ljava/util/HashMap;", "Ljava/util/UUID;", "Lltd/matrixstudios/alchemist/models/profile/GameProfile;", "getCache", "()Ljava/util/HashMap;", "setCache", "(Ljava/util/HashMap;)V", "collection", "Lcom/mongodb/client/MongoCollection;", "Lorg/bson/Document;", "getCollection", "()Lcom/mongodb/client/MongoCollection;", "handler", "Lio/github/nosequel/data/store/StoreType;", "getHandler", "()Lio/github/nosequel/data/store/StoreType;", "setHandler", "(Lio/github/nosequel/data/store/StoreType;)V", "byId", "uuid", "byUsername", "name", "", "loadProfile", "username", "save", "", "gameProfile", "commons"})
public final class ProfileGameService {
    @org.jetbrains.annotations.NotNull()
    public static final ltd.matrixstudios.alchemist.service.profiles.ProfileGameService INSTANCE = null;
    @org.jetbrains.annotations.NotNull()
    private static io.github.nosequel.data.store.StoreType<java.util.UUID, ltd.matrixstudios.alchemist.models.profile.GameProfile> handler;
    @org.jetbrains.annotations.NotNull()
    private static final com.mongodb.client.MongoCollection<org.bson.Document> collection = null;
    @org.jetbrains.annotations.NotNull()
    private static java.util.HashMap<java.util.UUID, ltd.matrixstudios.alchemist.models.profile.GameProfile> cache;
    
    private ProfileGameService() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final io.github.nosequel.data.store.StoreType<java.util.UUID, ltd.matrixstudios.alchemist.models.profile.GameProfile> getHandler() {
        return null;
    }
    
    public final void setHandler(@org.jetbrains.annotations.NotNull()
    io.github.nosequel.data.store.StoreType<java.util.UUID, ltd.matrixstudios.alchemist.models.profile.GameProfile> p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.mongodb.client.MongoCollection<org.bson.Document> getCollection() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.HashMap<java.util.UUID, ltd.matrixstudios.alchemist.models.profile.GameProfile> getCache() {
        return null;
    }
    
    public final void setCache(@org.jetbrains.annotations.NotNull()
    java.util.HashMap<java.util.UUID, ltd.matrixstudios.alchemist.models.profile.GameProfile> p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final ltd.matrixstudios.alchemist.models.profile.GameProfile byId(@org.jetbrains.annotations.NotNull()
    java.util.UUID uuid) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final ltd.matrixstudios.alchemist.models.profile.GameProfile byUsername(@org.jetbrains.annotations.NotNull()
    java.lang.String name) {
        return null;
    }
    
    public final void save(@org.jetbrains.annotations.NotNull()
    ltd.matrixstudios.alchemist.models.profile.GameProfile gameProfile) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final ltd.matrixstudios.alchemist.models.profile.GameProfile loadProfile(@org.jetbrains.annotations.NotNull()
    java.util.UUID uuid, @org.jetbrains.annotations.NotNull()
    java.lang.String username) {
        return null;
    }
}
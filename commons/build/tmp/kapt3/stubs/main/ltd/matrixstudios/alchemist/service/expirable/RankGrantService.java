package ltd.matrixstudios.alchemist.service.expirable;

import java.lang.System;

@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0010!\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u001e\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u00c6\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0003J\b\u0010\u0017\u001a\u00020\u0018H\u0016J\u001a\u0010\u0019\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00020\u001b0\u001a2\u0006\u0010\u001c\u001a\u00020\u000bJ\u0014\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00020\u001b2\u0006\u0010\u001e\u001a\u00020\u000bJ\u0012\u0010\u001f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00020\u001b0\u001aJ\u000e\u0010 \u001a\u00020\u00182\u0006\u0010!\u001a\u00020\"J\u000e\u0010#\u001a\u00020\u00182\u0006\u0010$\u001a\u00020\u0002R\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR&\u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u00020\nX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fR,\u0010\u0010\u001a\u0014\u0012\u0004\u0012\u00020\u000b\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00020\u00120\u0011X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016\u00a8\u0006%"}, d2 = {"Lltd/matrixstudios/alchemist/service/expirable/RankGrantService;", "Lltd/matrixstudios/alchemist/service/expirable/ExpiringService;", "Lltd/matrixstudios/alchemist/models/grant/types/RankGrant;", "()V", "collection", "Lcom/mongodb/client/MongoCollection;", "Lorg/bson/Document;", "getCollection", "()Lcom/mongodb/client/MongoCollection;", "handler", "Lio/github/nosequel/data/store/StoreType;", "Ljava/util/UUID;", "getHandler", "()Lio/github/nosequel/data/store/StoreType;", "setHandler", "(Lio/github/nosequel/data/store/StoreType;)V", "playerGrants", "Ljava/util/HashMap;", "", "getPlayerGrants", "()Ljava/util/HashMap;", "setPlayerGrants", "(Ljava/util/HashMap;)V", "clearOutModels", "", "findByTarget", "Ljava/util/concurrent/CompletableFuture;", "", "target", "getFromCache", "uuid", "getValues", "recalculatePlayer", "gameProfile", "Lltd/matrixstudios/alchemist/models/profile/GameProfile;", "save", "rankGrant", "commons"})
public final class RankGrantService extends ltd.matrixstudios.alchemist.service.expirable.ExpiringService<ltd.matrixstudios.alchemist.models.grant.types.RankGrant> {
    @org.jetbrains.annotations.NotNull()
    public static final ltd.matrixstudios.alchemist.service.expirable.RankGrantService INSTANCE = null;
    @org.jetbrains.annotations.NotNull()
    private static io.github.nosequel.data.store.StoreType<java.util.UUID, ltd.matrixstudios.alchemist.models.grant.types.RankGrant> handler;
    @org.jetbrains.annotations.NotNull()
    private static final com.mongodb.client.MongoCollection<org.bson.Document> collection = null;
    @org.jetbrains.annotations.NotNull()
    private static java.util.HashMap<java.util.UUID, java.util.List<ltd.matrixstudios.alchemist.models.grant.types.RankGrant>> playerGrants;
    
    private RankGrantService() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final io.github.nosequel.data.store.StoreType<java.util.UUID, ltd.matrixstudios.alchemist.models.grant.types.RankGrant> getHandler() {
        return null;
    }
    
    public final void setHandler(@org.jetbrains.annotations.NotNull()
    io.github.nosequel.data.store.StoreType<java.util.UUID, ltd.matrixstudios.alchemist.models.grant.types.RankGrant> p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.mongodb.client.MongoCollection<org.bson.Document> getCollection() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.HashMap<java.util.UUID, java.util.List<ltd.matrixstudios.alchemist.models.grant.types.RankGrant>> getPlayerGrants() {
        return null;
    }
    
    public final void setPlayerGrants(@org.jetbrains.annotations.NotNull()
    java.util.HashMap<java.util.UUID, java.util.List<ltd.matrixstudios.alchemist.models.grant.types.RankGrant>> p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.concurrent.CompletableFuture<java.util.Collection<ltd.matrixstudios.alchemist.models.grant.types.RankGrant>> getValues() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.Collection<ltd.matrixstudios.alchemist.models.grant.types.RankGrant> getFromCache(@org.jetbrains.annotations.NotNull()
    java.util.UUID uuid) {
        return null;
    }
    
    public final void recalculatePlayer(@org.jetbrains.annotations.NotNull()
    ltd.matrixstudios.alchemist.models.profile.GameProfile gameProfile) {
    }
    
    public final void save(@org.jetbrains.annotations.NotNull()
    ltd.matrixstudios.alchemist.models.grant.types.RankGrant rankGrant) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.concurrent.CompletableFuture<java.util.Collection<ltd.matrixstudios.alchemist.models.grant.types.RankGrant>> findByTarget(@org.jetbrains.annotations.NotNull()
    java.util.UUID target) {
        return null;
    }
    
    @java.lang.Override()
    public void clearOutModels() {
    }
}
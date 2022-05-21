package ltd.matrixstudios.alchemist.service.expirable;

import java.lang.System;

@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u001e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0003J\b\u0010\u0019\u001a\u00020\u001aH\u0016J\u001a\u0010\u001b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u00070\u001c2\u0006\u0010\u001d\u001a\u00020\u001eJ\u0014\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u00020\u00072\u0006\u0010 \u001a\u00020\u0006J\u001a\u0010!\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00020\u00070\u001c2\u0006\u0010\"\u001a\u00020\u0006J\u000e\u0010#\u001a\u00020\u001a2\u0006\u0010$\u001a\u00020\u0002J\u000e\u0010%\u001a\u00020\u001a2\u0006\u0010&\u001a\u00020\'J\u000e\u0010(\u001a\u00020\u001a2\u0006\u0010\u001d\u001a\u00020\u001eR,\u0010\u0004\u001a\u0014\u0012\u0004\u0012\u00020\u0006\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00020\u00070\u0005X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000bR&\u0010\f\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00020\rX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R \u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00140\u0013X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\u0016\"\u0004\b\u0017\u0010\u0018\u00a8\u0006)"}, d2 = {"Lltd/matrixstudios/alchemist/service/expirable/RankGrantService;", "Lltd/matrixstudios/alchemist/service/expirable/ExpiringService;", "Lltd/matrixstudios/alchemist/models/grant/types/RankGrant;", "()V", "cache", "Ljava/util/HashMap;", "Ljava/util/UUID;", "", "getCache", "()Ljava/util/HashMap;", "setCache", "(Ljava/util/HashMap;)V", "handler", "Lio/github/nosequel/data/store/StoreType;", "getHandler", "()Lio/github/nosequel/data/store/StoreType;", "setHandler", "(Lio/github/nosequel/data/store/StoreType;)V", "internalCollection", "Lcom/mongodb/client/MongoCollection;", "Lorg/bson/Document;", "getInternalCollection", "()Lcom/mongodb/client/MongoCollection;", "setInternalCollection", "(Lcom/mongodb/client/MongoCollection;)V", "clearOutModels", "", "collectUsersWithRank", "Ljava/util/concurrent/CompletableFuture;", "rank", "Lltd/matrixstudios/alchemist/models/ranks/Rank;", "findByTarget", "target", "findGrantsFromRawDatabase", "uuid", "save", "rankGrant", "setupGrants", "gameProfile", "Lltd/matrixstudios/alchemist/models/profile/GameProfile;", "smashThenReplaceGrants", "commons"})
public final class RankGrantService extends ltd.matrixstudios.alchemist.service.expirable.ExpiringService<ltd.matrixstudios.alchemist.models.grant.types.RankGrant> {
    @org.jetbrains.annotations.NotNull()
    public static final ltd.matrixstudios.alchemist.service.expirable.RankGrantService INSTANCE = null;
    @org.jetbrains.annotations.NotNull()
    private static io.github.nosequel.data.store.StoreType<java.util.UUID, ltd.matrixstudios.alchemist.models.grant.types.RankGrant> handler;
    @org.jetbrains.annotations.NotNull()
    private static java.util.HashMap<java.util.UUID, java.util.Collection<ltd.matrixstudios.alchemist.models.grant.types.RankGrant>> cache;
    @org.jetbrains.annotations.NotNull()
    private static com.mongodb.client.MongoCollection<org.bson.Document> internalCollection;
    
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
    public final java.util.HashMap<java.util.UUID, java.util.Collection<ltd.matrixstudios.alchemist.models.grant.types.RankGrant>> getCache() {
        return null;
    }
    
    public final void setCache(@org.jetbrains.annotations.NotNull()
    java.util.HashMap<java.util.UUID, java.util.Collection<ltd.matrixstudios.alchemist.models.grant.types.RankGrant>> p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.mongodb.client.MongoCollection<org.bson.Document> getInternalCollection() {
        return null;
    }
    
    public final void setInternalCollection(@org.jetbrains.annotations.NotNull()
    com.mongodb.client.MongoCollection<org.bson.Document> p0) {
    }
    
    public final void save(@org.jetbrains.annotations.NotNull()
    ltd.matrixstudios.alchemist.models.grant.types.RankGrant rankGrant) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.Collection<ltd.matrixstudios.alchemist.models.grant.types.RankGrant> findByTarget(@org.jetbrains.annotations.NotNull()
    java.util.UUID target) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.concurrent.CompletableFuture<java.util.Collection<java.util.UUID>> collectUsersWithRank(@org.jetbrains.annotations.NotNull()
    ltd.matrixstudios.alchemist.models.ranks.Rank rank) {
        return null;
    }
    
    public final void setupGrants(@org.jetbrains.annotations.NotNull()
    ltd.matrixstudios.alchemist.models.profile.GameProfile gameProfile) {
    }
    
    public final void smashThenReplaceGrants(@org.jetbrains.annotations.NotNull()
    ltd.matrixstudios.alchemist.models.ranks.Rank rank) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.concurrent.CompletableFuture<java.util.Collection<ltd.matrixstudios.alchemist.models.grant.types.RankGrant>> findGrantsFromRawDatabase(@org.jetbrains.annotations.NotNull()
    java.util.UUID uuid) {
        return null;
    }
    
    @java.lang.Override()
    public void clearOutModels() {
    }
}
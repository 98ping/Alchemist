package ltd.matrixstudios.alchemist.service.expirable;

import java.lang.System;

@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000h\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0010\u001e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u00c6\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0003J\b\u0010\u0017\u001a\u00020\u0018H\u0016J\u001a\u0010\u0019\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00020\f0\u001a2\u0006\u0010\u001b\u001a\u00020\u000bJ\u0014\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u000bJ\u0014\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u00020\f2\u0006\u0010 \u001a\u00020\u000bJ\u0012\u0010!\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00020\f0\u001aJ\u000e\u0010\"\u001a\u00020\u00182\u0006\u0010#\u001a\u00020$J\u000e\u0010%\u001a\u00020\u00182\u0006\u0010&\u001a\u00020\u0002J\u0010\u0010\'\u001a\u0004\u0018\u00010\u00022\u0006\u0010(\u001a\u00020)J\u0014\u0010*\u001a\b\u0012\u0004\u0012\u00020\u00020\u001d2\u0006\u0010+\u001a\u00020,J\u0014\u0010-\u001a\b\u0012\u0004\u0012\u00020\u00020\u001d2\u0006\u0010.\u001a\u00020/R\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR,\u0010\t\u001a\u0014\u0012\u0004\u0012\u00020\u000b\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00020\f0\nX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R&\u0010\u0011\u001a\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u00020\u0012X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016\u00a8\u00060"}, d2 = {"Lltd/matrixstudios/alchemist/service/expirable/PunishmentService;", "Lltd/matrixstudios/alchemist/service/expirable/ExpiringService;", "Lltd/matrixstudios/alchemist/models/grant/types/Punishment;", "()V", "collection", "Lcom/mongodb/client/MongoCollection;", "Lorg/bson/Document;", "getCollection", "()Lcom/mongodb/client/MongoCollection;", "grants", "", "Ljava/util/UUID;", "", "getGrants", "()Ljava/util/Map;", "setGrants", "(Ljava/util/Map;)V", "handler", "Lio/github/nosequel/data/store/StoreType;", "getHandler", "()Lio/github/nosequel/data/store/StoreType;", "setHandler", "(Lio/github/nosequel/data/store/StoreType;)V", "clearOutModels", "", "findByTarget", "Ljava/util/concurrent/CompletableFuture;", "target", "findExecutorPunishments", "", "executor", "getFromCache", "uuid", "getValues", "recalculatePlayer", "gameProfile", "Lltd/matrixstudios/alchemist/models/profile/GameProfile;", "save", "punishment", "searchFromId", "punishmentId", "", "sortByActorType", "actorType", "Lltd/matrixstudios/alchemist/punishments/actor/ActorType;", "sortByPunishmentType", "punishmentType", "Lltd/matrixstudios/alchemist/punishments/PunishmentType;", "commons"})
public final class PunishmentService extends ltd.matrixstudios.alchemist.service.expirable.ExpiringService<ltd.matrixstudios.alchemist.models.grant.types.Punishment> {
    @org.jetbrains.annotations.NotNull()
    public static final ltd.matrixstudios.alchemist.service.expirable.PunishmentService INSTANCE = null;
    @org.jetbrains.annotations.NotNull()
    private static io.github.nosequel.data.store.StoreType<java.util.UUID, ltd.matrixstudios.alchemist.models.grant.types.Punishment> handler;
    @org.jetbrains.annotations.NotNull()
    private static final com.mongodb.client.MongoCollection<org.bson.Document> collection = null;
    @org.jetbrains.annotations.NotNull()
    private static java.util.Map<java.util.UUID, java.util.Collection<ltd.matrixstudios.alchemist.models.grant.types.Punishment>> grants;
    
    private PunishmentService() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final io.github.nosequel.data.store.StoreType<java.util.UUID, ltd.matrixstudios.alchemist.models.grant.types.Punishment> getHandler() {
        return null;
    }
    
    public final void setHandler(@org.jetbrains.annotations.NotNull()
    io.github.nosequel.data.store.StoreType<java.util.UUID, ltd.matrixstudios.alchemist.models.grant.types.Punishment> p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.mongodb.client.MongoCollection<org.bson.Document> getCollection() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.Map<java.util.UUID, java.util.Collection<ltd.matrixstudios.alchemist.models.grant.types.Punishment>> getGrants() {
        return null;
    }
    
    public final void setGrants(@org.jetbrains.annotations.NotNull()
    java.util.Map<java.util.UUID, java.util.Collection<ltd.matrixstudios.alchemist.models.grant.types.Punishment>> p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.concurrent.CompletableFuture<java.util.Collection<ltd.matrixstudios.alchemist.models.grant.types.Punishment>> getValues() {
        return null;
    }
    
    public final void save(@org.jetbrains.annotations.NotNull()
    ltd.matrixstudios.alchemist.models.grant.types.Punishment punishment) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.Collection<ltd.matrixstudios.alchemist.models.grant.types.Punishment> getFromCache(@org.jetbrains.annotations.NotNull()
    java.util.UUID uuid) {
        return null;
    }
    
    public final void recalculatePlayer(@org.jetbrains.annotations.NotNull()
    ltd.matrixstudios.alchemist.models.profile.GameProfile gameProfile) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<ltd.matrixstudios.alchemist.models.grant.types.Punishment> findExecutorPunishments(@org.jetbrains.annotations.NotNull()
    java.util.UUID executor) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final ltd.matrixstudios.alchemist.models.grant.types.Punishment searchFromId(@org.jetbrains.annotations.NotNull()
    java.lang.String punishmentId) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.concurrent.CompletableFuture<java.util.Collection<ltd.matrixstudios.alchemist.models.grant.types.Punishment>> findByTarget(@org.jetbrains.annotations.NotNull()
    java.util.UUID target) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<ltd.matrixstudios.alchemist.models.grant.types.Punishment> sortByActorType(@org.jetbrains.annotations.NotNull()
    ltd.matrixstudios.alchemist.punishments.actor.ActorType actorType) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<ltd.matrixstudios.alchemist.models.grant.types.Punishment> sortByPunishmentType(@org.jetbrains.annotations.NotNull()
    ltd.matrixstudios.alchemist.punishments.PunishmentType punishmentType) {
        return null;
    }
    
    @java.lang.Override()
    public void clearOutModels() {
    }
}
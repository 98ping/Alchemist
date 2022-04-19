package ltd.matrixstudios.alchemist.service.ranks;

import java.lang.System;

@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0002\b\u0004\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u000b\u001a\u0004\u0018\u00010\u00062\u0006\u0010\f\u001a\u00020\u0005J\u0006\u0010\r\u001a\u00020\u000eJ\b\u0010\u000f\u001a\u0004\u0018\u00010\u0006J\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00060\u0011J\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00060\u0011J\u000e\u0010\u0013\u001a\u00020\u000e2\u0006\u0010\u0014\u001a\u00020\u0006R&\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\n\u00a8\u0006\u0015"}, d2 = {"Lltd/matrixstudios/alchemist/service/ranks/RankService;", "", "()V", "handler", "Lio/github/nosequel/data/store/StoreType;", "", "Lltd/matrixstudios/alchemist/models/ranks/Rank;", "getHandler", "()Lio/github/nosequel/data/store/StoreType;", "setHandler", "(Lio/github/nosequel/data/store/StoreType;)V", "byId", "id", "createDefaultRankIfDoesntExist", "", "findFirstAvailableDefaultRank", "getRanksInOrder", "", "getValues", "save", "rank", "commons"})
public final class RankService {
    @org.jetbrains.annotations.NotNull()
    public static final ltd.matrixstudios.alchemist.service.ranks.RankService INSTANCE = null;
    @org.jetbrains.annotations.NotNull()
    private static io.github.nosequel.data.store.StoreType<java.lang.String, ltd.matrixstudios.alchemist.models.ranks.Rank> handler;
    
    private RankService() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final io.github.nosequel.data.store.StoreType<java.lang.String, ltd.matrixstudios.alchemist.models.ranks.Rank> getHandler() {
        return null;
    }
    
    public final void setHandler(@org.jetbrains.annotations.NotNull()
    io.github.nosequel.data.store.StoreType<java.lang.String, ltd.matrixstudios.alchemist.models.ranks.Rank> p0) {
    }
    
    public final void createDefaultRankIfDoesntExist() {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.Collection<ltd.matrixstudios.alchemist.models.ranks.Rank> getValues() {
        return null;
    }
    
    public final void save(@org.jetbrains.annotations.NotNull()
    ltd.matrixstudios.alchemist.models.ranks.Rank rank) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.Collection<ltd.matrixstudios.alchemist.models.ranks.Rank> getRanksInOrder() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final ltd.matrixstudios.alchemist.models.ranks.Rank findFirstAvailableDefaultRank() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final ltd.matrixstudios.alchemist.models.ranks.Rank byId(@org.jetbrains.annotations.NotNull()
    java.lang.String id) {
        return null;
    }
}
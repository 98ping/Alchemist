package ltd.matrixstudios.alchemist.models.grant.types;

import java.lang.System;

@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\b\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B-\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\u0006\u0012\u0006\u0010\b\u001a\u00020\u0004\u0012\u0006\u0010\t\u001a\u00020\n\u00a2\u0006\u0002\u0010\u000bJ\n\u0010\u0011\u001a\u0004\u0018\u00010\u0002H\u0016R\u001a\u0010\f\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010\u00a8\u0006\u0012"}, d2 = {"Lltd/matrixstudios/alchemist/models/grant/types/RankGrant;", "Lltd/matrixstudios/alchemist/models/grant/Grantable;", "Lltd/matrixstudios/alchemist/models/ranks/Rank;", "rankId", "", "addedTo", "Ljava/util/UUID;", "addedBy", "addedReason", "duration", "", "(Ljava/lang/String;Ljava/util/UUID;Ljava/util/UUID;Ljava/lang/String;J)V", "rank", "getRank", "()Ljava/lang/String;", "setRank", "(Ljava/lang/String;)V", "getGrantable", "commons"})
public final class RankGrant extends ltd.matrixstudios.alchemist.models.grant.Grantable<ltd.matrixstudios.alchemist.models.ranks.Rank> {
    @org.jetbrains.annotations.NotNull()
    private java.lang.String rank;
    
    public RankGrant(@org.jetbrains.annotations.NotNull()
    java.lang.String rankId, @org.jetbrains.annotations.NotNull()
    java.util.UUID addedTo, @org.jetbrains.annotations.NotNull()
    java.util.UUID addedBy, @org.jetbrains.annotations.NotNull()
    java.lang.String addedReason, long duration) {
        super(null, null, null, null, null, null, null);
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getRank() {
        return null;
    }
    
    public final void setRank(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    @java.lang.Override()
    public ltd.matrixstudios.alchemist.models.ranks.Rank getGrantable() {
        return null;
    }
}
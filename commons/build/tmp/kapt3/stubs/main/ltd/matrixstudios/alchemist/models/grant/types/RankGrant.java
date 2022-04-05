package ltd.matrixstudios.alchemist.models.grant.types;

import java.lang.System;

@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\r\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B5\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\u0006\u0012\u0006\u0010\b\u001a\u00020\u0004\u0012\u0006\u0010\t\u001a\u00020\n\u0012\u0006\u0010\u000b\u001a\u00020\f\u00a2\u0006\u0002\u0010\rJ\n\u0010\u0018\u001a\u0004\u0018\u00010\u0002H\u0016R\u001a\u0010\u000e\u001a\u00020\fX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0012R\u001a\u0010\u0013\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\u0017\u00a8\u0006\u0019"}, d2 = {"Lltd/matrixstudios/alchemist/models/grant/types/RankGrant;", "Lltd/matrixstudios/alchemist/models/grant/Grantable;", "Lltd/matrixstudios/alchemist/models/ranks/Rank;", "rankId", "", "addedTo", "Ljava/util/UUID;", "addedBy", "addedReason", "duration", "", "actor", "Lltd/matrixstudios/alchemist/punishments/actor/DefaultActor;", "(Ljava/lang/String;Ljava/util/UUID;Ljava/util/UUID;Ljava/lang/String;JLltd/matrixstudios/alchemist/punishments/actor/DefaultActor;)V", "internalActor", "getInternalActor", "()Lltd/matrixstudios/alchemist/punishments/actor/DefaultActor;", "setInternalActor", "(Lltd/matrixstudios/alchemist/punishments/actor/DefaultActor;)V", "rank", "getRank", "()Ljava/lang/String;", "setRank", "(Ljava/lang/String;)V", "getGrantable", "commons"})
public final class RankGrant extends ltd.matrixstudios.alchemist.models.grant.Grantable<ltd.matrixstudios.alchemist.models.ranks.Rank> {
    @org.jetbrains.annotations.NotNull()
    private ltd.matrixstudios.alchemist.punishments.actor.DefaultActor internalActor;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String rank;
    
    public RankGrant(@org.jetbrains.annotations.NotNull()
    java.lang.String rankId, @org.jetbrains.annotations.NotNull()
    java.util.UUID addedTo, @org.jetbrains.annotations.NotNull()
    java.util.UUID addedBy, @org.jetbrains.annotations.NotNull()
    java.lang.String addedReason, long duration, @org.jetbrains.annotations.NotNull()
    ltd.matrixstudios.alchemist.punishments.actor.DefaultActor actor) {
        super(null, null, null, null, null, null, null);
    }
    
    @org.jetbrains.annotations.NotNull()
    public final ltd.matrixstudios.alchemist.punishments.actor.DefaultActor getInternalActor() {
        return null;
    }
    
    public final void setInternalActor(@org.jetbrains.annotations.NotNull()
    ltd.matrixstudios.alchemist.punishments.actor.DefaultActor p0) {
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
package ltd.matrixstudios.alchemist.models.grant;

import java.lang.System;

@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u001a\b&\u0018\u0000*\u0004\b\u0000\u0010\u00012\u00020\u0002BA\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0004\u0012\u0006\u0010\u0006\u001a\u00020\u0004\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\n\u0012\b\u0010\u000b\u001a\u0004\u0018\u00010\u0004\u0012\b\u0010\f\u001a\u0004\u0018\u00010\b\u00a2\u0006\u0002\u0010\rJ\u000f\u0010\"\u001a\u0004\u0018\u00018\u0000H&\u00a2\u0006\u0002\u0010#R\u001a\u0010\u0006\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R\u001a\u0010\t\u001a\u00020\nX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015R\u001a\u0010\u0007\u001a\u00020\bX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u0017\"\u0004\b\u0018\u0010\u0019R\u001c\u0010\u000b\u001a\u0004\u0018\u00010\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001a\u0010\u000f\"\u0004\b\u001b\u0010\u0011R\u001c\u0010\f\u001a\u0004\u0018\u00010\bX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001c\u0010\u0017\"\u0004\b\u001d\u0010\u0019R\u001a\u0010\u0005\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001e\u0010\u000f\"\u0004\b\u001f\u0010\u0011R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b \u0010\u000f\"\u0004\b!\u0010\u0011\u00a8\u0006$"}, d2 = {"Lltd/matrixstudios/alchemist/models/grant/Grantable;", "T", "", "uuid", "Ljava/util/UUID;", "target", "executor", "reason", "", "expirable", "Lltd/matrixstudios/alchemist/models/expirables/Expirable;", "removedBy", "removedReason", "(Ljava/util/UUID;Ljava/util/UUID;Ljava/util/UUID;Ljava/lang/String;Lltd/matrixstudios/alchemist/models/expirables/Expirable;Ljava/util/UUID;Ljava/lang/String;)V", "getExecutor", "()Ljava/util/UUID;", "setExecutor", "(Ljava/util/UUID;)V", "getExpirable", "()Lltd/matrixstudios/alchemist/models/expirables/Expirable;", "setExpirable", "(Lltd/matrixstudios/alchemist/models/expirables/Expirable;)V", "getReason", "()Ljava/lang/String;", "setReason", "(Ljava/lang/String;)V", "getRemovedBy", "setRemovedBy", "getRemovedReason", "setRemovedReason", "getTarget", "setTarget", "getUuid", "setUuid", "getGrantable", "()Ljava/lang/Object;", "commons"})
public abstract class Grantable<T extends java.lang.Object> {
    @org.jetbrains.annotations.NotNull()
    private java.util.UUID uuid;
    @org.jetbrains.annotations.NotNull()
    private java.util.UUID target;
    @org.jetbrains.annotations.NotNull()
    private java.util.UUID executor;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String reason;
    @org.jetbrains.annotations.NotNull()
    private ltd.matrixstudios.alchemist.models.expirables.Expirable expirable;
    @org.jetbrains.annotations.Nullable()
    private java.util.UUID removedBy;
    @org.jetbrains.annotations.Nullable()
    private java.lang.String removedReason;
    
    public Grantable(@org.jetbrains.annotations.NotNull()
    java.util.UUID uuid, @org.jetbrains.annotations.NotNull()
    java.util.UUID target, @org.jetbrains.annotations.NotNull()
    java.util.UUID executor, @org.jetbrains.annotations.NotNull()
    java.lang.String reason, @org.jetbrains.annotations.NotNull()
    ltd.matrixstudios.alchemist.models.expirables.Expirable expirable, @org.jetbrains.annotations.Nullable()
    java.util.UUID removedBy, @org.jetbrains.annotations.Nullable()
    java.lang.String removedReason) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.UUID getUuid() {
        return null;
    }
    
    public final void setUuid(@org.jetbrains.annotations.NotNull()
    java.util.UUID p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.UUID getTarget() {
        return null;
    }
    
    public final void setTarget(@org.jetbrains.annotations.NotNull()
    java.util.UUID p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.UUID getExecutor() {
        return null;
    }
    
    public final void setExecutor(@org.jetbrains.annotations.NotNull()
    java.util.UUID p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getReason() {
        return null;
    }
    
    public final void setReason(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final ltd.matrixstudios.alchemist.models.expirables.Expirable getExpirable() {
        return null;
    }
    
    public final void setExpirable(@org.jetbrains.annotations.NotNull()
    ltd.matrixstudios.alchemist.models.expirables.Expirable p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.util.UUID getRemovedBy() {
        return null;
    }
    
    public final void setRemovedBy(@org.jetbrains.annotations.Nullable()
    java.util.UUID p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getRemovedReason() {
        return null;
    }
    
    public final void setRemovedReason(@org.jetbrains.annotations.Nullable()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public abstract T getGrantable();
}
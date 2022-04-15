package ltd.matrixstudios.alchemist.punishment.packets;

import java.lang.System;

@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0010\n\u0002\u0010\u0002\n\u0000\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\u0006\u0010\u0007\u001a\u00020\b\u00a2\u0006\u0002\u0010\tJ\b\u0010\u0018\u001a\u00020\u0019H\u0016R\u001a\u0010\u0006\u001a\u00020\u0005X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\rR\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R\u001a\u0010\u0007\u001a\u00020\bX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015R\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u000b\"\u0004\b\u0017\u0010\r\u00a8\u0006\u001a"}, d2 = {"Lltd/matrixstudios/alchemist/punishment/packets/PunishmentRemovePacket;", "Lltd/matrixstudios/alchemist/redis/RedisPacket;", "punishmentType", "Lltd/matrixstudios/alchemist/punishments/PunishmentType;", "target", "Ljava/util/UUID;", "executor", "silent", "", "(Lltd/matrixstudios/alchemist/punishments/PunishmentType;Ljava/util/UUID;Ljava/util/UUID;Z)V", "getExecutor", "()Ljava/util/UUID;", "setExecutor", "(Ljava/util/UUID;)V", "getPunishmentType", "()Lltd/matrixstudios/alchemist/punishments/PunishmentType;", "setPunishmentType", "(Lltd/matrixstudios/alchemist/punishments/PunishmentType;)V", "getSilent", "()Z", "setSilent", "(Z)V", "getTarget", "setTarget", "action", "", "spigot"})
public final class PunishmentRemovePacket extends ltd.matrixstudios.alchemist.redis.RedisPacket {
    @org.jetbrains.annotations.NotNull()
    private ltd.matrixstudios.alchemist.punishments.PunishmentType punishmentType;
    @org.jetbrains.annotations.NotNull()
    private java.util.UUID target;
    @org.jetbrains.annotations.NotNull()
    private java.util.UUID executor;
    private boolean silent;
    
    public PunishmentRemovePacket(@org.jetbrains.annotations.NotNull()
    ltd.matrixstudios.alchemist.punishments.PunishmentType punishmentType, @org.jetbrains.annotations.NotNull()
    java.util.UUID target, @org.jetbrains.annotations.NotNull()
    java.util.UUID executor, boolean silent) {
        super(null);
    }
    
    @org.jetbrains.annotations.NotNull()
    public final ltd.matrixstudios.alchemist.punishments.PunishmentType getPunishmentType() {
        return null;
    }
    
    public final void setPunishmentType(@org.jetbrains.annotations.NotNull()
    ltd.matrixstudios.alchemist.punishments.PunishmentType p0) {
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
    
    public final boolean getSilent() {
        return false;
    }
    
    public final void setSilent(boolean p0) {
    }
    
    @java.lang.Override()
    public void action() {
    }
}
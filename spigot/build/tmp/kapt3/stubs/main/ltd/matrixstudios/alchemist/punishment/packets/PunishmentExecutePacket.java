package ltd.matrixstudios.alchemist.punishment.packets;

import java.lang.System;

@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u000e\n\u0002\u0010\u0002\n\u0000\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\b\u0010\u0015\u001a\u00020\u0016H\u0016R\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR\u001a\u0010\u0006\u001a\u00020\u0007X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014\u00a8\u0006\u0017"}, d2 = {"Lltd/matrixstudios/alchemist/punishment/packets/PunishmentExecutePacket;", "Lltd/matrixstudios/alchemist/redis/RedisPacket;", "punishmentType", "Lltd/matrixstudios/alchemist/punishments/PunishmentType;", "target", "Ljava/util/UUID;", "reason", "", "(Lltd/matrixstudios/alchemist/punishments/PunishmentType;Ljava/util/UUID;Ljava/lang/String;)V", "getPunishmentType", "()Lltd/matrixstudios/alchemist/punishments/PunishmentType;", "setPunishmentType", "(Lltd/matrixstudios/alchemist/punishments/PunishmentType;)V", "getReason", "()Ljava/lang/String;", "setReason", "(Ljava/lang/String;)V", "getTarget", "()Ljava/util/UUID;", "setTarget", "(Ljava/util/UUID;)V", "action", "", "spigot"})
public final class PunishmentExecutePacket extends ltd.matrixstudios.alchemist.redis.RedisPacket {
    @org.jetbrains.annotations.NotNull()
    private ltd.matrixstudios.alchemist.punishments.PunishmentType punishmentType;
    @org.jetbrains.annotations.NotNull()
    private java.util.UUID target;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String reason;
    
    public PunishmentExecutePacket(@org.jetbrains.annotations.NotNull()
    ltd.matrixstudios.alchemist.punishments.PunishmentType punishmentType, @org.jetbrains.annotations.NotNull()
    java.util.UUID target, @org.jetbrains.annotations.NotNull()
    java.lang.String reason) {
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
    public final java.lang.String getReason() {
        return null;
    }
    
    public final void setReason(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @java.lang.Override()
    public void action() {
    }
}
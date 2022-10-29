package ltd.matrixstudios.alchemist.models.grant.types;

import java.lang.System;

@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0012\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001BK\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0004\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007\u0012\u0006\u0010\t\u001a\u00020\n\u0012\u0006\u0010\u000b\u001a\u00020\n\u0012\u0006\u0010\f\u001a\u00020\u0004\u0012\u0006\u0010\r\u001a\u00020\u000e\u0012\u0006\u0010\u000f\u001a\u00020\u0010\u00a2\u0006\u0002\u0010\u0011J\b\u0010 \u001a\u00020\u0002H\u0016J\u0006\u0010!\u001a\u00020\u0004R\u001a\u0010\u000f\u001a\u00020\u0010X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015R\u001a\u0010\u0005\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u0017\"\u0004\b\u0018\u0010\u0019R \u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001a\u0010\u001b\"\u0004\b\u001c\u0010\u001dR\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001e\u0010\u0017\"\u0004\b\u001f\u0010\u0019\u00a8\u0006\""}, d2 = {"Lltd/matrixstudios/alchemist/models/grant/types/Punishment;", "Lltd/matrixstudios/alchemist/models/grant/Grantable;", "Lltd/matrixstudios/alchemist/punishments/PunishmentType;", "punishmentType", "", "easyFindId", "proof", "", "Lltd/matrixstudios/alchemist/models/grant/types/proof/ProofEntry;", "addedTo", "Ljava/util/UUID;", "addedBy", "addedReason", "duration", "", "actor", "Lltd/matrixstudios/alchemist/punishments/actor/DefaultActor;", "(Ljava/lang/String;Ljava/lang/String;Ljava/util/List;Ljava/util/UUID;Ljava/util/UUID;Ljava/lang/String;JLltd/matrixstudios/alchemist/punishments/actor/DefaultActor;)V", "getActor", "()Lltd/matrixstudios/alchemist/punishments/actor/DefaultActor;", "setActor", "(Lltd/matrixstudios/alchemist/punishments/actor/DefaultActor;)V", "getEasyFindId", "()Ljava/lang/String;", "setEasyFindId", "(Ljava/lang/String;)V", "getProof", "()Ljava/util/List;", "setProof", "(Ljava/util/List;)V", "getPunishmentType", "setPunishmentType", "getGrantable", "getIssuedByName", "commons"})
public final class Punishment extends ltd.matrixstudios.alchemist.models.grant.Grantable<ltd.matrixstudios.alchemist.punishments.PunishmentType> {
    @org.jetbrains.annotations.NotNull()
    private java.lang.String punishmentType;
    @org.jetbrains.annotations.NotNull()
    private ltd.matrixstudios.alchemist.punishments.actor.DefaultActor actor;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String easyFindId;
    @org.jetbrains.annotations.NotNull()
    private java.util.List<ltd.matrixstudios.alchemist.models.grant.types.proof.ProofEntry> proof;
    
    public Punishment(@org.jetbrains.annotations.NotNull()
    java.lang.String punishmentType, @org.jetbrains.annotations.NotNull()
    java.lang.String easyFindId, @org.jetbrains.annotations.NotNull()
    java.util.List<ltd.matrixstudios.alchemist.models.grant.types.proof.ProofEntry> proof, @org.jetbrains.annotations.NotNull()
    java.util.UUID addedTo, @org.jetbrains.annotations.NotNull()
    java.util.UUID addedBy, @org.jetbrains.annotations.NotNull()
    java.lang.String addedReason, long duration, @org.jetbrains.annotations.NotNull()
    ltd.matrixstudios.alchemist.punishments.actor.DefaultActor actor) {
        super(null, null, null, null, null, null, null);
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getPunishmentType() {
        return null;
    }
    
    public final void setPunishmentType(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final ltd.matrixstudios.alchemist.punishments.actor.DefaultActor getActor() {
        return null;
    }
    
    public final void setActor(@org.jetbrains.annotations.NotNull()
    ltd.matrixstudios.alchemist.punishments.actor.DefaultActor p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getEasyFindId() {
        return null;
    }
    
    public final void setEasyFindId(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<ltd.matrixstudios.alchemist.models.grant.types.proof.ProofEntry> getProof() {
        return null;
    }
    
    public final void setProof(@org.jetbrains.annotations.NotNull()
    java.util.List<ltd.matrixstudios.alchemist.models.grant.types.proof.ProofEntry> p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getIssuedByName() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public ltd.matrixstudios.alchemist.punishments.PunishmentType getGrantable() {
        return null;
    }
}
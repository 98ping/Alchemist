package ltd.matrixstudios.alchemist.punishment;

import java.lang.System;

@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0016\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bJ\u000e\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fJ\u000e\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000b\u001a\u00020\fJ\u001e\u0010\u000f\u001a\u00020\u00042\u0006\u0010\u0010\u001a\u00020\u000e2\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b\u00a8\u0006\u0011"}, d2 = {"Lltd/matrixstudios/alchemist/punishment/BukkitPunishmentFunctions;", "", "()V", "dispatch", "", "punishment", "Lltd/matrixstudios/alchemist/models/grant/types/Punishment;", "silent", "", "getExecutorFromSender", "Lltd/matrixstudios/alchemist/punishments/actor/executor/Executor;", "sender", "Lorg/bukkit/command/CommandSender;", "getSenderUUID", "Ljava/util/UUID;", "remove", "executor", "spigot"})
public final class BukkitPunishmentFunctions {
    @org.jetbrains.annotations.NotNull()
    public static final ltd.matrixstudios.alchemist.punishment.BukkitPunishmentFunctions INSTANCE = null;
    
    private BukkitPunishmentFunctions() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.UUID getSenderUUID(@org.jetbrains.annotations.NotNull()
    org.bukkit.command.CommandSender sender) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final ltd.matrixstudios.alchemist.punishments.actor.executor.Executor getExecutorFromSender(@org.jetbrains.annotations.NotNull()
    org.bukkit.command.CommandSender sender) {
        return null;
    }
    
    public final void remove(@org.jetbrains.annotations.NotNull()
    java.util.UUID executor, @org.jetbrains.annotations.NotNull()
    ltd.matrixstudios.alchemist.models.grant.types.Punishment punishment, boolean silent) {
    }
    
    public final void dispatch(@org.jetbrains.annotations.NotNull()
    ltd.matrixstudios.alchemist.models.grant.types.Punishment punishment, boolean silent) {
    }
}
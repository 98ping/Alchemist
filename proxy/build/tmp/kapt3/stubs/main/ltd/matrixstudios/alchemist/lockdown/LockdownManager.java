package ltd.matrixstudios.alchemist.lockdown;

import java.lang.System;

@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006J\u0006\u0010\u0007\u001a\u00020\u0004\u00a8\u0006\b"}, d2 = {"Lltd/matrixstudios/alchemist/lockdown/LockdownManager;", "", "()V", "hasClearance", "", "player", "Lnet/md_5/bungee/api/connection/ProxiedPlayer;", "serverIsOnLockdown", "proxy"})
public final class LockdownManager {
    @org.jetbrains.annotations.NotNull()
    public static final ltd.matrixstudios.alchemist.lockdown.LockdownManager INSTANCE = null;
    
    private LockdownManager() {
        super();
    }
    
    public final boolean serverIsOnLockdown() {
        return false;
    }
    
    public final boolean hasClearance(@org.jetbrains.annotations.NotNull()
    net.md_5.bungee.api.connection.ProxiedPlayer player) {
        return false;
    }
}
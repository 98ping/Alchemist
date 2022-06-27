package ltd.matrixstudios.alchemist.permissions;

import java.lang.System;

@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0006\u0010\u0016\u001a\u00020\u0017J\u000e\u0010\u0018\u001a\u00020\u00172\u0006\u0010\u0019\u001a\u00020\u001aJ&\u0010\u001b\u001a\u00020\u00172\u0006\u0010\u001c\u001a\u00020\u00052\u0016\u0010\u001d\u001a\u0012\u0012\u0006\u0012\u0004\u0018\u00010\u0007\u0012\u0006\u0012\u0004\u0018\u00010\b0\u0006J(\u0010\u001e\u001a\u00020\u00172\u0006\u0010\u0019\u001a\u00020\u001a2\u0018\u0010\u001d\u001a\u0014\u0012\u0006\u0012\u0004\u0018\u00010\u0007\u0012\u0006\u0012\u0004\u0018\u00010\b\u0018\u00010\u0006R6\u0010\u0003\u001a\u001e\u0012\u0004\u0012\u00020\u0005\u0012\u0014\u0012\u0012\u0012\u0006\u0012\u0004\u0018\u00010\u0007\u0012\u0006\u0012\u0004\u0018\u00010\b0\u00060\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR\u001a\u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u000f0\u000eX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0010\u001a\u00020\u0011X\u0086.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015\u00a8\u0006\u001f"}, d2 = {"Lltd/matrixstudios/alchemist/permissions/AccessiblePermissionHandler;", "", "()V", "pendingLoadPermissions", "Ljava/util/HashMap;", "Ljava/util/UUID;", "", "", "", "getPendingLoadPermissions", "()Ljava/util/HashMap;", "setPendingLoadPermissions", "(Ljava/util/HashMap;)V", "permissionAttachmentMap", "", "Lorg/bukkit/permissions/PermissionAttachment;", "permissionField", "Ljava/lang/reflect/Field;", "getPermissionField", "()Ljava/lang/reflect/Field;", "setPermissionField", "(Ljava/lang/reflect/Field;)V", "load", "", "remove", "player", "Lorg/bukkit/entity/Player;", "setupPlayer", "uuid", "perms", "update", "spigot"})
public final class AccessiblePermissionHandler {
    @org.jetbrains.annotations.NotNull()
    public static final ltd.matrixstudios.alchemist.permissions.AccessiblePermissionHandler INSTANCE = null;
    private static final java.util.Map<java.util.UUID, org.bukkit.permissions.PermissionAttachment> permissionAttachmentMap = null;
    public static java.lang.reflect.Field permissionField;
    @org.jetbrains.annotations.NotNull()
    private static java.util.HashMap<java.util.UUID, java.util.Map<java.lang.String, java.lang.Boolean>> pendingLoadPermissions;
    
    private AccessiblePermissionHandler() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.reflect.Field getPermissionField() {
        return null;
    }
    
    public final void setPermissionField(@org.jetbrains.annotations.NotNull()
    java.lang.reflect.Field p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.HashMap<java.util.UUID, java.util.Map<java.lang.String, java.lang.Boolean>> getPendingLoadPermissions() {
        return null;
    }
    
    public final void setPendingLoadPermissions(@org.jetbrains.annotations.NotNull()
    java.util.HashMap<java.util.UUID, java.util.Map<java.lang.String, java.lang.Boolean>> p0) {
    }
    
    public final void load() {
    }
    
    public final void setupPlayer(@org.jetbrains.annotations.NotNull()
    java.util.UUID uuid, @org.jetbrains.annotations.NotNull()
    java.util.Map<java.lang.String, java.lang.Boolean> perms) {
    }
    
    public final void remove(@org.jetbrains.annotations.NotNull()
    org.bukkit.entity.Player player) {
    }
    
    public final void update(@org.jetbrains.annotations.NotNull()
    org.bukkit.entity.Player player, @org.jetbrains.annotations.Nullable()
    java.util.Map<java.lang.String, java.lang.Boolean> perms) {
    }
}
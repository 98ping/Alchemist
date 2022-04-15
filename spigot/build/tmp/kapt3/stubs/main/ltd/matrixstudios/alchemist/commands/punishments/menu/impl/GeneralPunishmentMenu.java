package ltd.matrixstudios.alchemist.commands.punishments.menu.impl;

import java.lang.System;

@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0010%\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\u001c\u0010\u0015\u001a\u000e\u0012\u0004\u0012\u00020\u0017\u0012\u0004\u0012\u00020\u00180\u00162\u0006\u0010\u0006\u001a\u00020\u0007H\u0016J\u0010\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u0006\u001a\u00020\u0007H\u0016R\u001a\u0010\u0006\u001a\u00020\u0007X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014\u00a8\u0006\u001b"}, d2 = {"Lltd/matrixstudios/alchemist/commands/punishments/menu/impl/GeneralPunishmentMenu;", "Lltd/matrixstudios/alchemist/util/menu/pagination/PaginatedMenu;", "profile", "Lltd/matrixstudios/alchemist/models/profile/GameProfile;", "punishmentType", "Lltd/matrixstudios/alchemist/punishments/PunishmentType;", "player", "Lorg/bukkit/entity/Player;", "(Lltd/matrixstudios/alchemist/models/profile/GameProfile;Lltd/matrixstudios/alchemist/punishments/PunishmentType;Lorg/bukkit/entity/Player;)V", "getPlayer", "()Lorg/bukkit/entity/Player;", "setPlayer", "(Lorg/bukkit/entity/Player;)V", "getProfile", "()Lltd/matrixstudios/alchemist/models/profile/GameProfile;", "setProfile", "(Lltd/matrixstudios/alchemist/models/profile/GameProfile;)V", "getPunishmentType", "()Lltd/matrixstudios/alchemist/punishments/PunishmentType;", "setPunishmentType", "(Lltd/matrixstudios/alchemist/punishments/PunishmentType;)V", "getPagesButtons", "", "", "Lltd/matrixstudios/alchemist/util/menu/Button;", "getTitle", "", "spigot"})
public final class GeneralPunishmentMenu extends ltd.matrixstudios.alchemist.util.menu.pagination.PaginatedMenu {
    @org.jetbrains.annotations.NotNull()
    private ltd.matrixstudios.alchemist.models.profile.GameProfile profile;
    @org.jetbrains.annotations.NotNull()
    private ltd.matrixstudios.alchemist.punishments.PunishmentType punishmentType;
    @org.jetbrains.annotations.NotNull()
    private org.bukkit.entity.Player player;
    
    public GeneralPunishmentMenu(@org.jetbrains.annotations.NotNull()
    ltd.matrixstudios.alchemist.models.profile.GameProfile profile, @org.jetbrains.annotations.NotNull()
    ltd.matrixstudios.alchemist.punishments.PunishmentType punishmentType, @org.jetbrains.annotations.NotNull()
    org.bukkit.entity.Player player) {
        super(0, null);
    }
    
    @org.jetbrains.annotations.NotNull()
    public final ltd.matrixstudios.alchemist.models.profile.GameProfile getProfile() {
        return null;
    }
    
    public final void setProfile(@org.jetbrains.annotations.NotNull()
    ltd.matrixstudios.alchemist.models.profile.GameProfile p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final ltd.matrixstudios.alchemist.punishments.PunishmentType getPunishmentType() {
        return null;
    }
    
    public final void setPunishmentType(@org.jetbrains.annotations.NotNull()
    ltd.matrixstudios.alchemist.punishments.PunishmentType p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final org.bukkit.entity.Player getPlayer() {
        return null;
    }
    
    public final void setPlayer(@org.jetbrains.annotations.NotNull()
    org.bukkit.entity.Player p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public java.util.Map<java.lang.Integer, ltd.matrixstudios.alchemist.util.menu.Button> getPagesButtons(@org.jetbrains.annotations.NotNull()
    org.bukkit.entity.Player player) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public java.lang.String getTitle(@org.jetbrains.annotations.NotNull()
    org.bukkit.entity.Player player) {
        return null;
    }
}
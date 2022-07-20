package ltd.matrixstudios.alchemist.commands.grants.menu.grant;

import java.lang.System;

@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\n\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u0010\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0016J\u0018\u0010\u0013\u001a\n\u0012\u0004\u0012\u00020\u0015\u0018\u00010\u00142\u0006\u0010\u0011\u001a\u00020\u0012H\u0016J\u0012\u0010\u0016\u001a\u0004\u0018\u00010\u00152\u0006\u0010\u0011\u001a\u00020\u0012H\u0016J\u0010\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0011\u001a\u00020\u0012H\u0016J \u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001eH\u0016R\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000e\u00a8\u0006\u001f"}, d2 = {"Lltd/matrixstudios/alchemist/commands/grants/menu/grant/GrantButton;", "Lltd/matrixstudios/alchemist/util/menu/Button;", "rank", "Lltd/matrixstudios/alchemist/models/ranks/Rank;", "gameProfile", "Lltd/matrixstudios/alchemist/models/profile/GameProfile;", "(Lltd/matrixstudios/alchemist/models/ranks/Rank;Lltd/matrixstudios/alchemist/models/profile/GameProfile;)V", "getGameProfile", "()Lltd/matrixstudios/alchemist/models/profile/GameProfile;", "setGameProfile", "(Lltd/matrixstudios/alchemist/models/profile/GameProfile;)V", "getRank", "()Lltd/matrixstudios/alchemist/models/ranks/Rank;", "setRank", "(Lltd/matrixstudios/alchemist/models/ranks/Rank;)V", "getData", "", "player", "Lorg/bukkit/entity/Player;", "getDescription", "", "", "getDisplayName", "getMaterial", "Lorg/bukkit/Material;", "onClick", "", "slot", "", "type", "Lorg/bukkit/event/inventory/ClickType;", "spigot"})
public final class GrantButton extends ltd.matrixstudios.alchemist.util.menu.Button {
    @org.jetbrains.annotations.NotNull()
    private ltd.matrixstudios.alchemist.models.ranks.Rank rank;
    @org.jetbrains.annotations.NotNull()
    private ltd.matrixstudios.alchemist.models.profile.GameProfile gameProfile;
    
    public GrantButton(@org.jetbrains.annotations.NotNull()
    ltd.matrixstudios.alchemist.models.ranks.Rank rank, @org.jetbrains.annotations.NotNull()
    ltd.matrixstudios.alchemist.models.profile.GameProfile gameProfile) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final ltd.matrixstudios.alchemist.models.ranks.Rank getRank() {
        return null;
    }
    
    public final void setRank(@org.jetbrains.annotations.NotNull()
    ltd.matrixstudios.alchemist.models.ranks.Rank p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final ltd.matrixstudios.alchemist.models.profile.GameProfile getGameProfile() {
        return null;
    }
    
    public final void setGameProfile(@org.jetbrains.annotations.NotNull()
    ltd.matrixstudios.alchemist.models.profile.GameProfile p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public org.bukkit.Material getMaterial(@org.jetbrains.annotations.NotNull()
    org.bukkit.entity.Player player) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    @java.lang.Override()
    public java.util.List<java.lang.String> getDescription(@org.jetbrains.annotations.NotNull()
    org.bukkit.entity.Player player) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    @java.lang.Override()
    public java.lang.String getDisplayName(@org.jetbrains.annotations.NotNull()
    org.bukkit.entity.Player player) {
        return null;
    }
    
    @java.lang.Override()
    public short getData(@org.jetbrains.annotations.NotNull()
    org.bukkit.entity.Player player) {
        return 0;
    }
    
    @java.lang.Override()
    public void onClick(@org.jetbrains.annotations.NotNull()
    org.bukkit.entity.Player player, int slot, @org.jetbrains.annotations.NotNull()
    org.bukkit.event.inventory.ClickType type) {
    }
}
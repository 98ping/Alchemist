package ltd.matrixstudios.alchemist.commands.punishments.menu.impl;

import java.lang.System;

@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\n\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0010\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016J\u0018\u0010\f\u001a\n\u0012\u0004\u0012\u00020\u000e\u0018\u00010\r2\u0006\u0010\n\u001a\u00020\u000bH\u0016J\u0012\u0010\u000f\u001a\u0004\u0018\u00010\u000e2\u0006\u0010\n\u001a\u00020\u000bH\u0016J\u0010\u0010\u0010\u001a\u00020\u00112\u0006\u0010\n\u001a\u00020\u000bH\u0016J \u0010\u0012\u001a\u00020\u00132\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0017H\u0016R\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\u0004\u00a8\u0006\u0018"}, d2 = {"Lltd/matrixstudios/alchemist/commands/punishments/menu/impl/GeneralPunishmentButton;", "Lltd/matrixstudios/alchemist/util/menu/Button;", "punishment", "Lltd/matrixstudios/alchemist/models/grant/types/Punishment;", "(Lltd/matrixstudios/alchemist/models/grant/types/Punishment;)V", "getPunishment", "()Lltd/matrixstudios/alchemist/models/grant/types/Punishment;", "setPunishment", "getData", "", "player", "Lorg/bukkit/entity/Player;", "getDescription", "", "", "getDisplayName", "getMaterial", "Lorg/bukkit/Material;", "onClick", "", "slot", "", "type", "Lorg/bukkit/event/inventory/ClickType;", "spigot"})
public final class GeneralPunishmentButton extends ltd.matrixstudios.alchemist.util.menu.Button {
    @org.jetbrains.annotations.NotNull()
    private ltd.matrixstudios.alchemist.models.grant.types.Punishment punishment;
    
    public GeneralPunishmentButton(@org.jetbrains.annotations.NotNull()
    ltd.matrixstudios.alchemist.models.grant.types.Punishment punishment) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final ltd.matrixstudios.alchemist.models.grant.types.Punishment getPunishment() {
        return null;
    }
    
    public final void setPunishment(@org.jetbrains.annotations.NotNull()
    ltd.matrixstudios.alchemist.models.grant.types.Punishment p0) {
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
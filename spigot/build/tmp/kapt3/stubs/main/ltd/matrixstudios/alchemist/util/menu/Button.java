package ltd.matrixstudios.alchemist.util.menu;

import java.lang.System;

@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\n\n\u0000\n\u0002\u0010!\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\b&\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0005\u001a\u00020\u0006H&J\u0018\u0010\t\u001a\n\u0012\u0004\u0012\u00020\u000b\u0018\u00010\n2\u0006\u0010\u0005\u001a\u00020\u0006H&J\u0012\u0010\f\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\u0005\u001a\u00020\u0006H&J\u0010\u0010\r\u001a\u00020\u000e2\u0006\u0010\u0005\u001a\u00020\u0006H&J \u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0014H&\u00a8\u0006\u0015"}, d2 = {"Lltd/matrixstudios/alchemist/util/menu/Button;", "", "()V", "constructItemStack", "Lorg/bukkit/inventory/ItemStack;", "player", "Lorg/bukkit/entity/Player;", "getData", "", "getDescription", "", "", "getDisplayName", "getMaterial", "Lorg/bukkit/Material;", "onClick", "", "slot", "", "type", "Lorg/bukkit/event/inventory/ClickType;", "spigot"})
public abstract class Button {
    
    public Button() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public abstract org.bukkit.Material getMaterial(@org.jetbrains.annotations.NotNull()
    org.bukkit.entity.Player player);
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.util.List<java.lang.String> getDescription(@org.jetbrains.annotations.NotNull()
    org.bukkit.entity.Player player);
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.String getDisplayName(@org.jetbrains.annotations.NotNull()
    org.bukkit.entity.Player player);
    
    public abstract short getData(@org.jetbrains.annotations.NotNull()
    org.bukkit.entity.Player player);
    
    public abstract void onClick(@org.jetbrains.annotations.NotNull()
    org.bukkit.entity.Player player, int slot, @org.jetbrains.annotations.NotNull()
    org.bukkit.event.inventory.ClickType type);
    
    @org.jetbrains.annotations.NotNull()
    public final org.bukkit.inventory.ItemStack constructItemStack(@org.jetbrains.annotations.NotNull()
    org.bukkit.entity.Player player) {
        return null;
    }
}
package ltd.matrixstudios.alchemist.util.menu.pagination;

import java.lang.System;

@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\b&\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u000e\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00030\rH\u0016J\u0018\u0010\u000e\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00110\u00100\u000fJ\u000e\u0010\u0012\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005J\u0012\u0010\u0013\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00110\u0010J\u001c\u0010\u0014\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00110\u00102\u0006\u0010\u0004\u001a\u00020\u0005H&J\u0010\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0004\u001a\u00020\u0005H&J\u0006\u0010\u0017\u001a\u00020\u0018R\u001a\u0010\u0007\u001a\u00020\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000bR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0019"}, d2 = {"Lltd/matrixstudios/alchemist/util/menu/pagination/PaginatedMenu;", "", "size", "", "player", "Lorg/bukkit/entity/Player;", "(ILorg/bukkit/entity/Player;)V", "page", "getPage", "()I", "setPage", "(I)V", "getAllPagesButtonSlots", "", "getButtonsInRange", "Ljava/util/concurrent/CompletableFuture;", "", "Lltd/matrixstudios/alchemist/util/menu/Button;", "getMaximumPages", "getPageNavigationButtons", "getPagesButtons", "getTitle", "", "updateMenu", "", "spigot"})
public abstract class PaginatedMenu {
    private final int size = 0;
    private final org.bukkit.entity.Player player = null;
    private int page = 1;
    
    public PaginatedMenu(int size, @org.jetbrains.annotations.NotNull()
    org.bukkit.entity.Player player) {
        super();
    }
    
    public final int getPage() {
        return 0;
    }
    
    public final void setPage(int p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public abstract java.util.Map<java.lang.Integer, ltd.matrixstudios.alchemist.util.menu.Button> getPagesButtons(@org.jetbrains.annotations.NotNull()
    org.bukkit.entity.Player player);
    
    @org.jetbrains.annotations.NotNull()
    public abstract java.lang.String getTitle(@org.jetbrains.annotations.NotNull()
    org.bukkit.entity.Player player);
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.concurrent.CompletableFuture<java.util.Map<java.lang.Integer, ltd.matrixstudios.alchemist.util.menu.Button>> getButtonsInRange() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.Map<java.lang.Integer, ltd.matrixstudios.alchemist.util.menu.Button> getPageNavigationButtons() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public java.util.List<java.lang.Integer> getAllPagesButtonSlots() {
        return null;
    }
    
    public final int getMaximumPages(@org.jetbrains.annotations.NotNull()
    org.bukkit.entity.Player player) {
        return 0;
    }
    
    public final void updateMenu() {
    }
}
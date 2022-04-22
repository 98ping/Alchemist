package ltd.matrixstudios.alchemist.commands.staff;

import java.lang.System;

@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u001a\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\b\b\u0001\u0010\u0007\u001a\u00020\bH\u0007\u00a8\u0006\t"}, d2 = {"Lltd/matrixstudios/alchemist/commands/staff/StaffchatCommand;", "Lco/aikar/commands/BaseCommand;", "()V", "staffchat", "", "player", "Lorg/bukkit/entity/Player;", "message", "", "spigot"})
public final class StaffchatCommand extends co.aikar.commands.BaseCommand {
    
    public StaffchatCommand() {
        super();
    }
    
    @co.aikar.commands.annotation.CommandAlias(value = "sc|staffchat")
    public final void staffchat(@org.jetbrains.annotations.NotNull()
    org.bukkit.entity.Player player, @org.jetbrains.annotations.NotNull()
    @co.aikar.commands.annotation.Name(value = "message")
    java.lang.String message) {
    }
}
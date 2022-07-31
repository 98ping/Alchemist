package ltd.matrixstudios.alchemist.commands.rank;

import java.lang.System;

@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0007\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u001a\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\b\b\u0001\u0010\u0007\u001a\u00020\bH\u0007J\u001a\u0010\t\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\b\b\u0001\u0010\u0007\u001a\u00020\bH\u0007J\u0010\u0010\n\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007J\u001a\u0010\u000b\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\b\b\u0001\u0010\u0007\u001a\u00020\bH\u0007J\u0010\u0010\f\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007J.\u0010\r\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\b\b\u0001\u0010\u0007\u001a\u00020\b2\b\b\u0001\u0010\r\u001a\u00020\b2\b\b\u0001\u0010\u000e\u001a\u00020\bH\u0007\u00a8\u0006\u000f"}, d2 = {"Lltd/matrixstudios/alchemist/commands/rank/GenericRankCommands;", "Lco/aikar/commands/BaseCommand;", "()V", "create", "", "sender", "Lorg/bukkit/command/CommandSender;", "name", "", "delete", "help", "info", "list", "module", "arg", "spigot"})
@co.aikar.commands.annotation.CommandAlias(value = "rank")
public final class GenericRankCommands extends co.aikar.commands.BaseCommand {
    
    public GenericRankCommands() {
        super();
    }
    
    @co.aikar.commands.annotation.CommandPermission(value = "rank.admin")
    @co.aikar.commands.annotation.HelpCommand()
    public final void help(@org.jetbrains.annotations.NotNull()
    org.bukkit.command.CommandSender sender) {
    }
    
    @co.aikar.commands.annotation.CommandPermission(value = "rank.admin")
    @co.aikar.commands.annotation.Subcommand(value = "create")
    public final void create(@org.jetbrains.annotations.NotNull()
    org.bukkit.command.CommandSender sender, @org.jetbrains.annotations.NotNull()
    @co.aikar.commands.annotation.Name(value = "name")
    java.lang.String name) {
    }
    
    @co.aikar.commands.annotation.CommandPermission(value = "rank.admin")
    @co.aikar.commands.annotation.Subcommand(value = "list")
    public final void list(@org.jetbrains.annotations.NotNull()
    org.bukkit.command.CommandSender sender) {
    }
    
    @co.aikar.commands.annotation.CommandPermission(value = "rank.admin")
    @co.aikar.commands.annotation.Subcommand(value = "delete")
    public final void delete(@org.jetbrains.annotations.NotNull()
    org.bukkit.command.CommandSender sender, @org.jetbrains.annotations.NotNull()
    @co.aikar.commands.annotation.Name(value = "rank")
    java.lang.String name) {
    }
    
    @co.aikar.commands.annotation.CommandPermission(value = "rank.admin")
    @co.aikar.commands.annotation.Subcommand(value = "info")
    public final void info(@org.jetbrains.annotations.NotNull()
    org.bukkit.command.CommandSender sender, @org.jetbrains.annotations.NotNull()
    @co.aikar.commands.annotation.Name(value = "rank")
    java.lang.String name) {
    }
    
    @co.aikar.commands.annotation.CommandPermission(value = "rank.admin")
    @co.aikar.commands.annotation.Subcommand(value = "module")
    public final void module(@org.jetbrains.annotations.NotNull()
    org.bukkit.command.CommandSender sender, @org.jetbrains.annotations.NotNull()
    @co.aikar.commands.annotation.Name(value = "rank")
    java.lang.String name, @org.jetbrains.annotations.NotNull()
    @co.aikar.commands.annotation.Name(value = "module")
    java.lang.String module, @org.jetbrains.annotations.NotNull()
    @co.aikar.commands.annotation.Name(value = "argument")
    java.lang.String arg) {
    }
}